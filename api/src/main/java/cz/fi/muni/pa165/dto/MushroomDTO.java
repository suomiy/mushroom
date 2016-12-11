package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.MushroomType;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Michal Kysilko 436339 , on 23.11.16.
 *
 * @author Michal Kysilko 436339
 */

public class MushroomDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private MushroomType type;

    private Date fromDate;

    private Date toDate;

    private String description;

    public MushroomDTO() {
    }

    public Long getId() {
        return id;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MushroomDTO)) return false;

        MushroomDTO that = (MushroomDTO) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
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
