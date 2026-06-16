package com.example.Beteg.nyilvantarto.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.domain.Note;
import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.DoctorService;
import com.example.Beteg.nyilvantarto.service.NoteService;
import com.example.Beteg.nyilvantarto.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Létrehozza a mockokat és beinjektálja
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }


    @Test
    void saveNoteHappyPath() throws Exception {
        // GIVEN
        Long patientId = 1L;
        Long doctorId = 2L;
        String content = "Minden lelet negatív.";

        Patient patient = Patient.builder().id(patientId).name("Beteg János").build();
        Doctor doctor = Doctor.builder().id(doctorId).name("Dr. Teszt").build();

        when(patientService.findById(patientId)).thenReturn(patient);
        when(doctorService.findById(doctorId)).thenReturn(doctor);

        // WHEN & THEN
        mockMvc.perform(post("/notes/save")
                        .sessionAttr("doctorId", doctorId) // Bent van a doki ID a sessionben?
                        .param("patientId", patientId.toString())
                        .param("content", content))
                .andExpect(status().is3xxRedirection()) // Átirányít?
                .andExpect(redirectedUrl("/patients/" + patientId)); // A megfelelő beteg profiljára dob vissza?

        verify(noteService, times(1)).save(any(Note.class));
    }

    @Test
    void deleteNoteHappyPath() throws Exception {
        // GIVEN
        Long noteId = 1L;
        Long patientId = 5L;

        Patient patient = Patient.builder().id(patientId).build();
        Note note = Note.builder().id(noteId).patient(patient).build();

        when(noteService.findById(noteId)).thenReturn(note);

        // WHEN & THEN
        mockMvc.perform(get("/notes/delete/{id}", noteId))
                .andExpect(status().is3xxRedirection()) // Átirányít?
                .andExpect(redirectedUrl("/patients/" + patientId)); // Törlés után a beteg adatlapjára visz vissza?

        verify(noteService, times(1)).deleteById(noteId);
    }
}