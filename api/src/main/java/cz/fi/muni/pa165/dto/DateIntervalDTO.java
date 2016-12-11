package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
public class DateIntervalDTO {

    @NotNull
    private Date from;

    @NotNull
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateIntervalDTO)) return false;

        DateIntervalDTO that = (DateIntervalDTO) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return to != null ? to.equals(that.to) : that.to == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
