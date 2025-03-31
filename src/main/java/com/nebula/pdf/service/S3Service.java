package com.nebula.pdf.service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public String uploadFile(MultipartFile file) throws IOException {
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Create put object request
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFilename)
                .contentType(file.getContentType())
                .build();

        // Upload to S3
        PutObjectResponse response = s3Client.putObject(
                request,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        // Return the public URL of the uploaded file
        return String.format("https://%s.s3.%s.amazonaws.com/%s", 
            bucketName,
            region,
            uniqueFilename
        );
    }
} 