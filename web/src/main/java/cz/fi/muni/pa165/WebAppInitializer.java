package cz.fi.muni.pa165;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Filip Krepinsky (410022) on 12/3/16
 */

@WebListener
public class WebAppInitializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sev) {
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
