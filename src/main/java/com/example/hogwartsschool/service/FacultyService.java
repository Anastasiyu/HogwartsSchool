package com.example.hogwartsschool.service;

import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.exception.FacultyNotFoundExeption;
import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.repositories.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        logger.debug("Method create was invoked");
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(long id) {
        logger.debug("Method read was invoked");
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty with id = {} not found", id);
            return new FacultyNotFoundExeption(id);
        }));

    }

    public FacultyRecord update(long id,
                                FacultyRecord facultyRecord) {
        logger.debug("Method update was invoked");
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty with id = {} not found", id);
            return new FacultyNotFoundExeption(id);
        });
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }


    public FacultyRecord delete(long id) {
        logger.debug("Method delete was invoked");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty with id = {} not found", id);
            return new FacultyNotFoundExeption(id);
        });
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> findByColor(String color) {
        logger.debug("Method findByColor was invoked");
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByNameOrColor(String nameOrColor) {
        logger.debug("Method findByNameOrColor was invoked");
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(nameOrColor, nameOrColor).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findStudentByFaculty(long id) {
        logger.debug("Method findStudentByFaculty was invoked");
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .map(students ->
                        students.stream()
                                .map(recordMapper::toRecord)
                                .collect(Collectors.toList())
                )
                .orElseThrow(() -> {
                    logger.debug("Faculty with id = {} not found", id);
                    return new FacultyNotFoundExeption(id);
                });
    }

    public String findTheLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}