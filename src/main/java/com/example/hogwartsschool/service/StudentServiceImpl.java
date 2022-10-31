package com.example.hogwartsschool.service;

import com.example.hogwartsschool.model.Student;
import com.example.hogwartsschool.repositories.StudentRepository;
import org.springframework.stereotype.Service;


import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {

        return studentRepository.findById(id).get();
    }

    public Student editStudent(long id, Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);


    }


    public Collection<Student> findByAge(int age) {
        return studentRepository.findAll();
    }

}
