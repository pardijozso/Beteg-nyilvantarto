package com.example.Beteg.nyilvantarto.repository;

import com.example.Beteg.nyilvantarto.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
