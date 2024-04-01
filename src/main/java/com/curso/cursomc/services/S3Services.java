package com.curso.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Services {

    @Autowired
    private AmazonS3 s3Client;
    @Value("${s3.bucket}")
    private String bucketName;

    private Logger LOG = LoggerFactory.getLogger(S3Services.class);
    public void uploadFile(String localFilePath){

        try {
            File file = new File(localFilePath);
            s3Client.putObject(new PutObjectRequest(bucketName, "teste", file));
        }
        catch (AmazonServiceException e) {
            LOG.info("AmazonServiceException: " + e.getErrorMessage());
            LOG.info("Status code: " + e.getErrorCode());
        }
        catch (AmazonClientException e) {
            LOG.info("AmazonClientException: " + e.getMessage());
        }

    }
}
