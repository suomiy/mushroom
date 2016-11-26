package cz.fi.muni.pa165.service.config;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan("cz.fi.muni.pa165.service")
public class ServiceConfig {
}

