package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Rank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Filip Krepinsky (410022) on 10/29/16
 */

@Entity
public class Hunter extends User {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank rank;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hunter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Visit> getVisits() {
        return Collections.unmodifiableList(visits);
    }

    public void removeVisit(Visit visit) {
        this.visits.remove(visit);
    }

    public void addVisit(Visit visit) {
        visit.setHunter(this);
        this.visits.add(visit);
    }
}
