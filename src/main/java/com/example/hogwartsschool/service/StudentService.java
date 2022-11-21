package com.example.hogwartsschool.service;

import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.entity.Student;
import com.example.hogwartsschool.exception.StudentNotFoundExeption;
import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.repositories.FacultyRepository;
import com.example.hogwartsschool.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService {


    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    private final RecordMapper recordMapper;

    public StudentRecord create(StudentRecord studentRecord) {
        Student student = recordMapper.toEntity(studentRecord);
        Faculty faculty = Optional.ofNullable(studentRecord.getFacultyRecord())
                .map(FacultyRecord::getId)
                .flatMap(facultyRepository::findById)
                .orElse(null);
        student.setFaculty(faculty);
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord read(long id) {

        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id)));
    }

    public StudentRecord update(long id,
                                StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        oldStudent.setFaculty(
                Optional.ofNullable(studentRecord.getFacultyRecord())
                        .map(FacultyRecord::getId)
                        .flatMap(facultyRepository::findById)
                        .orElse(null)
        );
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord delete(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id));
        studentRepository.delete(student);
        return recordMapper.toRecord(student);


    }


    public Collection<StudentRecord> findByAge(int age) {

        return studentRepository.findAllByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int minAge,
                                                      int maxAge) {
        return studentRepository.findAllByAgeBetween(minAge, maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord findFacultyByStudent(long id) {
        return read(id).getFacultyRecord();
    }

    public int totalCountOfStudents() {
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        return studentRepository.lastStudents(count).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
}
