package com.giftgo.transport_file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class FileParsingService {
    private static final Logger logger = LoggerFactory.getLogger(FileParsingService.class);

    public void parseIncomingFile(MultipartFile file) {
        logger.info("Parsing file");
        try {
            String str = "";
            InputStream inputStream = file.getInputStream();
            inputStream.read();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null) {
                while ((str = reader.readLine()) != null) {
                    System.out.println(str);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}
