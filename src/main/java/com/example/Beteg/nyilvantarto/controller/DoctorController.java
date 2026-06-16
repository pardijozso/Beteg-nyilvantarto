package com.example.Beteg.nyilvantarto.controller;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "doctors/doctor-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/create-doctor";
    }

    @PostMapping
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("select/{id}")
    public String selectDoctor(@PathVariable Long id, HttpSession session) {
        session.setAttribute("doctorId", id);
        return "redirect:/patients/list?doctorId=" + id;
    }


}


