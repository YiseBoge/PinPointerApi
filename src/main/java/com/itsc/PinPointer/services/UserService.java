package com.itsc.PinPointer.services;

import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.FacilityVote;
import com.itsc.PinPointer.repositories.FacilityVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private FacilityVoteRepository facilityVoteRepository;

    @Autowired
    public UserService(FacilityVoteRepository facilityVoteRepository) {
        this.facilityVoteRepository = facilityVoteRepository;
    }

    public FacilityVote vote(FacilityVote facilityVote){
        List<FacilityVote> facilityVotes = facilityVoteRepository.findAll();

        facilityVotes = filterByPhoneNumber(facilityVotes, facilityVote.getPhoneNumber());

        if (facilityVotes.isEmpty()){
            return facilityVoteRepository.push(facilityVote);
        }
        return facilityVote;
    }

    public int getVotes(Facility facility){
        List<FacilityVote> facilityVotes = facilityVoteRepository.findAll();

        facilityVotes = filterByFacility(facilityVotes, facility.getId());

        return facilityVotes.size();
    }

    public List<FacilityVote> filterByPhoneNumber(List<FacilityVote> facilityVotes, String phoneNumber) {
        ArrayList<FacilityVote> filtered = new ArrayList<>();

        for (FacilityVote facilityVote :
                facilityVotes) {
            if (facilityVote.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                filtered.add(facilityVote);
            }
        }
        return filtered;
    }

    public List<FacilityVote> filterByFacility(List<FacilityVote> facilityVotes, String facilityId) {
        ArrayList<FacilityVote> filtered = new ArrayList<>();

        for (FacilityVote facilityVote :
                facilityVotes) {
            if (facilityVote.getFacilityId().equalsIgnoreCase(facilityId)) {
                filtered.add(facilityVote);
            }
        }
        return filtered;
    }
}
