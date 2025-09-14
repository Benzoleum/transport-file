package com.giftgo.transport_file.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entity {

    private UUID id;
    private String uuid;
    @Expose
    private String name;
    private String likes;
    @Expose
    private String transport;
    private double avgSpeed;
    @Expose
    private double topSpeed;

}
