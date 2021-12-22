package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.services.S3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-presigned-url")
public class S3Controller {
    final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    // @PostMapping
    // public String generatePresignedUrl(@RequestBody @Valid String fileName) {
    //   return s3Service.generateUrl(fileName);
    // }

    // @GetMapping
    // public ResponseEntity<Object> getFile(HttpServletRequest request, @RequestBody(required = false) Map<String, String> params) {
    //     final String path = request.getServletPath();
    //     if (params.containsKey(FILE_NAME))
    //         return new ResponseEntity<>(s3Service.getObject(params.get(FILE_NAME)), HttpStatus.OK);
    // }

    @PostMapping
    public ResponseEntity<Object> saveFile(@RequestParam("extension") String extension) {
        return new ResponseEntity<>(s3Service.putObject(extension), HttpStatus.OK);
    }
}