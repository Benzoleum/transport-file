package com.giftgo.transport_file.service;

import com.giftgo.transport_file.dto.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonOutputServiceTest {

    private static JsonOutputService jsonOutputService;
    private static List<Entity> validEntityList = new ArrayList<>();
    private static List<Entity> nullEntityList = null;
    private static List<Entity> emptyEntityList = new ArrayList<>();


    @BeforeAll
    public static void setUp() {
        jsonOutputService = new JsonOutputService();
        validEntityList.add(new Entity(UUID.fromString("18148426-89e1-11ee-b9d1-0242ac120002"), "1X1D14", "John Smith", "Likes Apricots", "Rides A Bike", 6.2, 12.1));
        validEntityList.add(new Entity(UUID.fromString("3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7"), "2X2D24", "Mike Smith", "Likes Grape", "Drives an SUV", 35.0, 95.5));
        validEntityList.add(new Entity(UUID.fromString("1afb6f5d-a7c2-4311-a92d-974f3180ff5e"), "3X3D35", "Jenny Walters", "Likes Avocados", "Rides A Scooter", 8.5, 15.3));
    }

    @Test
    public void testValidEntityListToJson() throws IOException {
        assertTrue(jsonOutputService.writeJsonToByteArrayResource(validEntityList).contentLength() != 0);
    }

    @Test
    public void testNullEntityListToJson_returnsNull() {
        assertTrue(jsonOutputService.writeJsonToByteArrayResource(nullEntityList) == null);
    }

    @Test
    public void testEmptyEntityListToJson_returnsNull() {
        assertTrue(jsonOutputService.writeJsonToByteArrayResource(emptyEntityList) == null);
    }
}
