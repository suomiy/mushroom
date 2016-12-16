package cz.fi.muni.pa165.rest.security;

import cz.fi.muni.pa165.enums.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import static cz.fi.muni.pa165.rest.Uri.*;

/**
 * @author Filip Krepinsky (410022) on 12/14/16
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "rest-api";

    private static final String[] create = new String[]{
            ROOT_URI_FOREST + Part.CREATE + Part.ALL,
            ROOT_URI_MUSHROOM + Part.CREATE + Part.ALL,
            ROOT_URI_MUSCHROOM_COUNT + Part.CREATE + Part.ALL,
            ROOT_URI_VISIT + Part.CREATE + Part.ALL
    };

    private static final String[] update = new String[]{
            ROOT_URI_HUNTER + Part.UPDATE + Part.ALL,
            ROOT_URI_MUSCHROOM_COUNT + Part.UPDATE + Part.ALL,
            ROOT_URI_VISIT + Part.UPDATE + Part.ALL
    };

    private static final String[] delete = new String[]{
            ROOT_URI_HUNTER + Part.ONE_SEGMENT,
            ROOT_URI_MUSCHROOM_COUNT + Part.ONE_SEGMENT,
            ROOT_URI_VISIT + Part.ONE_SEGMENT
    };

    private static final String[] adminUpdate = new String[]{
            ROOT_URI_FOREST + Part.UPDATE + Part.ALL,
            ROOT_URI_MUSHROOM + Part.UPDATE + Part.ALL,
    };

    private static final String[] adminDelete = new String[]{
            ROOT_URI_FOREST + Part.ONE_SEGMENT,
            ROOT_URI_MUSHROOM + Part.ONE_SEGMENT,
    };

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, create).authenticated()
                .antMatchers(HttpMethod.POST, update).authenticated()
                .antMatchers(HttpMethod.DELETE, delete).authenticated()
                .antMatchers(HttpMethod.POST, adminUpdate).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, adminDelete).hasRole(Role.ADMIN.name())
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
