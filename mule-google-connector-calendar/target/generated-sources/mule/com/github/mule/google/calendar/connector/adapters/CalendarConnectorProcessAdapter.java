
package com.github.mule.google.calendar.connector.adapters;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.CalendarConnector;
import com.github.mule.google.calendar.connector.process.ProcessAdapter;
import com.github.mule.google.calendar.connector.process.ProcessCallback;
import com.github.mule.google.calendar.connector.process.ProcessTemplate;
import com.github.mule.google.calendar.connector.process.ProcessTemplate;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;


/**
 * A <code>CalendarConnectorProcessAdapter</code> is a wrapper around {@link CalendarConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CalendarConnectorProcessAdapter
    extends CalendarConnectorLifecycleAdapter
    implements ProcessAdapter<CalendarConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, CalendarConnectorCapabilitiesAdapter> getProcessTemplate() {
        final CalendarConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,CalendarConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, CalendarConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, CalendarConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
