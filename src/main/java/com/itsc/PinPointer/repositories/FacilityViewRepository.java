package com.itsc.PinPointer.repositories;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.itsc.PinPointer.domains.FacilityView;
import org.springframework.stereotype.Repository;

@Repository
public class FacilityViewRepository extends DefaultFirebaseRealtimeDatabaseRepository<FacilityView, String> {
}
