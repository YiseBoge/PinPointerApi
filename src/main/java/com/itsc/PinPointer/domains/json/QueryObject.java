package com.itsc.PinPointer.domains.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryObject {

    private String name;

    private String description;

    private String type;

    private double latitude;
    private double longitude;

    private int minViews;
    private int minVotes;
    private int maxVotes;
    private int maxDistance;
}
