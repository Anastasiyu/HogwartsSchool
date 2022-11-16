package com.example.hogwartsschool.component;

import com.example.hogwartsschool.entity.Avatar;
import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.entity.Student;
import com.example.hogwartsschool.record.AvatarRecord;
import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    public StudentRecord toRecord(Student student) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentRecord.setFacultyRecord(toRecord(student.getFaculty()));
        }
        return studentRecord;
    }

    public FacultyRecord toRecord(Faculty faculty) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
        return facultyRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatarRecord.getId());
        avatarRecord.setMediaType(avatarRecord.getMediaType());
        avatarRecord.setUrl("http://localhost:8080/Avatar" + avatar.getId() + "/from-fs");
        return avatarRecord;

    }

    public Student toEntity(StudentRecord studentRecord) {
        Student student = new Student();
        student.setName(studentRecord.getName());
        student.setAge(studentRecord.getAge());
        if (studentRecord.getFacultyRecord() != null) {
            student.setFaculty(toEntity(studentRecord.getFacultyRecord()));
        }
        return student;
    }

    public Faculty toEntity(FacultyRecord facultyRecord){
        Faculty faculty = new Faculty();
        faculty.setName(facultyRecord.getName());
        faculty.setColor(facultyRecord.getColor());
        return faculty;
    }
}
