package com.itsc.PinPointer.services;

import com.github.fabiomaffioletti.firebase.repository.Filter;
import com.itsc.PinPointer.domains.Facility;
import com.itsc.PinPointer.domains.FacilityVote;
import com.itsc.PinPointer.repositories.FacilityVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private FacilityVoteRepository facilityVoteRepository;

    @Autowired
    public UserService(FacilityVoteRepository facilityVoteRepository) {
        this.facilityVoteRepository = facilityVoteRepository;
    }

    public FacilityVote vote(FacilityVote facilityVote){

        Filter.FilterBuilder filter = Filter.FilterBuilder.builder();
        filter.orderBy("phoneNumber");
        filter.equalTo(facilityVote.getPhoneNumber());

        List<FacilityVote> facilityVotes = facilityVoteRepository.find(filter.build());

        if (facilityVotes.isEmpty()){
            return facilityVoteRepository.push(facilityVote);
        }
        return facilityVote;
    }

    public int getVotes(Facility facility){
        //TODO get the number of votes for the facility
        return 0;
    }
}
