package com.giftgo.transport_file.service;

import com.giftgo.transport_file.dto.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileParsingService {
    private static final Logger logger = LoggerFactory.getLogger(FileParsingService.class);

    public List<Entity> parseIncomingFile(MultipartFile file) {
        logger.info("Parsing file");
        ArrayList<Entity> entities = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            inputStream.read();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null) {
                String fullLine;
                while ((fullLine = reader.readLine()) != null) {
                    String[] line = fullLine.split("\\|");
                    UUID uuid = UUID.fromString(line[0]);
                    String id = line[1];
                    String name = line[2];
                    String likes = line[3];
                    String transport = line[4];
                    double avgSpeed = Double.parseDouble(line[5]);
                    double topSpeed = Double.parseDouble(line[6]);
                    logger.trace("UUID: {}, ID: {}, Name: {}, Likes: {}, Transport: {}, Avg Speed: {}, Top Speed: {}", uuid, id, name, likes, transport, avgSpeed, topSpeed);
                    entities.add(new Entity(uuid, id, name, likes, transport, avgSpeed, topSpeed));
                }
            }
        } catch (IOException e) {
            logger.error("Error while parsing file", e);
        }
        return entities;
    }
}
