package com.example.Beteg.nyilvantarto.controller;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.DoctorService;
import com.example.Beteg.nyilvantarto.service.NoteService;
import com.example.Beteg.nyilvantarto.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller()
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;

    @GetMapping()
    public List<Note> getAllPatients() {
        return noteService.getAllNotes();
    }

    //POST: SAVE New Note
    @PostMapping("/save")
    public String saveNote(@RequestParam Long patientId,
                           @RequestParam String content,
                           HttpSession session) {

        Long doctorId = (Long) session.getAttribute("doctorId");
        Patient patient = patientService.findById(patientId);
        Doctor doctor = doctorService.findById(doctorId);

        Note note = new Note();
        note.setContent(content);
        note.setPatient(patient);
        note.setDoctor(doctor);
        note.setCreatedAt(LocalDateTime.now());

        noteService.save(note);
        return "redirect:/patients/" + patientId;
    }


    //GET: Delete Note
    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        Note note = noteService.findById(id);
        Long patientId = note.getPatient().getId();
        noteService.deleteById(id);
        return "redirect:/patients/" + patientId;
    }



}
