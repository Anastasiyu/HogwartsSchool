package com.example.hogwartsschool.controller;

import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


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
    public FacultyRecord delete(@PathVariable Long id) {

        return facultyService.delete(id);
    }

    @GetMapping(params = "!colorOrName")
    public Collection<FacultyRecord> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping(params = "colorOrName")
    public Collection<FacultyRecord> findByNameOrColor(@RequestParam String nameOrColor) {
        return facultyService.findByNameOrColor(nameOrColor);
    }

    @GetMapping("/{id}/student")
    public Collection<StudentRecord> findStudentByFaculty(@PathVariable Long id) {
        return facultyService.findStudentByFaculty(id);

    }

}
