package com.example.Beteg.nyilvantarto.controller.api;


import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/notes")
public class NoteRestController {

    @Autowired
    private NoteService noteService;

    @GetMapping()
    public List<Note> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getPatientById(@PathVariable Long id){
        return noteService.findById(id);
    }

    @PostMapping("/create")
    public Note createPatient(@RequestBody Note note){
        return noteService.save(note);
    }


    @PostMapping("/update")
    public Note updatePatient(@RequestBody Note note){
        return noteService.edit(note);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id){
        noteService.deleteById(id);
    }




}
