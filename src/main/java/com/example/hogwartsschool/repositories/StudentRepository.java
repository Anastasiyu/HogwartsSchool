package com.example.hogwartsschool.repositories;

import com.example.hogwartsschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
