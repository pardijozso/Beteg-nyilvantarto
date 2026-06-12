package com.example.Beteg.nyilvantarto.repository;

import com.example.Beteg.nyilvantarto.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {


}
