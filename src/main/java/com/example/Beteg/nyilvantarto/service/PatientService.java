package com.example.Beteg.nyilvantarto.service;

import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.exception.NoSuchEntityException;
import com.example.Beteg.nyilvantarto.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {


    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public void save(Patient patient){
        patientRepository.save(patient);
    }

    public void edit(Patient patient){
        patientRepository.save(patient);
    }

    public Patient findById(Long id){
    Optional <Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            return optionalPatient.get();
        }else {
            throw new NoSuchEntityException("There was no patient with id: " + id);
        }
    }

    public void deleteById(Long id){
        patientRepository.deleteById(id);
    }







}
