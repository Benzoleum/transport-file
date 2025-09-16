package com.giftgo.transport_file.service;

import com.giftgo.transport_file.dto.IncomingRequest;
import com.google.gson.Gson;
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
import java.util.HashMap;

@Service
public class IpValidationService {
    private static final Logger logger = LoggerFactory.getLogger(IpValidationService.class);

    // http://ip-api.com/json/24.48.0.1
    // http://ip-api.com/json/{query}?fields=1098242
    public boolean validateIpAddress(HttpServletRequest incomingRequest) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

        HttpRequest outgoingRequest = HttpRequest.newBuilder()
//                .uri(URI.create("http://ip-api.com/json/" + incomingRequest.getRemoteAddr()tRemoteAddr() + "?fields=1098242"))
//                .uri(URI.create("http://ip-api.com/json/102.129.228.0?fields=1098242")) // hardcoding IP for testing (originating from UK)
                .uri(URI.create("http://ip-api.com/json/1.178.180.0?fields=1098242")) // hardcoding IP for testing (originating from US)
                .timeout(Duration.ofSeconds(10)).header("Accept", "application/json").GET().build();

        HttpResponse<String> response = client.send(outgoingRequest, HttpResponse.BodyHandlers.ofString());
        logger.info("IP validation request status {}", response.statusCode());
        logger.trace("IP validation response: {}", response.body());

        IncomingRequest incomingRequestDto = buildIncomingRequestFromJson(response.body());
        if (incomingRequestDto.getStatus().equalsIgnoreCase("success")) {
            if (!incomingRequestDto.getCountryCode().equalsIgnoreCase("CN")
                    && !incomingRequestDto.getCountryCode().equalsIgnoreCase("US")
                    && !incomingRequestDto.getCountryCode().equalsIgnoreCase("ES")) {
                if (!incomingRequestDto.getIsp().equalsIgnoreCase("AWS")
                        && !incomingRequestDto.getIsp().equalsIgnoreCase("GCP")
                        && !incomingRequestDto.getIsp().equalsIgnoreCase("Azure")) {
                    logger.debug("Request is coming from a whitelisted IP");
                    return true;
                }
            }
        }
        logger.warn("Request is coming from a restricted IP");
        return false;
    }


    public IncomingRequest buildIncomingRequestFromJson(String json) {
        Gson gson = new Gson();
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        IncomingRequest incomingRequest = new IncomingRequest();

        if (map.containsKey("status")) {
            incomingRequest.setStatus(map.get("status"));
        }

        if (map.containsKey("countryCode")) {
            incomingRequest.setCountryCode(map.get("countryCode"));
        }

        if (map.containsKey("isp")) {
            incomingRequest.setIsp(map.get("isp"));
        }

        return incomingRequest;
    }
}
