package cz.fi.muni.pa165.dto;


import com.fasterxml.jackson.annotation.JsonView;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.views.View;

public abstract class UserDTO {

    @JsonView(View.Summary.class)
    private Long id;

    @JsonView(View.Summary.class)
    private String firstName;

    @JsonView(View.Summary.class)
    private String surname;

    @JsonView(View.Summary.class)
    private String nick;

    @JsonView(View.Summary.class)
    private String email;

    @JsonView(View.Summary.class)
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
