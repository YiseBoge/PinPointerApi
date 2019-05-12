package com.itsc.PinPointer.domains.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportHolder {

    String type;

    int views;
    int facilitiesAmount;
}
