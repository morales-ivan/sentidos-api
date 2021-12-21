package com.sirius.sentidosapi.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket}")
    private String s3BucketName;

    public String generateUrl(String fileName) {
         try {
            // Set the presigned URL to expire after one hour.
            final java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            log.info("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(this.s3BucketName, fileName)
                            .withMethod(HttpMethod.PUT)
                            .withExpiration(expiration);
            return this.amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            return null;
        }
    }
}