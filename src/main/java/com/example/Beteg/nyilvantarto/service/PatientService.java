package com.example.Beteg.nyilvantarto.service;

import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    public List<Patient> getAllPatients(){
        return patients;
    }

private List<Patient> patients = List.of(
    Patient.builder()
            .name("Pardi J")
            .birthDate(LocalDate.of(2000,01,01))
            .birthPlace("Budapest")
            .motherName("Pardiné")
            .address("Nyiregyháza, Egyetem út 1. ")
            .diagnosis("Túl okos")
            .build(),
        Patient.builder()
                .name("MP")
                .birthDate(LocalDate.of(2000,01,01))
                .birthPlace("Budapest")
                .motherName("Mné")
                .address("Miskol, Egyetem út 1. ")
                .diagnosis("Nem Túl okos")
                .build()



);

}
