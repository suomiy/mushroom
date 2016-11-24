package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Erik Macej 433744 , on 29.10.16.
 *
 * @author Erik Macej 433744
 */
@Entity
public class MushroomCount implements Comparable<MushroomCount> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Mushroom mushroom;

    @NotNull
    @ManyToOne(optional = false)
    private Visit visit;

    @Min(1)
    private int count;

    public void setId(Long id) { this.id = id; }

    public Long getId() {
        return id;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
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
        if (o == null) return false;
        if (!(o instanceof MushroomCount)) return false;

        MushroomCount that = (MushroomCount) o;

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
    public int compareTo(MushroomCount mushroomCount) {
        Mushroom otherShroom = mushroomCount.mushroom;

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
