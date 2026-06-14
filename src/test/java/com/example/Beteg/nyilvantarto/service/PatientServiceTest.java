package com.example.Beteg.nyilvantarto.service;

import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepositoryMock;
    @InjectMocks
    private PatientService underTest;

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
        assertEquals(LocalDate.parse("1990-01-01"), result.getBirthDate()); // <-- Most már típushelyes!
        assertEquals("Szabó Mária", result.getMotherName());
        assertEquals("Budapest, Fő utca 1.", result.getAddress());
        assertEquals("Gyógyult", result.getDiagnosis());

        verify(patientRepositoryMock, times(1)).findById(id);
        verify(patientRepositoryMock, times(1)).save(existingPatientInDb);
    }
}
