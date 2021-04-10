package com.izertis.eventhubprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="politic_party")
@Getter
@ToString(includeFieldNames = true)
@AllArgsConstructor
@NoArgsConstructor
public class PoliticParty {

    @Id
    @Column(name="id")
    @EqualsAndHashCode.Include
    private String id;

    @JsonIgnore
    @OneToMany(mappedBy = "politicParty", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<User> users;

    public PoliticParty(String id) {
        this.id = id;
        this.users = new HashSet<>();
    }
}
