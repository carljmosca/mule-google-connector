
package com.github.mule.google.calendar.connector.adapters;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.CalendarConnector;
import com.github.mule.google.calendar.connector.basic.MetadataAware;


/**
 * A <code>CalendarConnectorMetadataAdapater</code> is a wrapper around {@link CalendarConnector } that adds support for querying metadata about the extension.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CalendarConnectorMetadataAdapater
    extends CalendarConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "google-calendar";
    private final static String MODULE_VERSION = "1.0-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.4.0";
    private final static String DEVKIT_BUILD = "3.4.0.1555.8df15c1";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

}
