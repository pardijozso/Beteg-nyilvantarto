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

import java.time.LocalDate;
import java.util.List;

import com.example.Beteg.nyilvantarto.domain.Doctor;
import com.example.Beteg.nyilvantarto.domain.Patient;
import com.example.Beteg.nyilvantarto.service.DoctorService;
import com.example.Beteg.nyilvantarto.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PatientControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Létrehozza a mockokat és beinjektálja
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void getAllPatientsWithDoctorIdHappyPath() throws Exception {
        // GIVEN
        Long doctorId = 1L;
        List<Patient> expectedPatients = List.of(Patient.builder().id(1L).name("Beteg János").build());

        when(patientService.findByDoctorId(doctorId)).thenReturn(expectedPatients);

        // WHEN & THEN
        mockMvc.perform(get("/patients/list")
                        .param("doctorId", doctorId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/patients"))
                .andExpect(model().attribute("patients", expectedPatients)) // Orvos szerint szűrt lista van a modellben?
                .andExpect(model().attributeDoesNotExist("search")); // Keresési kulcsszó nincs átadva?

        verify(patientService, times(1)).findByDoctorId(doctorId);
    }

    @Test
    void getAllPatientsWithSearchHappyPath() throws Exception {
        // GIVEN
        String search = "János";
        List<Patient> expectedPatients = List.of(Patient.builder().id(1L).name("Beteg János").build());

        when(patientService.searchPatients(search)).thenReturn(expectedPatients);

        // WHEN & THEN
        mockMvc.perform(get("/patients/list")
                        .param("search", search))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/patients"))
                .andExpect(model().attribute("patients", expectedPatients)) // Keresett lista van a modellben?
                .andExpect(model().attribute("search", search)); // Keresőszó visszakerült a modellbe?

        verify(patientService, times(1)).searchPatients(search);
    }

    @Test
    void createPatientFormHappyPath() throws Exception {
        // GIVEN
        List<Doctor> expectedDoctors = List.of(Doctor.builder().id(1L).name("Dr. Teszt").build());

        when(doctorService.getAllDoctors()).thenReturn(expectedDoctors);

        // WHEN & THEN
        mockMvc.perform(get("/patients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create-patient"))
                .andExpect(model().attributeExists("patient")) // Új, üres Patient objektum bekerült a modellbe?
                .andExpect(model().attribute("doctors", expectedDoctors)); // Orvosok listája bekerült a modellbe?
    }

    @Test
    void savePatientHappyPath() throws Exception {
        // WHEN & THEN
        mockMvc.perform(post("/patients")
                        .param("name", "Új Beteg"))
                .andExpect(status().is3xxRedirection()) // Redirect miatt 3xx státuszt várunk, magyarul átirányított máshová?
                .andExpect(redirectedUrl("/patients/list")); // Ellenőrizzük a pontos átirányítási útvonalat?

        verify(patientService, times(1)).save(any(Patient.class));
    }

    @Test
    void viewPatientFormHappyPath() throws Exception {
        // GIVEN
        Long id = 1L;
        Patient patient = Patient.builder()
                .id(1L)
                .name("Nagy János")
                .birthPlace("Szolnok")
                .birthDate(LocalDate.parse("1990-01-01"))
                .motherName("Szabó Mária")
                .address("Budapest, Fő utca 1.")
                .diagnosis("Gyógyult")
                .build();

        when(patientService.findById(id)).thenReturn(patient);

        // WHEN & THEN
        mockMvc.perform(get("/patients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/view-patient"))
                .andExpect(model().attribute("patient", patient)) // Megfelelő beteg van a modellben?
                .andExpect(model().attribute("notes", patient.getNotes())); // Beteg megjegyzései átkerültek a modellbe?
    }

    @Test
    void editPatientFormHappyPath() throws Exception {
        // GIVEN
        Long id = 1L;
        Patient patient = Patient.builder().id(id).name("Beteg János").build();

        when(patientService.findById(id)).thenReturn(patient);

        // WHEN & THEN
        mockMvc.perform(get("/patients/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/edit-patient"))
                .andExpect(model().attribute("patient", patient)); // Szerkesztendő beteg benne van a modellben?
    }

    @Test
    void updatePatientHappyPath() throws Exception {
        // WHEN & THEN
        mockMvc.perform(post("/patients/edit")
                        .param("id", "1")
                        .param("name", "Módosított János"))
                .andExpect(status().is3xxRedirection()) // Átirányít?
                .andExpect(redirectedUrl("/patients/list")); // Sikeres mentés után a listára dob vissza?

        verify(patientService, times(1)).edit(any(Patient.class));
    }

    @Test
    void deletePatientHappyPath() throws Exception {
        // GIVEN
        Long id = 1L;

        // WHEN & THEN
        mockMvc.perform(get("/patients/delete/{id}", id))
                .andExpect(status().is3xxRedirection()) // Átirányít?
                .andExpect(redirectedUrl("/patients/list")); // Törlés után a listára dob vissza?

        verify(patientService, times(1)).deleteById(id);
    }

    @Test
    void listPatientsCleanHappyPath() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/patients/clear")
                        .sessionAttr("doctorId", 1L)) // Teszteljük, hogy volt benne érték
                .andExpect(status().is3xxRedirection()) // Átirányít?
                .andExpect(redirectedUrl("/patients/list")) // Törlés után a listára dob vissza?
                .andExpect(request().sessionAttributeDoesNotExist("doctorId")); // Törlődött a doctorId a sessionből?
    }
}
