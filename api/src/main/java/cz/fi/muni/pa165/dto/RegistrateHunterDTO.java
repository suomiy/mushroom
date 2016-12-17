package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Rank;
import cz.fi.muni.pa165.enums.Role;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Erik Macej 433744 , on 10.12.16.
 *
 * @author Erik Macej 433744
 */
public class RegistrateHunterDTO {

    private String firstName;

    private String surname;

    @NotNull
    @Size(min = 4,max = 20)
    private String nick;

    @NotNull
    @Email
    private String email;

    private Role type = Role.USER;

    private Rank rank = Rank.BEGINNER;

    @NotNull
    @Size(min = 6)
    private String password;

    public RegistrateHunterDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getType() { return type; }

    public void setType(Role type) { this.type = type; }

    public Rank getRank() { return rank; }

    public void setRank(Rank rank) { this.rank = rank; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        RegistrateHunterDTO user = (RegistrateHunterDTO) o;

        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RegistrateHunterDTO{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", rank=" + rank +
                '}';
    }
}
