package com.example.hogwartsschool.exception;

public class FacultyNotFoundExeption extends RuntimeException{
    private  final  Long id;
    public FacultyNotFoundExeption(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
