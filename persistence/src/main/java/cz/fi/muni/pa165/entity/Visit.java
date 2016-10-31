package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Jiří Šácha 409861
 */
@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Hunter hunter;

    @ManyToOne(optional = false)
    private Forest forest;

    private String note;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("mushroom.name")
    private SortedSet<MushroomCount> mushroomsCount = new TreeSet<>();

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

    public SortedSet<MushroomCount> getMushroomsCount() {
        return Collections.unmodifiableSortedSet(mushroomsCount);
    }

    public void addMushroomCount(MushroomCount mushroomCount) {
        mushroomCount.setVisit(this);
        this.mushroomsCount.add(mushroomCount);
    }

    public void removeMushroomCount(MushroomCount mushroomCount) {
        this.mushroomsCount.remove(mushroomCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;

        Visit visit = (Visit) o;

        if (hunter != null ? !hunter.equals(visit.hunter) : visit.hunter != null) return false;
        if (forest != null ? !forest.equals(visit.forest) : visit.forest != null) return false;
        return date != null ? date.equals(visit.date) : visit.date == null;

    }

    @Override
    public int hashCode() {
        int result = hunter != null ? hunter.hashCode() : 0;
        result = 31 * result + (forest != null ? forest.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", hunter=" + hunter +
                ", forest=" + forest +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", mushroomsCount=" + mushroomsCount +
                '}';
    }
}
