package com.giftgo.transport_file.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileValidationServiceTest {
    private static FileValidationService fileValidationService;

    MockMultipartFile validFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n + 3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n + 1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3".getBytes());
    MockMultipartFile invalidExtension = new MockMultipartFile("test.jpeg", "test.jpeg", "text/plain", "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n + 3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n + 1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3".getBytes());
    MockMultipartFile invalidContentType = new MockMultipartFile("test.txt", "test.txt", "text/html", "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n + 3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n + 1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3".getBytes());
    MockMultipartFile fileTooLarge = new MockMultipartFile("test.txt", "test.txt", "text/plain", new byte[50_000_001]
    );


    @BeforeAll
    public static void setUp() {
        fileValidationService = new FileValidationService();
        ReflectionTestUtils.setField(fileValidationService, "fileValidation", true); // required to enable file validation
    }

    @Test
    public void testValidIncomingFileIsValid() {
        assertTrue(fileValidationService.incomingFileIsValid(validFile));
    }

    @Test
    public void testFileWithInvalidExtension() {
        assertFalse(fileValidationService.incomingFileIsValid(invalidExtension));
    }

    @Test
    public void testFileWithInvalidContentType() {
        assertFalse(fileValidationService.incomingFileIsValid(invalidContentType));
    }

    @Test
    public void testFileWithTooLargeContent() {
        assertFalse(fileValidationService.incomingFileIsValid(fileTooLarge));
    }

}
