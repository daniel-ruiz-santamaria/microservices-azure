package com.izertis.eventhubprocessor.repositories;

import com.izertis.eventhubprocessor.model.PoliticParty;
import com.izertis.eventhubprocessor.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PoliticPartyRepository extends CrudRepository<PoliticParty, String> {

    Optional<PoliticParty> findById(String party);

}
