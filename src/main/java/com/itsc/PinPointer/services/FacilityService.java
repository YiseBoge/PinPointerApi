package com.itsc.PinPointer.services;

import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.json.JsonFacility;
import com.itsc.PinPointer.domains.json.QueryObject;
import com.itsc.PinPointer.exceptions.DataNotFoundException;
import com.itsc.PinPointer.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FacilityService {

    //Name Sorter
    Comparator<JsonFacility> compareByName = Comparator.comparing(JsonFacility::getName);
    //Type Sorter
    Comparator<JsonFacility> compareByType = Comparator.comparing(JsonFacility::getType);
    //Views Sorter
    Comparator<JsonFacility> compareByViews = Comparator.comparingInt(JsonFacility::getViews);
    //Votes Sorter
    Comparator<JsonFacility> compareByVotes = Comparator.comparingInt(JsonFacility::getVotes);
    private FacilityRepository facilityRepository;
    private UserService userService;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository, UserService userService) {
        this.facilityRepository = facilityRepository;
        this.userService = userService;
    }

    public Facility save(Facility facility) {
        return facilityRepository.push(facility);
    }

    public Facility update(Facility facility) {
        return facilityRepository.update(facility);
    }

    public ArrayList<JsonFacility> findAll() {
        List<Facility> facilities = facilityRepository.findAll();
        ArrayList<JsonFacility> jsonFacilities = new ArrayList<>();

        for (Facility facility :
                facilities) {
            jsonFacilities.add(
                    toJsonFacility(facility)
            );
        }
        jsonFacilities = sort(jsonFacilities, "views");

        return jsonFacilities;
    }

    public ArrayList<JsonFacility> findVoted() {
        ArrayList<JsonFacility> facilities = findAll();

        QueryObject queryObject = new QueryObject();
        queryObject.setMinVotes(5);

        facilities = filter(facilities, queryObject);

        return facilities;
    }

    public ArrayList<JsonFacility> findUnvoted() {
        ArrayList<JsonFacility> facilities = findAll();

        QueryObject queryObject = new QueryObject();
        queryObject.setMaxVotes(5);

        facilities = filter(facilities, queryObject);

        return facilities;
    }

    public Facility findById(String facilityId) throws DataNotFoundException {
        Facility found;

        try {
            found = facilityRepository.get(facilityId);
        } catch (Exception e) {
            throw new DataNotFoundException("No Facility of that Id was Found.");
        }

        return found;
    }

    public JsonFacility toJsonFacility(Facility facility) {
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

    public Facility view(Facility facility) {
        facility.incrementViews();
        return facilityRepository.update(facility);
    }

    public double distance(Facility facility, double latit, double longit) {
        return distance(facility.getLatitude(), facility.getLongitude(), latit, longit);
    }

    public double distance(double lat1, double long1, double lat2, double long2) {
        return convertDegreeToMeter(Math.sqrt(
                Math.pow((lat1 - lat2), 2) +
                        Math.pow((long1 - long2), 2)
        ));
    }

    public double convertDegreeToMeter(double degree){
        return degree * 111139;
    }

    public void delete(String facilityId) {
        facilityRepository.remove(facilityId);
    }

    public ArrayList<JsonFacility> filter(ArrayList<JsonFacility> allFacilities, QueryObject parameters) {
        ArrayList<JsonFacility> filtered = new ArrayList<>();

        for (JsonFacility facility :
                allFacilities) {
            if (parameters.getName() == null || containsIgnoreCase(facility.getName(), parameters.getName())) {
                if (parameters.getDescription() == null || containsIgnoreCase(facility.getDescription(), parameters.getDescription())) {
                    if (parameters.getType() == null || containsIgnoreCase(facility.getType(), parameters.getType())) {
                        if (parameters.getMinViews() == 0 || (facility.getViews() >= parameters.getMinViews())) {
                            if (parameters.getMinVotes() == 0 || (facility.getVotes() >= parameters.getMinVotes())) {
                                if (parameters.getMaxVotes() == 0 || (facility.getVotes() < parameters.getMaxVotes())) {
                                    if (parameters.getMaxDistance() == 0 ||
                                            (distance(facility.getLatitude(), facility.getLongitude(),
                                                    parameters.getLatitude(), parameters.getLongitude())
                                                    <= parameters.getMaxDistance())) {

                                        filtered.add(facility);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return filtered;
    }

    public ArrayList<JsonFacility> filter(ArrayList<JsonFacility> allFacilities, String parameter) {
        ArrayList<JsonFacility> filtered = new ArrayList<>();

        for (JsonFacility facility :
                allFacilities) {

            if (facility.getName().contains(parameter)
                    || containsIgnoreCase(facility.getDescription(), parameter)
                    || containsIgnoreCase(facility.getType(), parameter)){
                filtered.add(facility);
            }
        }

        return filtered;
    }

    public ArrayList<JsonFacility> sort(ArrayList<JsonFacility> facilities, String parameter) {
        switch (parameter) {
            case "name":
                Collections.sort(facilities, compareByName);
                break;
            case "type":
                Collections.sort(facilities, compareByType);
                break;
            case "views":
                Collections.sort(facilities, compareByViews.reversed());
                break;
            case "votes":
                Collections.sort(facilities, compareByVotes.reversed());
                break;
        }


        return facilities;
    }

    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}
