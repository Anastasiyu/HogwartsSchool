package com.example.hogwartsschool.repository;

import com.example.hogwartsschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository implements JpaRepository {

    private final Student student;

    public StudentRepository(Student student) {
        this.student = student;
    }
}
