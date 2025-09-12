package com.giftgo.transport_file.service;

import com.giftgo.transport_file.dto.Entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonFileOutputService {
    private static final Logger logger = LoggerFactory.getLogger(JsonFileOutputService.class);

    public void writeJsonToFile(List<Entity> entity) {
        if (entity != null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); // this will exclude fields without @Expose annotation
            String json = gson.toJson(entity);
            logger.trace("JSON: {}", json);
        } else {
            logger.error("No data to write to file");
        }
    }
}
