package com.itsc.PinPointer.domains;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FirebaseDocument("/facility")
public class Facility {

    public Facility(String name, String description, String type, long latitude, long longitude) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @FirebaseId
    private String id;

    private String name;

    private String description;

    private String type;

    private long latitude;
    private long longitude;
}
