package com.example.Beteg.nyilvantarto.service;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.exception.NoSuchEntityException;
import com.example.Beteg.nyilvantarto.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    private DoctorRepository DoctorRepositoryMock;

    @InjectMocks
    private DoctorService underTest;

    @Test
    void getAllDoctorsHappyPath() {
        //GIVEN
        List<Doctor> expectedDoctors = List.of(
                Doctor.builder()
                        .id(1L)
                        .name("Nagy János")
                        .build(),
                Doctor.builder()
                        .id(2L)
                        .name("Kiss Jenő")
                        .build()
        );

        when(DoctorRepositoryMock.findAll()).thenReturn(expectedDoctors);

        //WHEN
        List<Doctor> result = underTest.getAllDoctors();
        //THEN
        Assertions.assertIterableEquals(expectedDoctors, result);

    }

    @Test
    void saveHappyPath(){
        //GIVEN
        Doctor doctorToSave = Doctor.builder()
                .name("Kovács Péter")
                .build();

        Doctor savedDoctor = Doctor.builder()
                .id(1L)
                .name("Kovács Péter")
                .build();

        when(DoctorRepositoryMock.save(doctorToSave)).thenReturn(savedDoctor);

        //WHEN
        Doctor result = underTest.save(doctorToSave);

        //THEN
        assertEquals(savedDoctor,result);
    }

    @Test
    void editHappyPath() {
        Long id = 1L;

        Doctor doctorToEdit = Doctor.builder()
                .id(id)
                .name("Kovács Péter Jenő")
                .build();

        Doctor existingDoctorInDb = Doctor.builder()
                .id(id)
                .name("Kovács Péter")
                .build();

        when(DoctorRepositoryMock.findById(id)).thenReturn(Optional.of(existingDoctorInDb));
        when(DoctorRepositoryMock.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Doctor result = underTest.edit(doctorToEdit);

        assertNotNull(result);
        assertEquals("Kovács Péter Jenő", result.getName());

        verify(DoctorRepositoryMock, times(1)).findById(id);
        verify(DoctorRepositoryMock, times(1)).save(existingDoctorInDb);
    }

    @Test
    void findByIdWhenDoctorFound() {
        //GIVEN
        Long id = 1L;
        Doctor expectedDoctor = Doctor.builder()
                .id(id)
                .name("Nagy János")
                .build();
        Optional<Doctor> expectedOptionalDoctor = Optional.of(expectedDoctor);
        // Azért kell az optional mert a findById egy Optional obijektumot ad vissza.

        when(DoctorRepositoryMock.findById(id)).thenReturn(expectedOptionalDoctor);

        //WHEN
        Doctor result = underTest.findById(id);
        //THEN
        assertEquals(expectedDoctor, result);

    }

    @Test
    void findByIdWhenDoctorIsMissing() {
        //GIVEN
        Long id = 1L;
        String exeptonMessage = "There was no doctor with id: " + id;
        Optional<Doctor> expectedOptionalDoctor = Optional.empty();

        when(DoctorRepositoryMock.findById(id)).thenReturn(expectedOptionalDoctor);

        //WHEN
        Exception exception = assertThrows(NoSuchEntityException.class, () -> underTest.findById(id));
        //THEN
        assertEquals(exeptonMessage, exception.getMessage());

    }

    @Test
    void deleteByIdHappyPath() {
        // GIVEN
        Long idToVoid = 1L;

        doNothing().when(DoctorRepositoryMock).deleteById(idToVoid);

        // WHEN
        underTest.deleteById(idToVoid);

        // THEN
        verify(DoctorRepositoryMock, times(1)).deleteById(idToVoid);
    }

}