package cz.fi.muni.pa165.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.views.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
public class HunterDTO extends UserDTO {

    @JsonView(View.Summary.class)
    private Rank rank;

    private List<VisitDTO> visits = new ArrayList<>();

    public HunterDTO() {
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<VisitDTO> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitDTO> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return super.toString() +
                "HunterDTO{" +
                "rank=" + rank +
                '}';
    }
}
