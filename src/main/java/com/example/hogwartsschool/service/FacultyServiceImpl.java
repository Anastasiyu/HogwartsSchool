package com.example.hogwartsschool.service;

import com.example.hogwartsschool.model.Faculty;
import com.example.hogwartsschool.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacultyServiceImpl implements  FacultyService{

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findAll();
    }

}
