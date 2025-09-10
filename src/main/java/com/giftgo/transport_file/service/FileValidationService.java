package com.giftgo.transport_file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidationService {
    private static final Logger logger = LoggerFactory.getLogger(FileValidationService.class);

    public void validateIncomingFile(MultipartFile file) throws HttpMediaTypeNotSupportedException {
        logger.info("Validating file");
        String fileName = "";
        String contentType = "";
        long fileSize = 0;

        if (file.getOriginalFilename() != null) {
            fileName = file.getOriginalFilename();
        }

        // Rejecting requests with empty content type or anything other than text/plain
        if (file.getContentType() != null && file.getContentType().equals("text/plain")) {
            contentType = file.getContentType();
        } else {
            throw new HttpMediaTypeNotSupportedException("Content type is not specified or not supported");
        }

        // Rejecting file that are not .txt to conform with specifications
        if (!file.getOriginalFilename().endsWith(".txt")) {
            throw new HttpMediaTypeNotSupportedException("Invalid file extension. Only .txt files are allowed");
        }

        // Limiting the file size to 50MB. Can be increased, but file parsing logic would have to be changed to avoid CPU bottlenecks and memory issues
        if (file.getSize() != 0 && file.getSize() < 50000000) {
            fileSize = file.getSize();
        } else {
            throw new HttpMediaTypeNotSupportedException("Empty file or exceeds the limit");
        }

        logger.info("File name: " + fileName);
        logger.info("Content Type: " + contentType);
        logger.info("File size: " + fileSize);
        logger.info("File is valid");
    }
}
