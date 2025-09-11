package com.giftgo.transport_file.controller;

import com.giftgo.transport_file.service.FileParsingService;
import com.giftgo.transport_file.service.FileValidationService;
import com.giftgo.transport_file.service.JsonFileOutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class InputFileController {
    private static final Logger logger = LoggerFactory.getLogger(InputFileController.class);
    private FileValidationService fileValidationService;
    private FileParsingService fileParsingService;
    private JsonFileOutputService jsonFileOutputService;

    @Autowired
    public InputFileController(FileValidationService fileValidationService, FileParsingService fileParsingService, JsonFileOutputService jsonFileOutputService) {
        this.fileValidationService = fileValidationService;
        this.fileParsingService = fileParsingService;
        this.jsonFileOutputService = jsonFileOutputService;
    }

    @PostMapping("/api/v1/upload")
    public ResponseEntity<String> receiveInputTxt(@RequestParam("inputFile") MultipartFile file) {
        logger.info("Received file");
        if (fileValidationService.incomingFileIsValid(file)) {
           jsonFileOutputService.writeJsonToFile(fileParsingService.parseIncomingFile(file));
        } else {
            return ResponseEntity.badRequest().body("Invalid file");
        }
        return ResponseEntity.ok("File received");
    }
}
