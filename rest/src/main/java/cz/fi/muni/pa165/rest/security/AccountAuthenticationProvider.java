package cz.fi.muni.pa165.rest.security;

import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import cz.fi.muni.pa165.service.HunterService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 12/16/16
 */

@Component
public class AccountAuthenticationProvider implements AuthenticationProvider {
    private static final String INVALID_CREDENTIALS = "Invalid credentials";

    @Inject
    private HunterService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        Hunter hunter = authService.findByEmail(email);
        if (hunter == null) {
            throw new HunterAuthenticationException(INVALID_CREDENTIALS);
        }

        if (!authService.authenticate(hunter, password)) {
            throw new HunterAuthenticationException(INVALID_CREDENTIALS);
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(hunter.getType().name()));

        return new UsernamePasswordAuthenticationToken(hunter, password, grantedAuthorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
