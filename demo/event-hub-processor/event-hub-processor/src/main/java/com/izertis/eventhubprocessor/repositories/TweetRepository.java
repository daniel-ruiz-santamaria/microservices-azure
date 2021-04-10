package com.izertis.eventhubprocessor.repositories;

import com.izertis.eventhubprocessor.model.PoliticParty;
import com.izertis.eventhubprocessor.model.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, String> {
}
