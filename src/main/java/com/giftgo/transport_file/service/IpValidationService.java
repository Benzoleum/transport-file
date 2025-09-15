package com.giftgo.transport_file.service;

import com.giftgo.transport_file.controller.InputFileController;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class IpValidationService {
    private static final Logger logger = LoggerFactory.getLogger(IpValidationService.class);

    // http://ip-api.com/json/24.48.0.1
    public boolean validateIpAddress(HttpServletRequest incomingRequest) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest outgoingRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://ip-api.com/json/" + incomingRequest.getRemoteAddr()))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(outgoingRequest, HttpResponse.BodyHandlers.ofString());
        logger.info("IP validation request status {}", response.statusCode());
        logger.info("IP validation response: {}", response.body());

        return false;
    }
}
