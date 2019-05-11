package com.itsc.PinPointer.domains.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonFacility {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String type;

    private long latitude;
    private long longitude;

    private int views;
    private int votes;
}
