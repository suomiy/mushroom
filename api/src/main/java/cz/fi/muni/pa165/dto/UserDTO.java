package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Role;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class UserDTO {

    @NotNull
    private Long id;

    private String firstName;

    private String surname;

    @NotNull
    @Size(min = 5, max = 20)
    private String nick;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Role type;

    public UserDTO() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getNick() { return nick; }

    public void setNick(String nick) { this.nick = nick; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Role getType() { return type; }

    public void setType(Role type) { this.type = type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO user = (UserDTO) o;

        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
