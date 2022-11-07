package com.example.hogwartsschool.controller;

import com.example.hogwartsschool.entity.Student;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;


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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<StudentRecord>> findStudents(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


}
