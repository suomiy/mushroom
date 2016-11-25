package cz.fi.muni.pa165.dto;

import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
public class VisitDTO {

    private Long id;

    private HunterDTO hunter;

    private ForestDTO forest;

    private String note;

    private Date date;

    private SortedSet<MushroomCountDTO> mushroomsCount = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HunterDTO getHunter() {
        return hunter;
    }

    public void setHunter(HunterDTO hunter) {
        this.hunter = hunter;
    }

    public ForestDTO getForest() {
        return forest;
    }

    public void setForest(ForestDTO forest) {
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

    public SortedSet<MushroomCountDTO> getMushroomsCount() {
        return Collections.unmodifiableSortedSet(mushroomsCount);
    }

    public void addMushroomCount(MushroomCountDTO mushroomCount) {
        mushroomCount.setVisit(this);
        this.mushroomsCount.add(mushroomCount);
    }

    public void removeMushroomCount(MushroomCountDTO mushroomCount) {
        this.mushroomsCount.remove(mushroomCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitDTO)) return false;

        VisitDTO visit = (VisitDTO) o;

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
                ", hunter=" + hunter.getNick() +
                ", forest=" + forest.getName() +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", mushroomsCount=" + mushroomsCount +
                '}';
    }
}
