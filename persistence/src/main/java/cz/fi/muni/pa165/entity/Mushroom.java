package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.utils.DateIntervalUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by "Michal Kysilko" on 26.10.16.
 *
 * @author Michal Kysilko 436339
 */

@Entity
public class Mushroom implements Comparable<Mushroom> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MushroomType type;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;

    private String description;

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MushroomType getType() {
        return this.type;
    }

    public void setType(MushroomType type) {
        this.type = type;
    }

    public Date getFromDate() {
        return new Date(this.fromDate.getTime());
    }

    /**
     * Sets this date which is exclusive to the interval, doesn't use the Time of the object,
     * <p>
     * This method also recalculates toDate of this object
     *
     * @param fromDate date object (Month and Day are used only)
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = DateIntervalUtils.makeIntervalFromDate(fromDate);

        if (this.toDate != null) { // update
            setToDate(this.toDate);
        }
    }

    public Date getToDate() {
        return new Date(this.toDate.getTime());
    }

    /**
     * Sets this date which is exclusive to the interval, doesn't use the Time of the object,
     *
     * @param toDate date object (Month and Day are used only)
     */
    public void setToDate(Date toDate) {
        this.toDate = DateIntervalUtils.makeIntervalToDate(fromDate, toDate);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null) {
            return true;
        }

        if (!(o instanceof Mushroom)) {
            return false;
        }

        Mushroom other = (Mushroom) o;
        if (this.name == null && other.getName() != null) {
            return false;
        }

        if (!this.name.equals(other.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(Mushroom mushroom) {
        String otherName = mushroom.name;

        if (name == null || otherName == null) {
            return name == null ? (otherName == null ? 0 : -1) : 1;
        }

        return name.compareTo(otherName);
    }

    @Override
    public String toString() {
        return "Mushroom{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", type='" + this.type + '\'' +
                ", fromDate='" + (this.fromDate == null ? null : this.fromDate.toString()) + '\'' +
                ", toDate='" + (this.toDate == null ? null : this.toDate.toString()) + '\'' +
                ", description='" + this.description + '\'' +
                '}';
    }
}
