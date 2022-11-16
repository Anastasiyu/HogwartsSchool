package com.example.hogwartsschool.service;

import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.exception.FacultyNotFoundExeption;
import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService{

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(long id) {
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id)));

    }

    public FacultyRecord update(long id,
                                FacultyRecord facultyRecord) {

        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }


    public FacultyRecord delete(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id));
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> findByColor(String color) {
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByNameOrColor(String nameOrColor,
                                                       String color) {
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(nameOrColor, nameOrColor).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findStudentByFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id)).getStudents().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
}
