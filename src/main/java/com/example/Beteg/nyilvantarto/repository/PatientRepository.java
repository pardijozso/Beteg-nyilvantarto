package com.example.Beteg.nyilvantarto.repository;

import com.example.Beteg.nyilvantarto.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByNameContainingIgnoreCase(String name);
    List<Patient> findByDoctorId(Long doctorId);
}
