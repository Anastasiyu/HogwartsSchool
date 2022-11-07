package com.example.hogwartsschool.controller;

import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.record.FacultyRecord;

import com.example.hogwartsschool.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public FacultyRecord create(@RequestBody @Valid FacultyRecord facultyRecord) {

        return facultyService.create(facultyRecord);
    }


    @GetMapping("{id}")
    public FacultyRecord read(@PathVariable Long id) {
        return facultyService.read(id);
    }



    @PutMapping("{id}")
    public FacultyRecord update(@PathVariable Long id,
                                @RequestBody @Valid FacultyRecord facultyRecord) {
        return facultyService.update(id, facultyRecord);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<FacultyRecord>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


}
