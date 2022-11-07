package com.example.hogwartsschool.repositories;

import com.example.hogwartsschool.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty>  findAllByColor(String color);


}

