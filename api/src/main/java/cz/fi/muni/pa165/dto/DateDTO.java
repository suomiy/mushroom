package cz.fi.muni.pa165.dto;

import java.util.Date;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
public class DateDTO {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateDTO)) return false;

        DateDTO dateDTO = (DateDTO) o;

        return date != null ? date.equals(dateDTO.date) : dateDTO.date == null;
    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }
}
