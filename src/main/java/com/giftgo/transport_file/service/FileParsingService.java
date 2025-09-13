package com.giftgo.transport_file.service;

import com.giftgo.transport_file.dto.Entity;
import com.giftgo.transport_file.exceptions.EmptyInputFileException;
import com.giftgo.transport_file.exceptions.InvalidDataInFileException;
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
                    if (line.length != 7) {
                        logger.warn("The data format is invalid. Expected 7 columns, got {}", line.length);
                        throw new InvalidDataInFileException("The data format is invalid. Expected 7 columns, got " + line.length);
                    }
                    UUID uuid;
                    String id;
                    String name;
                    String likes;
                    String transport;
                    double avgSpeed;
                    double topSpeed;

                    // Ideally we might want to skip invalid lines and proceed with existing ones while logging invalid entries
                    try {
                        uuid = UUID.fromString(line[0]);
                        id = line[1];
                        name = line[2];
                        likes = line[3];
                        transport = line[4];
                        avgSpeed = Double.parseDouble(line[5]);
                        topSpeed = Double.parseDouble(line[6]);
                    } catch (Exception e) {
                        throw new InvalidDataInFileException("Invalid data in file, please check the input data");
                    }
                    logger.trace("UUID: {}, ID: {}, Name: {}, Likes: {}, Transport: {}, Avg Speed: {}, Top Speed: {}", uuid, id, name, likes, transport, avgSpeed, topSpeed);
                    // Create POJOs from lines in the file and add to a list for further processing
                    entities.add(new Entity(uuid, id, name, likes, transport, avgSpeed, topSpeed));
                }
            } else {
                logger.warn("No data to parse");
                throw new EmptyInputFileException("The file is empty. Please check the file and try again");
            }
        } catch (IOException e) {
            logger.error("Error while parsing the file", e);
            throw new RuntimeException("Error while parsing the file");
        }
        return entities;
    }
}
