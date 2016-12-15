package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.utils.DateIntervalUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @ManyToOne(optional = false)
    private Hunter hunter;

    @NotNull
    @ManyToOne(optional = false)
    private Forest forest;

    private String note;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fromDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date toDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("count")
    private SortedSet<MushroomCount> mushroomsCount = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getFromDate() {
        return fromDate == null ? null : new Date(this.fromDate.getTime());
    }

    /**
     * Sets this date which is exclusive to the interval, doesn't use the Time of the object,
     * <p>
     * This method also recalculates toDate of this object
     *
     * @param fromDate date object (Month and Day are used only)
     */
    public void setFromDate(Date fromDate) {
        DateIntervalUtils.checkIsNotAfter(fromDate, toDate);

        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate == null ? null : new Date(toDate.getTime());
    }

    /**
     * Sets this date which is exclusive to the interval, doesn't use the Time of the object,
     *
     * @param toDate date object (Month and Day are used only)
     */
    public void setToDate(Date toDate) {
        DateIntervalUtils.checkIsNotAfter(fromDate, toDate);

        this.toDate = toDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setMushroomsCount(SortedSet<MushroomCount> mushroomsCount) {
        this.mushroomsCount = mushroomsCount;
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

        return id != null ? id.equals(visit.id) : visit.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", hunter=" + hunter +
                ", forest=" + forest +
                ", note='" + note + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", mushroomsCount=" + mushroomsCount +
                '}';
    }
}
