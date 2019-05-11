package com.itsc.PinPointer.services;

import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.FacilityVote;
import com.itsc.PinPointer.repositories.FacilityVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private FacilityVoteRepository facilityVoteRepository;

    @Autowired
    public UserService(FacilityVoteRepository facilityVoteRepository) {
        this.facilityVoteRepository = facilityVoteRepository;
    }

    public FacilityVote vote(FacilityVote facilityVote){
        return facilityVoteRepository.push(facilityVote);
    }

    public int getViews(Facility facility){
        //TODO get the number of Views for the facility
        return 0;
    }

    public int getVotes(Facility facility){
        //TODO get the number of votes for the facility
        return 0;
    }
}
