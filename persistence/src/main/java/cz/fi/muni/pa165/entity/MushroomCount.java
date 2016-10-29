package cz.fi.muni.pa165.entity;

import javax.persistence.*;

/**
 * Created by Erik Macej 433744 , on 29.10.16.
 *
 * @author Erik Macej 433744
 */
@Entity
public class MushroomCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mushroom mushroom;

    @ManyToOne
    private Visit visit;

    @Column
    private int count;

    public Long getId() { return id; }

    public Mushroom getMushroom() { return mushroom; }

    public void setMushroom(Mushroom mushroom) { this.mushroom = mushroom; }

    public Visit getVisit() { return visit; }

    public void setVisit(Visit visit) { this.visit = visit; }

    public int getCount() { return count; }

    public void setCount(int count) { this.count = count; }


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
    public String toString() {
        return "MushroomFound{" +
                "id=" + id +
                ", mushroom=" + mushroom +
                ", visit=" + visit +
                ", count=" + count +
                '}';
    }
}
