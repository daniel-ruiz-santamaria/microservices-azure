package com.izertis.eventhubprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@Getter
@ToString(includeFieldNames = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name="id")
    @EqualsAndHashCode.Include
    private String id;

    @Column(name="name", nullable = true,columnDefinition = "VARCHAR(400)",length = 400)
    private String name;

    @Column(name="description", nullable = true,columnDefinition = "VARCHAR(800)",length = 800)
    private String description;

    @Column(name="location", nullable = true,columnDefinition = "VARCHAR(400)",length = 400)
    private String location;

    @Column(name="followers", nullable = true,columnDefinition = "int")
    private int followers;

    @Column(name="friends", nullable = true,columnDefinition = "int")
    private int friends;

    @ManyToOne(fetch = FetchType.LAZY)
    private PoliticParty politicParty;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Tweet> tweets;

    public User(PoliticParty politicParty, JsonObject jUser) {

        this.politicParty = politicParty;
        this.tweets = new HashSet<>();

        if (jUser.has("id"))
            this.id = jUser.get("id").getAsString();
        if (jUser.has("name"))
            this.name = jUser.get("name").getAsString();
        if (jUser.has("description"))
            this.description = jUser.get("description").getAsString();
        if (jUser.has("location"))
            this.location = jUser.get("location").getAsString();
        if (jUser.has("followers_count"))
            this.followers = jUser.get("followers_count").getAsInt();
        if (jUser.has("friends_count"))
            this.friends = jUser.get("friends_count").getAsInt();
    }
}
