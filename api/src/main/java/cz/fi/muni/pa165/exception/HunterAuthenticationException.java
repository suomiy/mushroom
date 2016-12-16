package cz.fi.muni.pa165.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Erik Macej 433744 , on 24.11.16.
 *
 * @author Erik Macej 433744
 */
public class HunterAuthenticationException extends AuthenticationException {

    public HunterAuthenticationException(String msg) {
        super(msg);
    }

    public HunterAuthenticationException(String s, Throwable throwable) { super(s, throwable);}
}
