
package com.github.mule.google.calendar.connector.adapters;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.CalendarConnector;
import com.github.mule.google.calendar.connector.connection.Connection;


/**
 * A <code>CalendarConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link CalendarConnector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CalendarConnectorConnectionIdentifierAdapter
    extends CalendarConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.connectionId();
    }

}
