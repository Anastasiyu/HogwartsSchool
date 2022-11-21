package com.example.hogwartsschool.controller;

import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.service.StudentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;

@Validated
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

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return studentService.totalCountOfStudents();
    }

    @GetMapping("/avarageAge")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lastStudents")
    public List<StudentRecord> lastStudents(@RequestParam @Min(1) @Max(15) int count) {
        return studentService.lastStudents(count);
    }
}
