package com.example.hogwartsschool.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(StudentNotFoundExeption.class)
    public ResponseEntity<String> handleStudentNotFoundExeption(StudentNotFoundExeption e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(String.format("Студент с id = %id не найден!", e.getId()));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(FacultyNotFoundExeption.class)
    public ResponseEntity<String> handleFacultyNotFoundExeption(FacultyNotFoundExeption e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Факкультет с id = %id не найден!", e.getId()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AvatarNotFoundExeption.class)
    public ResponseEntity<String> handleAvatarNotFoundExeption(AvatarNotFoundExeption e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Аватар с id = %id не найден!", e.getId()));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        e.getBindingResult().getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining())
                );
    }

}
