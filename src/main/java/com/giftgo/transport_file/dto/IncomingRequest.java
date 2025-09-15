package com.giftgo.transport_file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor

public class IncomingRequest {
    /*
    {
        "query": "24.48.0.1",
            "status": "success",
            "country": "Canada",
            "countryCode": "CA",
            "region": "QC",
            "regionName": "Quebec",
            "city": "Montreal",
            "zip": "H1K",
            "lat": 45.6085,
            "lon": -73.5493,
            "timezone": "America/Toronto",
            "isp": "Le Groupe Videotron Ltee",
            "org": "Videotron Ltee",
            "as": "AS5769 Videotron Ltee"
    }
    */

    private String query;
    private String status;
    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String city;
    private String zip;
    private double lat;
    private double lon;
    private String timezone;
    private String isp;
    private String org;
    private String as;
}
