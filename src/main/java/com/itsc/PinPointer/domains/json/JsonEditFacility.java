package com.itsc.PinPointer.domains.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonEditFacility {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
