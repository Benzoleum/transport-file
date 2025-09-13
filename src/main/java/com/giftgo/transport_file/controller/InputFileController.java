package com.giftgo.transport_file.controller;

import com.giftgo.transport_file.exceptions.*;
import com.giftgo.transport_file.service.ApplicationOrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class InputFileController {
    private static final Logger logger = LoggerFactory.getLogger(InputFileController.class);
    private ApplicationOrchestratorService applicationOrchestratorService;

    @Autowired
    public InputFileController(ApplicationOrchestratorService applicationOrchestratorService) {
        this.applicationOrchestratorService = applicationOrchestratorService;
    }

    @PostMapping("/api/v1/upload")
    public ResponseEntity<?> receiveInputTxt(@RequestParam("inputFile") MultipartFile file) {
        logger.info("Received file");
        ByteArrayResource resource = null;

        try {
            resource = applicationOrchestratorService.processFile(file);
        } catch (EmptyInputFileException | InvalidDataInFileException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidFileReceivedException | InvalidContentTypeException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
        } catch (WritingToJsonException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
        }

        if (resource != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OutcomeFile.json").contentLength(resource.contentLength()).body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body("Error while processing file");
        }
    }
}
