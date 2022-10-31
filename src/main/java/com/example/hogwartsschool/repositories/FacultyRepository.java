package com.example.hogwartsschool.repositories;

import com.example.hogwartsschool.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
