package com.example.hogwartsschool.service;

import com.example.hogwartsschool.entity.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Long id, Student student);

    void deleteStudent(Long id);

    Collection<Student> findAllByAge(int age);
}
