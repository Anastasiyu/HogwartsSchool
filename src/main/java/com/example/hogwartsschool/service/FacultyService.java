package com.example.hogwartsschool.service;

import com.example.hogwartsschool.entity.Faculty;


import java.util.Collection;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(Long id);

    Faculty editFaculty(Long id, Faculty faculty);

    void deleteFaculty(Long id);

    Collection<Faculty> findAllByColor(String color);
}
