
package com.github.mule.google.calendar.connector.devkit;

import javax.annotation.Generated;


/**
 * Marks DevKit {@link org.mule.api.agent.Agent} implementations to take care of logging information at Mule app level mainly for troubleshooting purposes.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public interface SplashScreenAgent {

      /**
     * Print version information for all the modules used by the application
     */
    void splash();

    /**
     * Retrieve the count of modules used by the application
     *
     * @return
     */
    int getExtensionsCount();
}
