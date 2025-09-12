package com.giftgo.transport_file.service;

import com.giftgo.transport_file.dto.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ApplicationOrchestratorService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationOrchestratorService.class);
    private FileValidationService fileValidationService;
    private FileParsingService fileParsingService;
    private JsonOutputService jsonOutputService;

    @Autowired
    public ApplicationOrchestratorService(FileValidationService fileValidationService, FileParsingService fileParsingService, JsonOutputService jsonOutputService) {
        this.fileValidationService = fileValidationService;
        this.fileParsingService = fileParsingService;
        this.jsonOutputService = jsonOutputService;
    }

    public ByteArrayResource processFile(MultipartFile file) {
        if (file != null) {
            if (fileValidationService.incomingFileIsValid(file)) {
                List<Entity> entities = fileParsingService.parseIncomingFile(file);
                if (entities != null) {
                    ByteArrayResource resource = jsonOutputService.writeJsonToByteArrayResource(entities);
                    if (resource != null) {
                        return resource;
                    } else {
                        logger.error("Error while writing JSON");
                        return null;
                    }
                } else {
                    logger.warn("File contains invalid data");
                    return null;
                }
            } else {
                logger.warn("File is invalid");
                return null;
            }
        } else {
            logger.warn("No file to process");
            return null;
        }
    }

}
