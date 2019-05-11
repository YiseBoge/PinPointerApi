package com.itsc.PinPointer.api;

import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.json.JsonFacility;
import com.itsc.PinPointer.exceptions.DataNotFoundException;
import com.itsc.PinPointer.services.FacilityService;
import com.itsc.PinPointer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/facility")
@CrossOrigin(origins = "*")
public class FacilityController {

    private FacilityService facilityService;
    private UserService userService;

    @Autowired
    public FacilityController(FacilityService facilityService, UserService userService) {
        this.facilityService = facilityService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JsonFacility>> findAll(){
        List<JsonFacility> facilities = facilityService.findAll();

        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonFacility> findOne(@PathVariable("id") String facilityId) throws DataNotFoundException {
        Facility found = facilityService.findById(facilityId);
        JsonFacility jsonFacility = facilityService.toJsonFacility(found);

        return new ResponseEntity<>(jsonFacility, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Facility> add(@Valid @RequestBody JsonFacility jsonFacility){

        Facility saved = facilityService.save(new Facility(
                jsonFacility.getName(),
                jsonFacility.getDescription(),
                jsonFacility.getType(),
                jsonFacility.getLatitude(),
                jsonFacility.getLongitude(),
                0
        ));

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{facility_id}/edit")
    public ResponseEntity<Facility> edit(@Valid @RequestBody JsonFacility jsonFacility, @RequestParam String facility_id){

        return null;
    }
}
