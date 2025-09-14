package com.giftgo.transport_file.controller;

import com.giftgo.transport_file.exceptions.EmptyInputFileException;
import com.giftgo.transport_file.exceptions.InvalidContentTypeException;
import com.giftgo.transport_file.exceptions.InvalidDataInFileException;
import com.giftgo.transport_file.exceptions.InvalidFileReceivedException;
import com.giftgo.transport_file.service.ApplicationOrchestratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InputFileController.class)
public class InputControllerTest {
    MockMultipartFile validFile = new MockMultipartFile("inputFile", "test.txt", "text/plain", ("18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());

    MockMultipartFile emptyFile = new MockMultipartFile("inputFile", "test.txt", "text/plain", "".getBytes());

    MockMultipartFile invalidExtension = new MockMultipartFile("inputFile", "test.jpeg", "text/plain", ("18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1\n" + "3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5\n" + "1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3").getBytes());

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationOrchestratorService orchestrator;

    @Test
    void parseValidFile() throws Exception {
        byte[] json = "[{\"name\": \"John Smith\", \"transport\": \"Rides A Bike\", \"topSpeed\": 12.1},{\"name\": \"Mike Smith\", \"transport\": \"Drives an SUV\", \"topSpeed\": 95.5},{\"name\": \"Jenny Walters\", \"transport\": \"Rides A Scooter\", \"topSpeed\": 15.3}]\n".getBytes();

        ByteArrayResource resource = new ByteArrayResource(json);
        when(orchestrator.processFile(org.mockito.ArgumentMatchers.any())).thenReturn(resource);

        mockMvc.perform(multipart("/api/v1/upload").file(validFile)).andExpect(status().isOk()).andExpect(header().string("Content-Disposition", "attachment; filename=OutcomeFile.json")).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().bytes(json));
    }

    @Test
    void testBadRequest() throws Exception {

        doThrow(new EmptyInputFileException("empty")).when(orchestrator).processFile(org.mockito.ArgumentMatchers.any());
        mockMvc.perform(multipart("/api/v1/upload").file(emptyFile)).andExpect(status().isBadRequest());

        doThrow(new InvalidDataInFileException("invalid")).when(orchestrator).processFile(org.mockito.ArgumentMatchers.any());
        mockMvc.perform(multipart("/api/v1/upload").file(emptyFile)).andExpect(status().isBadRequest());
    }

    @Test
    void testUnsupportedMediaType() throws Exception {

        doThrow(new InvalidFileReceivedException("bad file")).when(orchestrator).processFile(org.mockito.ArgumentMatchers.any());
        mockMvc.perform(multipart("/api/v1/upload").file(invalidExtension)).andExpect(status().isUnsupportedMediaType());

        doThrow(new InvalidContentTypeException("bad type")).when(orchestrator).processFile(org.mockito.ArgumentMatchers.any());
        mockMvc.perform(multipart("/api/v1/upload").file(invalidExtension)).andExpect(status().isUnsupportedMediaType());
    }
}
