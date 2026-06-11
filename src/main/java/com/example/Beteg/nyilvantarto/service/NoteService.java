package com.example.Beteg.nyilvantarto.service;


import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteService {

public List<Note> getAllNotes(){
    return notes;
}

private List<Note> notes = List.of(
  Note.builder()
          .content("első vizsálat eredménye negatív")
          .patient(
                  Patient.builder()
                          .name("Pardi J").build()
          )
          .build(),

        Note.builder()
                .content("első vizsálat eredménye pozitív")
                .patient(
                        Patient.builder()
                                .name("MP").build()
                )
                .build()
);

}
