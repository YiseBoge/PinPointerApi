package com.itsc.PinPointer.repositories;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.itsc.PinPointer.domains.Facility;
import org.springframework.stereotype.Repository;

@Repository
public class FacilityRepository extends DefaultFirebaseRealtimeDatabaseRepository<Facility, String> {
}
