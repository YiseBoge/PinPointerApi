package com.itsc.PinPointer.api;

import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.FacilityVote;
import com.itsc.PinPointer.domains.json.JsonFacility;
import com.itsc.PinPointer.exceptions.DataNotFoundException;
import com.itsc.PinPointer.services.FacilityService;
import com.itsc.PinPointer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Get Mappings //
    @GetMapping
    public ResponseEntity<List<JsonFacility>> findAll(){
        List<JsonFacility> facilities = facilityService.findAll();

        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonFacility> findOne(@PathVariable("id") String facilityId) throws DataNotFoundException {
        Facility found = facilityService.findById(facilityId);
        facilityService.view(found);

        JsonFacility jsonFacility = facilityService.toJsonFacility(found);
        return new ResponseEntity<>(jsonFacility, HttpStatus.OK);
    }

    @GetMapping("/{id}/distance")
    public ResponseEntity<Double> calculateDistance(@PathVariable("id") String facilityId, @RequestParam("latitude") double latitude, @RequestParam() double longitude) throws DataNotFoundException {

        Facility found = facilityService.findById(facilityId);

        Double distance = facilityService.distance(found, latitude, longitude);

        return new ResponseEntity<>(distance, HttpStatus.OK);
    }

    // Post Mappings //
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

    @PostMapping("/{id}/vote")
    public ResponseEntity<FacilityVote> vote(@PathVariable("id") String facilityId, @RequestParam("phone_number") String phoneNumber) throws DataNotFoundException {

        Facility found = facilityService.findById(facilityId);
        FacilityVote vote = new FacilityVote(found.getId(), phoneNumber);

        return new ResponseEntity<>(userService.vote(vote), HttpStatus.OK);
    }

    // Put Mappings //
    @PutMapping("/{id}/edit")
    public ResponseEntity<Facility> edit(@Valid @RequestBody JsonFacility jsonFacility, @PathVariable("id") String facilityId) throws DataNotFoundException {

        Facility found = facilityService.findById(facilityId);

        found.setName(jsonFacility.getName());
        found.setDescription(jsonFacility.getDescription());
        found.setLatitude(jsonFacility.getLatitude());
        found.setLongitude(jsonFacility.getLongitude());

        return new ResponseEntity<>(facilityService.update(found), HttpStatus.OK);
    }

    // Delete Mappings //
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOne(@PathVariable("id") String facilityId) throws DataNotFoundException {
        Facility found = facilityService.findById(facilityId);

        facilityService.delete(found.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("status", "Successfully Deleted");
        result.put("message", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }


    // Customized Queries from this point on //
}
