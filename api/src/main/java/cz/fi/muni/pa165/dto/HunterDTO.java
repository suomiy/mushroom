package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Rank;

import javax.validation.constraints.NotNull;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
public class HunterDTO extends UserDTO {

    @NotNull
    private Rank rank;

    public HunterDTO() {
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return super.toString() +
                "HunterDTO{" +
                "rank=" + rank +
                '}';
    }
}
