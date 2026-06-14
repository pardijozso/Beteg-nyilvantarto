package com.example.Beteg.nyilvantarto.controller;

import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.NoteService;
import com.example.Beteg.nyilvantarto.service.PatientService;
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

    @GetMapping()
    public List<Note> getAllPatients() {
        return noteService.getAllNotes();
    }

    //POST: SAVE New Note
    @PostMapping("/save")
    public String saveNote(@RequestParam Long patientId, @RequestParam String content) {

        Patient patient = patientService.findById(patientId);
        Note note = new Note();
        note.setContent(content);
        note.setPatient(patient);
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
