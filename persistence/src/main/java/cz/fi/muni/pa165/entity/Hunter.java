package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Rank;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Filip Krepinsky (410022) on 10/29/16
 */

@Entity
public class Hunter extends User {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank rank = Rank.BEGINNER;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hunter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Visit> visits = new HashSet<>();

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Set<Visit> getVisits() {
        return Collections.unmodifiableSet(visits);
    }

    public void removeVisit(Visit visit) {
        this.visits.remove(visit);
    }

    public void addVisit(Visit visit) {
        visit.setHunter(this);
        this.visits.add(visit);
    }
}
