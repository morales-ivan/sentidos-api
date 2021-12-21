package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.services.S3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-presigned-url")
public class S3Controller {
    final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping
    public String generatePresignedUrl(@RequestBody @Valid String fileName) {
      return s3Service.generateUrl(fileName);
    }
}