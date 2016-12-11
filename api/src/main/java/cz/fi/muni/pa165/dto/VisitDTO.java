package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
public class VisitDTO {

    private Long id;

    @NotNull
    private Long hunterId;

    @NotNull
    private Long forestId;

    private String note;

    @NotNull
    private Date fromDate;

    @NotNull
    private Date toDate;

    private List<MushroomCountDTO> mushroomsCount;

    public VisitDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public Long getForestId() {
        return forestId;
    }

    public void setForestId(Long forestId) {
        this.forestId = forestId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MushroomCountDTO> getMushroomsCount() {
        return mushroomsCount;
    }

    public void setMushroomsCount(List<MushroomCountDTO> mushroomsCount) {
        this.mushroomsCount = mushroomsCount;
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
        return "VisitDTO{" +
                "id=" + id +
                ", hunterId=" + hunterId +
                ", forestId=" + forestId +
                ", note='" + note + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", mushroomsCount=" + mushroomsCount +
                '}';
    }
}
