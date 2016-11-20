package cz.fi.muni.pa165.dto;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
public class UserAuthenticateDTO {

    private String password;

    private String email;

    public UserAuthenticateDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordHash(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String nick) {
        this.email = email;
    }
}
