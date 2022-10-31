package com.example.hogwartsschool.repository;

import com.example.hogwartsschool.model.Faculty;
import com.example.hogwartsschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public class FacultyRepository implements JpaRepository {

    private final Faculty faculty;

    public FacultyRepository(Faculty faculty) {
        this.faculty = faculty;
    }
}
