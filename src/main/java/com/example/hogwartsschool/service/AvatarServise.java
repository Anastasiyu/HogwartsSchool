package com.example.hogwartsschool.service;


import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.entity.Avatar;
import com.example.hogwartsschool.entity.Student;
import com.example.hogwartsschool.exception.AvatarNotFoundExeption;
import com.example.hogwartsschool.exception.StudentNotFoundExeption;
import com.example.hogwartsschool.record.AvatarRecord;
import com.example.hogwartsschool.repositories.AvatarRepository;
import com.example.hogwartsschool.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvatarServise {
    private static final Logger logger = LoggerFactory.getLogger(AvatarServise.class);
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    private final RecordMapper recordMapper;
    @Value("${application.avatar.store}")
    private String avatarsDir;

    public AvatarServise(StudentRepository studentRepository,
                         AvatarRepository avatarRepository,
                         RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        this.recordMapper = recordMapper;
    }

    public AvatarRecord create(Long studentId, MultipartFile multipartFile) throws IOException {
        logger.debug("Method create was invoked");

        Student student = studentRepository.findById(studentId).orElseThrow(() -> {
            logger.error("Avatar with id = {} not found", studentId);
            return new StudentNotFoundExeption(studentId);
        });
        byte[] data = multipartFile.getBytes();

        String extension = Optional.ofNullable(multipartFile.getOriginalFilename())
                .map(fileName -> fileName.substring(multipartFile.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        Path path = Paths.get(avatarsDir).resolve(studentId + extension);
        Files.write(path, data);

        Avatar avatar = new Avatar();
        avatar.setData(data);
        avatar.setFileSize(data.length);
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        return recordMapper.toRecord(avatarRepository.save(avatar));
    }


    public org.springframework.data.util.Pair<byte[], String> readFromFs(long id) throws IOException {
        logger.debug("Method readFromFs was invoked");
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> {
            logger.error("Avatar with id = {} not found", id);
            return new AvatarNotFoundExeption(id);
        });
        return org.springframework.data.util.Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())), avatar.getMediaType());
    }


    public org.springframework.data.util.Pair<byte[], String> readFromDb(long id) {
        logger.debug("Method freadFromDb was invoked");
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> {
            logger.error("Avatar with id = {} not found", id);
            return new AvatarNotFoundExeption(id);
        });
        return org.springframework.data.util.Pair.of(avatar.getData(), avatar.getMediaType());
    }


    public List<AvatarRecord> findByPagination(int page, int size) {
        logger.debug("Method findByPagination was invoked");
        return avatarRepository.findAll(PageRequest.of(page, size)).get()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

}


