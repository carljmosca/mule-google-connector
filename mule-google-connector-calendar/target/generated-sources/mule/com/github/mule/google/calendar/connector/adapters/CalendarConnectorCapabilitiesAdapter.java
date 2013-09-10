
package com.github.mule.google.calendar.connector.adapters;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.CalendarConnector;
import com.github.mule.google.calendar.connector.basic.Capabilities;
import com.github.mule.google.calendar.connector.basic.Capability;


/**
 * A <code>CalendarConnectorCapabilitiesAdapter</code> is a wrapper around {@link CalendarConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CalendarConnectorCapabilitiesAdapter
    extends CalendarConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == Capability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

}
