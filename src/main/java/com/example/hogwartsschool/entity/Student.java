package com.example.hogwartsschool.entity;

import com.example.hogwartsschool.record.StudentRecord;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "students")
public class Student  {
    @ManyToOne
    @JoinColumn(name = "faculty id")
    private Student student;


    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Студент №" + id + ": "+ name + ", "+ age;
    }


}
