package com.example.hogwartsschool.service;

import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.entity.Avatar;
import com.example.hogwartsschool.entity.Student;
import com.example.hogwartsschool.exception.StudentNotFoundExeption;
import com.example.hogwartsschool.record.StudentRecord;
import com.example.hogwartsschool.repositories.AvatarRepository;
import com.example.hogwartsschool.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentService {


    public StudentService(StudentRepository studentRepository,
                          RecordMapper recordMapper,
                          AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
        this.avatarRepository = avatarRepository;
    }

    private final AvatarRepository avatarRepository;

    private final StudentRepository studentRepository;
    private final RecordMapper recordMapper;
    @Value("${Avatars.dir.path}")
    private String avatarsDir;


    public StudentRecord create(StudentRecord studentRecord) {
        return recordMapper.toRecord(studentRepository.save(recordMapper.toEntity(studentRecord)));
    }

    public StudentRecord read(long id) {

        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id)));
    }

    public StudentRecord update(long id, StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
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

    public Collection<StudentRecord> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Avatar readAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        StudentRecord studentRecord = read(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream ok = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bok = new BufferedOutputStream(ok, 1024)
        ) {
            bis.transferTo(bok);

        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent((Student) studentRecord);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
