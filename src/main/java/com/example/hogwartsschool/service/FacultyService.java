package com.example.hogwartsschool.service;

import com.example.hogwartsschool.model.Faculty;


import java.util.Collection;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(long id, Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> findByColor(String color);
}
