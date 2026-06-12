package com.example.Beteg.nyilvantarto.service;


import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.exception.NoSuchEntityException;
import com.example.Beteg.nyilvantarto.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public void save(Note note){
        noteRepository.save(note);
    }

    public void edit(Note note){
        noteRepository.save(note);
    }

    public Note findById(Long id){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return optionalNote.get();
        }else {
            throw new NoSuchEntityException("There was no note with id: " + id);
        }
    }

    public void deleteById(Long id){
        noteRepository.deleteById(id);
    }



}
