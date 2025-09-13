package com.giftgo.transport_file.service;

import com.giftgo.transport_file.exceptions.InvalidDataInFileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileParsingServiceTest {
    private static FileParsingService fileParsingService;

    MockMultipartFile validFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", ("18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());
    MockMultipartFile invalidDelimeterInData = new MockMultipartFile("test.txt", "test.txt", "text/plain", ("18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2:12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());
    MockMultipartFile invalidUuidInFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", ("18148426-89e1-11ee-b9d1|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());
    MockMultipartFile missingUuidInOneLine = new MockMultipartFile("test.txt", "test.txt", "text/plain", ("1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());
    MockMultipartFile stringInsteadOfDouble = new MockMultipartFile("test.txt", "test.txt", "text/plain", ("18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|test\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());

    @BeforeAll
    public static void setUp() {
        fileParsingService = new FileParsingService();
    }

    @Test
    public void testValidFileParsing() {
        assert (fileParsingService.parseIncomingFile(validFile).size() == 3);
    }

    @Test
    public void shouldThrowExceptionWhenUuidIsMissing() {
        assertThrows(InvalidDataInFileException.class, () -> fileParsingService.parseIncomingFile(missingUuidInOneLine));
    }

    @Test
    public void shouldThrowExceptionWhenReceivedStringInsteadOfDouble() {
        assertThrows(InvalidDataInFileException.class, () -> fileParsingService.parseIncomingFile(stringInsteadOfDouble));
    }

    @Test
    public void shouldThrowExceptionWhenReceivedInvalidUuid() {
        assertThrows(InvalidDataInFileException.class, () -> fileParsingService.parseIncomingFile(invalidUuidInFile));
    }

    @Test
    public void shouldThrowExceptionWhenDelimeterIsInvalid() {
        assertThrows(InvalidDataInFileException.class, () -> fileParsingService.parseIncomingFile(invalidDelimeterInData));
    }
}
