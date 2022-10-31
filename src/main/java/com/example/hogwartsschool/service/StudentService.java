package com.example.hogwartsschool.service;

import com.example.hogwartsschool.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id, Student student);

    Student deleteStudent(long id);

    Collection<Student> findByAge(int age);
}
