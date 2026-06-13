package com.example.Beteg.nyilvantarto.controller;


import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    //GET: List all petients (responds to patients/list
   /* @GetMapping("/list")
    public String getAllPatients(Model model){
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/patients"; //Template location in patients directory
    }*/

    @GetMapping("/list")
    public String getAllPatients(@RequestParam(required = false) String search, Model model){
        List<Patient> patients = patientService.searchPatients(search);
        model.addAttribute("patients", patients);
        model.addAttribute("search", search);

        return "patients/patients";
    }

    //GET: Show Create Author Page
    @GetMapping("/new")
    public String createPatientForm(Model model){
        model.addAttribute("patient", new Patient());
        return "patients/create-patient"; //Template for creating patient
    }

    //POST: Save New Patient
    @PostMapping
    public String savePatient(@ModelAttribute Patient patient){
    patientService.save(patient);
    return "redirect:/patients/list"; // Redirect to /patients/list after saving
    }

    //GET: Show Patient Page
    @GetMapping("/{id}")
    public String viewPatientForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.findById(id);
        model.addAttribute("patient", patient);
        return "patients/view-patient";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model){
        Patient patient = patientService.findById(id);
        model.addAttribute("patient", patient);
        return "patients/edit-patient"; //Template for editing patients
    }

    //POST: Update Existing Patient
    @PostMapping("/edit")
    public String updatePatient(@ModelAttribute Patient patient){
        patientService.edit(patient);
        return "redirect:/patients/list";// Redirect to /patients/list after updating
    }

    //Get: Delete Patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id){
        patientService.deleteById(id);
        return "redirect:/patients/list";// Redirect to /patients/list after deleting
    }


}
