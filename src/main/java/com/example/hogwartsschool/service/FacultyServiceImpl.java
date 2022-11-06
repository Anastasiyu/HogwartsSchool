package com.example.hogwartsschool.service;

import com.example.hogwartsschool.entity.Faculty;
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

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findAllByColor(String color) {
        return facultyRepository.findAll();
    }

}
