package com.example.Beteg.nyilvantarto.controller;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.repository.DoctorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "doctor-list"; // Ez legyen a html neve
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form";
    }

    @PostMapping
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }
}