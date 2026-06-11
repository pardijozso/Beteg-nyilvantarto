package com.example.Beteg.nyilvantarto.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class patients {

    private Long id;
    private String name;

    @Column(name = "birth_place")
    private String birthPlace;

    @Controller(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "mother_name")
    private String motherName;

    private String address;

    @Column(length = 2000)
    private String diagnosis;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //Kapcsolat a notes táblával
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes;

}
