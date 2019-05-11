package com.itsc.PinPointer.repositories;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.itsc.PinPointer.domains.FacilityVote;
import org.springframework.stereotype.Repository;

@Repository
public class FacilityVoteRepository extends DefaultFirebaseRealtimeDatabaseRepository<FacilityVote, String> {
}
