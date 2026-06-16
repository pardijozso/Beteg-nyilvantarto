package com.example.Beteg.nyilvantarto.service;

import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.exception.NoSuchEntityException;
import com.example.Beteg.nyilvantarto.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepositoryMock;

    @InjectMocks
    private PatientService underTest;

    @Test
    void getAllPatientsHappyPath() {

        //GIVEN
        List<Patient> expectedPatients = List.of(

                Patient.builder()
                        .id(1L)
                        .name("Nagy János")
                        .birthPlace("Szolnok")
                        .birthDate(LocalDate.parse("1990-01-01"))
                        .motherName("Szabó Mária")
                        .address("Budapest, Fő utca 1.")
                        .diagnosis("Gyógyult")
                        .build(),

                Patient.builder()
                        .id(2L)
                        .name("Kiss Péter")
                        .birthPlace("Debrecen")
                        .birthDate(LocalDate.parse("1995-01-01"))
                        .motherName("Szabó Valaki")
                        .address("Kisújszállás, Fő utca 12.")
                        .diagnosis("Idegbeteg")
                        .build()
        );

        when(patientRepositoryMock.findAll()).thenReturn(expectedPatients);

        //WHEN
        List<Patient> result = underTest.getAllPatients();

        //THEN
        Assertions.assertIterableEquals(expectedPatients, result);

    }


    @Test
    void savePatientHappyPath() {

        //GIVEN
        Patient patientToSave = Patient.builder()
                .name("Nagy János")
                .birthPlace("Szolnok")
                .birthDate(LocalDate.parse("1990-01-01"))
                .motherName("Szabó Mária")
                .address("Budapest, Fő utca 1.")
                .diagnosis("Gyógyult")
                .build();

        Patient savedPatient = Patient.builder()
                .id(1L)
                .name("Nagy János")
                .birthPlace("Szolnok")
                .birthDate(LocalDate.parse("1990-01-01"))
                .motherName("Szabó Mária")
                .address("Budapest, Fő utca 1.")
                .diagnosis("Gyógyult")
                .build();

        when(patientRepositoryMock.save(patientToSave)).thenReturn(savedPatient);

        //WHEN
        Patient result = underTest.save(patientToSave);
        //THEN
        assertEquals(savedPatient, result);

    }

    @Test
    void editPatientHappyPath() {
        Long id = 1L;

        Patient patientToEdit = Patient.builder()
                .id(id)
                .name("Kovács Péter Új Neve")
                .birthPlace("Debrecen")
                .birthDate(LocalDate.parse("1990-01-01"))
                .motherName("Szabó Mária")
                .address("Budapest, Fő utca 1.")
                .diagnosis("Gyógyult")
                .build();

        Patient existingPatientInDb = Patient.builder()
                .id(id)
                .name("Kovács Péter")
                .birthPlace("Budapest")
                .birthDate(LocalDate.parse("1989-12-31"))
                .motherName("Kiss Anna")
                .address("Szeged, Petőfi utca 2.")
                .diagnosis("Beteg")
                .build();

        when(patientRepositoryMock.findById(id)).thenReturn(Optional.of(existingPatientInDb));
        when(patientRepositoryMock.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient result = underTest.edit(patientToEdit);

        assertNotNull(result);
        assertEquals("Kovács Péter Új Neve", result.getName());
        assertEquals("Debrecen", result.getBirthPlace());
        assertEquals(LocalDate.parse("1990-01-01"), result.getBirthDate());
        assertEquals("Szabó Mária", result.getMotherName());
        assertEquals("Budapest, Fő utca 1.", result.getAddress());
        assertEquals("Gyógyult", result.getDiagnosis());

        verify(patientRepositoryMock, times(1)).findById(id);
        verify(patientRepositoryMock, times(1)).save(existingPatientInDb);
    }

    @Test
    void findByIdIfPatientFound(){
        //GIVEN
        Long id = 1L;
        Patient expectedPatient = Patient.builder()
                .id(id)
                .name("Kovács Péter")
                .birthPlace("Budapest")
                .birthDate(LocalDate.parse("1989-12-31"))
                .motherName("Kiss Anna")
                .address("Szeged, Petőfi utca 2.")
                .diagnosis("Beteg")
                .build();

        Optional<Patient> expectedOptionalPatient = Optional.of(expectedPatient);

        when(patientRepositoryMock.findById(id)).thenReturn(expectedOptionalPatient);

        //WHEN
        Patient result = underTest.findById(id);
        //THEN
        assertEquals(expectedPatient, result);

    }

    @Test
    void findByIdWhenPatientMissing() {
        //GIVEN
        Long id = 1L;
        String exMessage = "There was no patient with id: " + id;
        Optional<Patient> expectedOptionalPatient = Optional.empty();

        when(patientRepositoryMock.findById(id)).thenReturn(expectedOptionalPatient);

        //WHEN
        Exception exception = assertThrows(NoSuchEntityException.class, () -> underTest.findById(id));

        //THEN
        assertEquals(exMessage, exception.getMessage());

    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        // Given
        Long patientId = 1L;

        // When
        underTest.deleteById(patientId);

        //THEN
        verify(patientRepositoryMock, times(1)).deleteById(patientId);
    }

    @Test
    void searchPatientsWhenSearchTextIsNull() {
        //GIVEN
        List<Patient> allPatients = List.of(new Patient(), new Patient());
        when(patientRepositoryMock.findAll()).thenReturn(allPatients);

        //WHEN
        List<Patient> resultNull = underTest.searchPatients(null);
        assertEquals(2, resultNull.size());

        List<Patient> resultBlank = underTest.searchPatients("   ");
        assertEquals(2, resultBlank.size());

        //THEN
        verify(patientRepositoryMock, times(2)).findAll();
        verify(patientRepositoryMock, never()).findByNameContainingIgnoreCase(anyString());
    }

    @Test
    void searchPatientsWhenSearchTextIsValid() {
        // Given
        String searchText = "János";
        List<Patient> expectedPatients = List.of(

                Patient.builder()
                        .id(1L)
                        .name("Nagy János")
                        .birthPlace("Szolnok")
                        .birthDate(LocalDate.parse("1990-01-01"))
                        .motherName("Szabó Mária")
                        .address("Budapest, Fő utca 1.")
                        .diagnosis("Gyógyult")
                        .build(),

                Patient.builder()
                        .id(2L)
                        .name("Kiss János")
                        .birthPlace("Debrecen")
                        .birthDate(LocalDate.parse("1995-01-01"))
                        .motherName("Szabó Valaki")
                        .address("Kisújszállás, Fő utca 12.")
                        .diagnosis("Idegbeteg")
                        .build()
        );

        when(patientRepositoryMock.findByNameContainingIgnoreCase(searchText)).thenReturn(expectedPatients);

        // When
        List<Patient> result = underTest.searchPatients(searchText);

        // Then
        verify(patientRepositoryMock, times(1)).findByNameContainingIgnoreCase(searchText);
        verify(patientRepositoryMock, never()).findAll();
    }

    @Test
    void findByDoctorIdHappyPath() {

        //GIVEN
        Long doctorId = 1L;

        List<Patient> expectedPatients = List.of(

                Patient.builder()
                        .id(1L)
                        .name("Nagy János")
                        .birthPlace("Szolnok")
                        .birthDate(LocalDate.parse("1990-01-01"))
                        .motherName("Szabó Mária")
                        .address("Budapest, Fő utca 1.")
                        .diagnosis("Gyógyult")
                        .build(),

                Patient.builder()
                        .id(2L)
                        .name("Kiss Péter")
                        .birthPlace("Debrecen")
                        .birthDate(LocalDate.parse("1995-01-01"))
                        .motherName("Szabó Valaki")
                        .address("Kisújszállás, Fő utca 12.")
                        .diagnosis("Idegbeteg")
                        .build()
        );

        when(patientRepositoryMock.findByDoctorId(doctorId)).thenReturn(expectedPatients);

        //WHEN
        List<Patient> result = underTest.findByDoctorId(doctorId);

        //THEN
        Assertions.assertIterableEquals(expectedPatients, result);

    }

}
