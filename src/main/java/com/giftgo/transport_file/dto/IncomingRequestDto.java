package com.giftgo.transport_file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor

public class IncomingRequestDto {

    private String status;
    private String countryCode;
    private String isp;
}
