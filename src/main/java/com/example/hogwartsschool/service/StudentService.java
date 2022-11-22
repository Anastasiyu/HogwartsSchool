package com.example.hogwartsschool.service;

import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.entity.Student;
import com.example.hogwartsschool.exception.StudentNotFoundExeption;
import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.repositories.FacultyRepository;
import com.example.hogwartsschool.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
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
        logger.debug("Method create was invoked");
        Student student = recordMapper.toEntity(studentRecord);
        Faculty faculty = Optional.ofNullable(studentRecord.getFacultyRecord())
                .map(FacultyRecord::getId)
                .flatMap(facultyRepository::findById)
                .orElse(null);
        student.setFaculty(faculty);
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord read(long id) {
        logger.debug("Method read was invoked");
        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student with id = {} not found", id);
            return new StudentNotFoundExeption(id);
        }));
    }

    public StudentRecord update(long id,
                                StudentRecord studentRecord) {
        logger.debug("Method update was invoked");
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student with id = {} not found", id);
            return new StudentNotFoundExeption(id);
        });
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
        logger.debug("Method delete was invoked");
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student with id = {} not found", id);
            return new StudentNotFoundExeption(id);
        });
        studentRepository.delete(student);
        return recordMapper.toRecord(student);


    }


    public Collection<StudentRecord> findByAge(int age) {
        logger.debug("Method findByAge was invoked");

        return studentRepository.findAllByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int minAge,
                                                      int maxAge) {
        logger.debug("Method findByAgeBetween was invoked");
        return studentRepository.findAllByAgeBetween(minAge, maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord findFacultyByStudent(long id) {
        logger.debug("Method findFacultyByStudent was invoked");
        return read(id).getFacultyRecord();
    }

    public int totalCountOfStudents() {
        logger.debug("Method totalCountOfStudents was invoked");
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        logger.debug("Method averageAgeOfStudents was invoked");
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        logger.debug("Method lastStudents was invoked");
        return studentRepository.lastStudents(count).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Stream<String> findStudentNameWhichStartedWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted();
    }

    public double findStudentAverageAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElseThrow();
    }


}
