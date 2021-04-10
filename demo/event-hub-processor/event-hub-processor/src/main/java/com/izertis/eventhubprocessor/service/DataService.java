package com.izertis.eventhubprocessor.service;

import com.google.gson.JsonObject;
import com.izertis.eventhubprocessor.model.PoliticParty;
import com.izertis.eventhubprocessor.model.Tweet;
import com.izertis.eventhubprocessor.model.User;
import com.izertis.eventhubprocessor.repositories.PoliticPartyRepository;
import com.izertis.eventhubprocessor.repositories.TweetRepository;
import com.izertis.eventhubprocessor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataService {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PoliticPartyRepository politicPartyRepository;

    public void saveTweet(JsonObject jTweet) {
        if (jTweet!=null) {
            if (jTweet.has("politic_party")) {
                String partyStr = jTweet.get("politic_party").getAsString();
                Optional<PoliticParty> oParty =  politicPartyRepository.findById(partyStr);
                PoliticParty party;
                if (!oParty.isEmpty()) {
                    party = oParty.get();
                } else {
                    party = new PoliticParty(partyStr);
                    politicPartyRepository.save(party);
                }


                if (jTweet.has("user")) {
                    JsonObject jUser = jTweet.get("user").getAsJsonObject();
                    if (jUser.has("id")) {
                        String userId = jUser.get("id").getAsString();
                        User user;
                        Optional oUser = userRepository.findById(userId);
                        if (!oUser.isEmpty()) {
                            user = (User) oUser.get();
                        } else {
                            user = new User(party,jUser);
                            userRepository.save(user);
                        }

                        Tweet tweet = new Tweet(user, jTweet);
                        tweetRepository.save(tweet);
                    }


                }
            }
        }
    }
}
