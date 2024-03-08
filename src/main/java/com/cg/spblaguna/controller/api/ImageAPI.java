package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.service.image.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageAPI {
    @Autowired
    private ImageServiceImpl imageService;
    @PostMapping
    public ResponseEntity<?> upload(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(imageService.saveImage(file), HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        imageService.deleteImage(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllImages() throws IOException {
        return new ResponseEntity<>(imageService.findAllImageResDTOs(), HttpStatus.OK);
    }
}
