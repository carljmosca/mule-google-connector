
package com.github.mule.google.calendar.connector.process;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class ProcessCallbackProcessInterceptor<T, O >implements ProcessInterceptor<T, O>
{


    public T execute(ProcessCallback<T, O> processCallback, O object, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return processCallback.process(object);
    }

    public T execute(ProcessCallback<T, O> processCallback, O object, Filter filter, MuleMessage message)
        throws Exception
    {
        return processCallback.process(object);
    }

}
