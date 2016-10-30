package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.MushroomType;

import javax.persistence.*;
import java.time.Month;

/**
 * Created by "Michal Kysilko" on 26.10.16.
 */

@Entity
public class Mushroom implements Comparable<Mushroom> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MushroomType type;

    @Enumerated(EnumType.STRING)
    private Month fromDate;

    @Enumerated(EnumType.STRING)
    private Month toDate;

    private String description;

    public long getId() {
        return this.id;
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

    public Month getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Month fromDate) {
        this.fromDate = fromDate;
    }

    public Month getToDate() {
        return this.toDate;
    }

    public void setToDate(Month toDate) {
        this.toDate = toDate;
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
