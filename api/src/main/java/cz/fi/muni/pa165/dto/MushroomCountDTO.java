package cz.fi.muni.pa165.dto;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public class MushroomCountDTO implements Comparable<MushroomCountDTO> {

    private Long id;

    private MushroomDTO mushroom;

    private VisitDTO visit;

    private int count;

    public MushroomCountDTO() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public MushroomDTO getMushroom() { return mushroom; }

    public void setMushroom(MushroomDTO mushroom) { this.mushroom = mushroom; }

    public VisitDTO getVisit() { return visit; }

    public void setVisit(VisitDTO visit) { this.visit = visit; }

    public int getCount() { return count; }

    public void setCount(int count) { this.count = count; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof MushroomCountDTO)) return false;

        MushroomCountDTO that = (MushroomCountDTO) o;

        if (count != that.count) return false;
        if (mushroom != null ? !mushroom.equals(that.mushroom) : that.mushroom != null) return false;
        return visit != null ? visit.equals(that.visit) : that.visit == null;

    }

    @Override
    public int hashCode() {
        int result = mushroom != null ? mushroom.hashCode() : 0;
        result = 31 * result + (visit != null ? visit.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

    @Override
    public int compareTo(MushroomCountDTO mushroomCount) {
        MushroomDTO otherShroom = mushroomCount.mushroom;

        if (mushroom == null || otherShroom == null) {
            return mushroom == null ? (otherShroom == null ? 0 : -1) : 1;
        }

        return mushroom.compareTo(otherShroom);
    }

    @Override
    public String toString() {
        return "MushroomCount{" +
                "id=" + id +
                ", mushroom=" + mushroom +
                ", visit=" + visit +
                ", count=" + count +
                '}';
    }

}
