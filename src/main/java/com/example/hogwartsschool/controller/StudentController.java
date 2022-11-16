package com.example.hogwartsschool.controller;

import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentRecord create(@RequestBody @Valid StudentRecord studentRecord) {
        return studentService.create(studentRecord);
    }


    @GetMapping("{id}")
    public StudentRecord read(@PathVariable Long id) {
        return studentService.read(id);
    }


    @PutMapping("{id}")
    public StudentRecord update(@PathVariable Long id,
                                @RequestBody @Valid StudentRecord studentRecord) {
        return studentService.update(id, studentRecord);
    }


    @DeleteMapping("{id}")
    public StudentRecord delete(@PathVariable Long id) {

        return studentService.delete(id);
    }

    @GetMapping
    public Collection<StudentRecord> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);

    }

    @GetMapping(value = "/findStudentsBetweenAge")
    public Collection<StudentRecord> findByAgeBetween(@RequestParam("min") int minAge,
                                                      @RequestParam("max") int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);

    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord findFacultyByStudent(@PathVariable long id) {
        return studentService.findFacultyByStudent(id);
    }
}
