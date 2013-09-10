
package com.github.mule.google.calendar.connector.connectivity;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.adapters.CalendarConnectorConnectionIdentifierAdapter;
import com.github.mule.google.calendar.connector.connection.ConnectionManager;
import com.github.mule.google.calendar.connector.process.ManagedConnectionProcessInterceptor;
import com.github.mule.google.calendar.connector.process.ProcessCallback;
import com.github.mule.google.calendar.connector.process.ProcessCallbackProcessInterceptor;
import com.github.mule.google.calendar.connector.process.ProcessInterceptor;
import com.github.mule.google.calendar.connector.process.ProcessTemplate;
import com.github.mule.google.calendar.connector.process.RetryProcessInterceptor;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class ManagedConnectionProcessTemplate<P >implements ProcessTemplate<P, CalendarConnectorConnectionIdentifierAdapter>
{

    private final ProcessInterceptor<P, CalendarConnectorConnectionIdentifierAdapter> processInterceptor;

    public ManagedConnectionProcessTemplate(ConnectionManager<CalendarConnectorConnectionKey, CalendarConnectorConnectionIdentifierAdapter> connectionManager, MuleContext muleContext) {
        ProcessInterceptor<P, CalendarConnectorConnectionIdentifierAdapter> processCallbackProcessInterceptor = new ProcessCallbackProcessInterceptor<P, CalendarConnectorConnectionIdentifierAdapter>();
        ProcessInterceptor<P, CalendarConnectorConnectionIdentifierAdapter> managedConnectionProcessInterceptor = new ManagedConnectionProcessInterceptor<P>(processCallbackProcessInterceptor, connectionManager, muleContext);
        ProcessInterceptor<P, CalendarConnectorConnectionIdentifierAdapter> retryProcessInterceptor = new RetryProcessInterceptor<P, CalendarConnectorConnectionIdentifierAdapter>(managedConnectionProcessInterceptor, muleContext, connectionManager.getRetryPolicyTemplate());
        processInterceptor = retryProcessInterceptor;
    }

    public P execute(ProcessCallback<P, CalendarConnectorConnectionIdentifierAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, messageProcessor, event);
    }

    public P execute(ProcessCallback<P, CalendarConnectorConnectionIdentifierAdapter> processCallback, Filter filter, MuleMessage message)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, filter, message);
    }

}
