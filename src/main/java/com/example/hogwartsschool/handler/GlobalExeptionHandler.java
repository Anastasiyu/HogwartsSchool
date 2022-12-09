package com.example.hogwartsschool.handler;

import com.example.hogwartsschool.exception.FacultyNotFoundExeption;
import com.example.hogwartsschool.exception.StudentNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHandler {
    @ExceptionHandler(StudentNotFoundExeption.class)
    public ResponseEntity<String> handleStudentNotFoundExeption(StudentNotFoundExeption e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент с id = " + e.getId() + "не найден");

    }

    @ExceptionHandler(FacultyNotFoundExeption.class)
    public ResponseEntity<String> handleFacultyNotFoundExeption(FacultyNotFoundExeption e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Факультет с id = " + e.getId() + "не найден");

    }

}
