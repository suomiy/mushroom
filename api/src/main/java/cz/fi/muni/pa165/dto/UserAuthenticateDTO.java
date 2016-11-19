package cz.fi.muni.pa165.dto;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
public class UserAuthenticateDTO {

    private String passwordHash;

    private String nick;

    public UserAuthenticateDTO() {
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
