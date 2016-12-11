package cz.fi.muni.pa165.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public class MushroomCountDTO {

    private Long id;

    @NotNull
    private Long mushroomId;

    @NotNull
    private Long visitId;

    @Min(1)
    private int count;

    public MushroomCountDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMushroomId() {
        return mushroomId;
    }

    public void setMushroomId(Long mushroomId) {
        this.mushroomId = mushroomId;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MushroomCountDTO)) return false;

        MushroomCountDTO that = (MushroomCountDTO) o;

        if (count != that.count) return false;
        if (mushroomId != null ? !mushroomId.equals(that.mushroomId) : that.mushroomId != null) return false;
        return visitId != null ? visitId.equals(that.visitId) : that.visitId == null;
    }

    @Override
    public int hashCode() {
        int result = mushroomId != null ? mushroomId.hashCode() : 0;
        result = 31 * result + (visitId != null ? visitId.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "MushroomCountDTO{" +
                "id=" + id +
                ", mushroomId=" + mushroomId +
                ", visitId=" + visitId +
                ", count=" + count +
                '}';
    }
}
