package com.giftgo.transport_file.service;

import com.giftgo.transport_file.exceptions.IncorrectFileTypeException;
import com.giftgo.transport_file.exceptions.InvalidContentTypeException;
import com.giftgo.transport_file.exceptions.InvalidFileReceivedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidationService {
    private static final Logger logger = LoggerFactory.getLogger(FileValidationService.class);

    //fetches the value from applicationl.yml file. True by default.
    @Value("${fileValidation:true}")
    private boolean fileValidation;

    public void validateIncomingFile(MultipartFile file) {
        if (fileValidation) {
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
                logger.warn("Content type is not specified or not supported");
                throw new InvalidContentTypeException("The content type is not supported. Please use text/plain file");
            }

            // Rejecting files that are not .txt to conform with specifications
            if (!file.getOriginalFilename().endsWith(".txt")) {
                logger.warn("Invalid file extension. Only TXT files are allowed");
                throw new IncorrectFileTypeException("The file extension is not supported. Please use .txt file");
            }

            // Limiting the file size to 50MB. Can be increased, but file parsing logic would have to be changed to avoid CPU bottlenecks and memory issues
            // Potentially can be a flag in the application.yml to increase/decrease the limit based on available resources
            if (file.getSize() != 0 && file.getSize() < 50000000) {
                fileSize = file.getSize();
            } else {
                logger.warn("Empty file or exceeds the file size limit");
                throw new InvalidFileReceivedException("The file is invalid. Please check that the file is not empty and does not exceed 50MB");
            }

            logger.info("File name: {}", fileName);
            logger.info("Content Type: {}", contentType);
            logger.info("File size: {}", fileSize);
            logger.info("File is valid");
        } else {
            logger.info("Skipping file validation");
        }
    }
}
