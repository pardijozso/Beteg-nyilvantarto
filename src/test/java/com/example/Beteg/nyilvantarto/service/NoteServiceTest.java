package com.example.Beteg.nyilvantarto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;


    @Test
    void getAllNotesHappyPath() {
        // GIVEN
        List<Note> expectedNotes = List.of(
                Note.builder().id(1L).content("Első lelet: negatív.").build(),
                Note.builder().id(2L).content("Második lelet: kontroll javasolt.").build(),
                Note.builder().id(3L).content("Harmadik lelet: recept felírva.").build()
        );

        when(noteRepository.findAll()).thenReturn(expectedNotes);

        // WHEN
        List<Note> actualNotes = noteService.getAllNotes();

        // THEN
        assertEquals(expectedNotes, actualNotes);
    }

    @Test
    void saveNoteHappyPath() {
        // GIVEN
        Note noteToSave = Note.builder().content("Új megjegyzés").build();
        Note savedNote = Note.builder().id(1L).content("Új megjegyzés").build();
        when(noteRepository.save(noteToSave)).thenReturn(savedNote);

        // WHEN
        Note actualNote = noteService.save(noteToSave);

        // THEN
        assertEquals(savedNote, actualNote);
    }

    @Test
    void editNoteHappyPath() {
        // GIVEN
        Note noteToEdit = Note.builder().id(1L).content("tartalom").build();
        Note editedNote = Note.builder().id(1L).content("Módosított tartalom").build();
        when(noteRepository.save(noteToEdit)).thenReturn(editedNote);

        // WHEN
        Note actualNote = noteService.edit(noteToEdit);

        // THEN
        assertEquals(editedNote,actualNote);
    }

    @Test
    void findByIdHappyPath() {
        // GIVEN
        Long id = 1L;
        Note expectedNote = Note.builder().id(id).content("Meglévő jegyzet").build();
        when(noteRepository.findById(id)).thenReturn(Optional.of(expectedNote));

        // WHEN
        Note actualNote = noteService.findById(id);

        // THEN
        assertEquals(expectedNote, actualNote);
        verify(noteRepository, times(1)).findById(id);
    }


    @Test
    void deleteByIdHappyPath() {
        // GIVEN
        Long id = 1L;

        // WHEN
        noteService.deleteById(id);

        // THEN
        verify(noteRepository, times(1)).deleteById(id); // Megtörtént a törlési hívás a repó felé?
    }
}
