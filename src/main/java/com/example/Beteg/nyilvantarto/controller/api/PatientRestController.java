package com.example.Beteg.nyilvantarto.controller.api;


import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/books")
public class PatientRestController {

    @Autowired
    private PatientService patientService;

    @GetMapping()
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id){
        return patientService.findById(id);
    }

    @PostMapping("/create")
    public Patient createPatient(@RequestBody Patient patient){
        return patientService.save(patient);
    }


    @PostMapping("/update")
    public Patient updatePatient(@RequestBody Patient patient){
        return patientService.edit(patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id){
        patientService.deleteById(id);
    }

}
