package com.giftgo.transport_file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class IncomingRequestDto {

    private UUID requestId;
    private String requestUri;
    private long timestamp;
    private String status;
    private String ip;
    private String countryCode;
    private String isp;
    private long timeToCompleteRequest;
}
