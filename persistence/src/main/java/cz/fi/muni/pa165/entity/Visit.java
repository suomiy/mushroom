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

    @ManyToOne
    private Hunter hunter;

    @ManyToOne
    private Forest forest;

    @Column(nullable = true, unique = false, length = 256)
    private String note;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("mushroom.name")
    private Set<MushroomCount> mushroomsCount = new TreeSet<>();

    public Visit(){

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<MushroomCount> getMushroomsCount() {
        return Collections.unmodifiableSet(mushroomsCount);
    }

    public void addMushroomCount(MushroomCount mushroomCount){
        this.mushroomsCount.add(mushroomCount);
    }

    public void removeMushroomCount(MushroomCount mushroomCount){
        this.mushroomsCount.remove(mushroomCount);
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
        if (o == null) return false;
        if (!(o instanceof Visit)) return false;

        Visit other = (Visit) o;

        if (!getHunter().equals(other.getHunter())) return false;
        if (!getForest().equals(other.getForest())) return false;
        return getDate().equals(other.getDate());

    }
}
