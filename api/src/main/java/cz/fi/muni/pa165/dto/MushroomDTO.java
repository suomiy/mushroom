package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.MushroomType;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michal Kysilko 436339 , on 23.11.16.
 *
 * @author Michal Kysilko 436339
 */

public class MushroomDTO implements Comparable<MushroomDTO> {

    public static final int BASE_YEAR = 1970;

    private Long id;

    private String name;

    private MushroomType type;

    private Date fromDate;

    private Date toDate;

    private String description;

    public MushroomDTO() {}

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
        Date result = null;

        if (fromDate != null) {
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            fromCalendar.set(Calendar.YEAR, BASE_YEAR);

            result = fromCalendar.getTime();
        }

        this.fromDate = result;

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
        Date result = null;

        if (toDate != null) {
            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(toDate);
            toCalendar.set(Calendar.YEAR, BASE_YEAR);

            if (this.fromDate != null) {
                Calendar fromCalendar = Calendar.getInstance();
                fromCalendar.setTime(this.fromDate);
                if (toCalendar.compareTo(fromCalendar) < 0) {
                    toCalendar.add(Calendar.YEAR, 1);
                }
            }
            result = toCalendar.getTime();
        }

        this.toDate = result;
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

        if (!(o instanceof MushroomDTO)) {
            return false;
        }

        MushroomDTO other = (MushroomDTO) o;
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
    public int compareTo(MushroomDTO mushroom) {
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
