package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.*;

/**
 * @author Jiří Šácha 409861
 */
@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    private Hunter hunter;

    @ManyToOne(fetch=FetchType.EAGER)
    private Forest forest;

    @ElementCollection
    @CollectionTable(name = "Visits",
            joinColumns = @JoinColumn(name = "visit_id"))
    @MapKeyJoinColumn(name = "mushroom_id")
    @Column(name = "count")
    private Map<Mushroom,Integer> mushroomCount;

    @Column(nullable = true, unique = false, length = 256)
    private String note;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Visit(){
        this.mushroomCount = new HashMap<Mushroom,Integer>();
    }

    public Long getId() {
        return id;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    public Forest getForest() {
        return forest;
    }

    public void setForest(Forest forest) {
        this.forest = forest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<Mushroom, Integer> getMushroomCount() {
        return Collections.unmodifiableMap(mushroomCount);
    }

    public void setMushroomCount(Map<Mushroom, Integer> mushroomCount) {
        this.mushroomCount = mushroomCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Map<Mushroom, Integer> getMushrooms() {
        return mushroomCount;
    }

    public void setMushrooms(Map<Mushroom, Integer> map) {
        mushroomCount = map;
    }

    public void addMushroom(Mushroom mushroom) {
        if(!this.mushroomCount.containsKey(mushroom)) {
            this.mushroomCount.put(mushroom, 1);
        } else {
            this.mushroomCount.put(mushroom,
                    this.mushroomCount.get(mushroom) + 1);
        }

        mushroom.addVisit(this);
    }

    public void removeMushroom(Mushroom mushroom) {
        this.mushroomCount.remove(mushroom);
        mushroom.removeVisit(this);
    }

    public void removeAllMushrooms() {
        for (Mushroom key : this.mushroomCount.keySet()) {
            key.removeVisit(this);
        }
        this.mushroomCount.clear();
    }

    @Override
    public int hashCode() {
        int result = getHunter().hashCode();
        result = 31 * result + getForest().hashCode();
        result = 31 * result + getDate().hashCode();
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;

        Visit other = (Visit) o;

        if (!getHunter().equals(other.getHunter())) return false;
        if (!getForest().equals(other.getForest())) return false;
        return getDate().equals(other.getDate());

    }
}
