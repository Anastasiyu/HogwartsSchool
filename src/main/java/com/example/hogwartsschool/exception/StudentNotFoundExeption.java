package com.example.hogwartsschool.exception;

public class StudentNotFoundExeption extends  RuntimeException{
    private  final  Long id;
    public StudentNotFoundExeption(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
