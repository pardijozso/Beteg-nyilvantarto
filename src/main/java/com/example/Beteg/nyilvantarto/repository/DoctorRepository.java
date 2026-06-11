package com.example.Beteg.nyilvantarto.repository;
import com.example.Beteg.nyilvantarto.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
}
