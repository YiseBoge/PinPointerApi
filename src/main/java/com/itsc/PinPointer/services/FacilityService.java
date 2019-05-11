package com.itsc.PinPointer.services;

import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.json.JsonFacility;
import com.itsc.PinPointer.exceptions.DataNotFoundException;
import com.itsc.PinPointer.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService {

    private FacilityRepository facilityRepository;
    private UserService userService;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository, UserService userService) {
        this.facilityRepository = facilityRepository;
        this.userService = userService;
    }

    public Facility save(Facility facility){
        return facilityRepository.push(facility);
    }

    public List<JsonFacility> findAll(){
        List<Facility> facilities = facilityRepository.findAll();
        List<JsonFacility> jsonFacilities = new ArrayList<>();

        for (Facility facility :
                facilities) {
            jsonFacilities.add(
                    toJsonFacility(facility)
            );
        }
        return jsonFacilities;
    }

    public Facility findById(String facilityId) throws DataNotFoundException {
        Facility found = facilityRepository.get(facilityId);

        if (found == null){
            throw new DataNotFoundException("No Facility of that Id was Found.");
        }

        return found;
    }

    public JsonFacility toJsonFacility(Facility facility){
        return new JsonFacility(
                facility.getId(),
                facility.getName(),
                facility.getDescription(),
                facility.getType(),
                facility.getLatitude(),
                facility.getLongitude(),
                facility.getViews(),

                userService.getVotes(facility)
        );
    }


    public Facility view(String facilityId) throws DataNotFoundException {
        Facility facility = findById(facilityId);

        facility.incrementViews();
        return facilityRepository.update(facility);
    }
}
