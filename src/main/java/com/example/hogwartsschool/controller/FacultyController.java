package com.example.hogwartsschool.controller;

import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.service.FacultyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {

        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty, @PathVariable long id) {
        Faculty foundFaculty = facultyService.editFaculty(id, faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findAllByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


}
