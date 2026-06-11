package com.example.Beteg.nyilvantarto.controller;

import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.NoteService;
import com.example.Beteg.nyilvantarto.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping()
    public List<Note> getAllPatients(){
        return noteService.getAllNotes();
    }
}
