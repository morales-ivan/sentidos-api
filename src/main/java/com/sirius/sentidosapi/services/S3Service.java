package com.sirius.sentidosapi.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.*;

@Service
@Slf4j
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket}")
    private String s3BucketName;

    public String generateUrl(String fileName, HttpMethod httpMethod) {
         try {
            // Set the presigned URL to expire after one hour.
            final java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(this.s3BucketName, fileName)
                            .withMethod(httpMethod)
                            .withExpiration(expiration);
            generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
            return this.amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            return null;
        }
    }

    // @Async
    // public String getObject(String fileName) {
    //     if (!amazonS3.doesObjectExist(this.s3BucketName, fileName))
    //         return "File does not exist";
    //     log.info("Generating pre-signed URL for file name {}", fileName);
    //     return generateUrl(fileName, HttpMethod.GET);
    // }

    @Async
    public Map<String, ?> putObject(String extension) {
        String fileName = UUID.randomUUID().toString() + extension;
        log.info("Generating pre-signed URL to upload an object.");
        String generatedUrl = generateUrl(fileName, HttpMethod.PUT);
        String objectUrl = "https://" + this.s3BucketName + ".s3.amazonaws.com/" + fileName;
        Map<String, String> map = new HashMap<String, String>();

        map.put("uploadUrl", generatedUrl);
        map.put("objectUrl", objectUrl);
        return map;
    }
}