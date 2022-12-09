package com.example.hogwartsschool.exception;

public class AvatarNotFoundExeption extends RuntimeException {
    public final long id;

    public AvatarNotFoundExeption(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
