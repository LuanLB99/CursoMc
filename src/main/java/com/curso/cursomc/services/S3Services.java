package com.curso.cursomc.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.curso.cursomc.services.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Services {

    @Autowired
    private AmazonS3 s3Client;
    @Value("${s3.bucket}")
    private String bucketName;

    private Logger LOG = LoggerFactory.getLogger(S3Services.class);
    public URI uploadFile(MultipartFile multipartFile){

        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(is, fileName, contentType);
        }
        catch (IOException e) {
            throw new FileException("Erro de Io: " + e.getMessage());
        }

    }

    public URI uploadFile(InputStream is, String fileName, String contentType){

        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            s3Client.putObject(bucketName, fileName, is, meta);

            return s3Client.getUrl(bucketName, fileName).toURI();
        }
         catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI" + e.getMessage());
        }

    }
}

