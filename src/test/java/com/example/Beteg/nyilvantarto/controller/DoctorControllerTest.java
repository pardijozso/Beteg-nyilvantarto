package com.example.Beteg.nyilvantarto.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class DoctorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // létrehozza a mockokat és beinjektálja
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void listDoctorsHappyPath() throws Exception {
        // GIVEN
        Doctor doctor1 = Doctor.builder().id(1L).name("Dr. Teszt Elek").build();
        Doctor doctor2 = Doctor.builder().id(2L).name("Dr. Mindenható").build();
        List<Doctor> expectedDoctors = List.of(doctor1, doctor2);

        when(doctorService.getAllDoctors()).thenReturn(expectedDoctors);

        // WHEN & THEN
        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctors/doctor-list"))
                .andExpect(model().attributeExists("doctors")) // A modell tartalmazza a "doctors" kulcsot?
                .andExpect(model().attribute("doctors", expectedDoctors)); // Az átadott lista megegyezik a várttal?

        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    void showCreateFormHappyPath() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/doctors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctors/create-doctor"))
                .andExpect(model().attributeExists("doctor")); // Egy új, üres Doctor objektum bekerült a modellbe?
    }

    @Test
    void saveDoctorHappyPath() throws Exception {
        // WHEN & THEN
        mockMvc.perform(post("/doctors")
                        .param("name", "Dr. Új Orvos"))
                .andExpect(status().is3xxRedirection()) // redirect miatt 3xx státuszt várunk, maagyarul átírányított máshová
                .andExpect(redirectedUrl("/doctors")); // Ellenőrizzük a pontos átirányítási útvonalat

        verify(doctorService, times(1)).save(any(Doctor.class));
    }

    @Test
    void selectDoctorHappyPath() throws Exception {
        // GIVEN
        Long id = 1L;

        // WHEN & THEN
        mockMvc.perform(get("/doctors/select/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/list?doctorId=" + id)) //az url-be bele rakta az id-t?
                .andExpect(request().sessionAttribute("doctorId", id)); //sessionbe mentette e?
    }
}