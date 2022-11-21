package com.example.hogwartsschool.controller;


import com.example.hogwartsschool.record.AvatarRecord;
import com.example.hogwartsschool.service.AvatarServise;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("Avatar")
public class AvatarController {

    private final AvatarServise avatarServise;

    public AvatarController(AvatarServise avatarServise) {
        this.avatarServise = avatarServise;
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AvatarRecord create(@RequestParam MultipartFile avatar,
                               @RequestParam long studentId) throws IOException {

        return avatarServise.create(studentId, avatar);
    }


    @GetMapping("/{id}/from-fs")
    public ResponseEntity<byte[]> readFromFs(@PathVariable long id) throws IOException {
        org.springframework.data.util.Pair<byte[], String> pair = avatarServise.readFromFs(id);

        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }


    @GetMapping("/{id}/from-db")
    public ResponseEntity<byte[]> readFromDb(@PathVariable long id) throws IOException {
        org.springframework.data.util.Pair<byte[], String> pair = avatarServise.readFromDb(id);
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }

    @GetMapping
    public List<AvatarRecord> findByPagination(@RequestParam @Min(0) int page,
                                               @RequestParam @Max(0) int size) {
        return avatarServise.findByPagination(page, size);
    }

}
