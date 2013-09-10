
package com.github.mule.google.calendar.connector.processors;

import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.CalendarConnector;
import com.github.mule.google.calendar.connector.connectivity.CalendarConnectorConnectionManager;
import com.github.mule.google.calendar.connector.process.ProcessAdapter;
import com.github.mule.google.calendar.connector.process.ProcessCallback;
import com.github.mule.google.calendar.connector.process.ProcessTemplate;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.i18n.CoreMessages;


/**
 * CreateEventMessageProcessor invokes the {@link com.github.mule.google.calendar.connector.CalendarConnector#createEvent(java.lang.String, java.util.Date, java.util.Date, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)} method in {@link CalendarConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CreateEventMessageProcessor
    extends AbstractMessageProcessor<Object>
    implements Disposable, Initialisable, Startable, Stoppable, MessageProcessor
{

    protected Object calendarId;
    protected String _calendarIdType;
    protected Object startDate;
    protected Date _startDateType;
    protected Object endDate;
    protected Date _endDateType;
    protected Object timeZone;
    protected String _timeZoneType;
    protected Object summary;
    protected String _summaryType;
    protected Object description;
    protected String _descriptionType;
    protected Object location;
    protected String _locationType;
    protected Object guestList;
    protected List<String> _guestListType;

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    public void start()
        throws MuleException
    {
    }

    public void stop()
        throws MuleException
    {
    }

    public void dispose() {
    }

    /**
     * Set the Mule context
     * 
     * @param context Mule context to set
     */
    public void setMuleContext(MuleContext context) {
        super.setMuleContext(context);
    }

    /**
     * Sets flow construct
     * 
     * @param flowConstruct Flow construct to set
     */
    public void setFlowConstruct(FlowConstruct flowConstruct) {
        super.setFlowConstruct(flowConstruct);
    }

    /**
     * Sets guestList
     * 
     * @param value Value to set
     */
    public void setGuestList(Object value) {
        this.guestList = value;
    }

    /**
     * Sets summary
     * 
     * @param value Value to set
     */
    public void setSummary(Object value) {
        this.summary = value;
    }

    /**
     * Sets startDate
     * 
     * @param value Value to set
     */
    public void setStartDate(Object value) {
        this.startDate = value;
    }

    /**
     * Sets calendarId
     * 
     * @param value Value to set
     */
    public void setCalendarId(Object value) {
        this.calendarId = value;
    }

    /**
     * Sets location
     * 
     * @param value Value to set
     */
    public void setLocation(Object value) {
        this.location = value;
    }

    /**
     * Sets description
     * 
     * @param value Value to set
     */
    public void setDescription(Object value) {
        this.description = value;
    }

    /**
     * Sets timeZone
     * 
     * @param value Value to set
     */
    public void setTimeZone(Object value) {
        this.timeZone = value;
    }

    /**
     * Sets endDate
     * 
     * @param value Value to set
     */
    public void setEndDate(Object value) {
        this.endDate = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws MuleException
     */
    public MuleEvent process(final MuleEvent event)
        throws MuleException
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(CalendarConnectorConnectionManager.class, true, event);
            final String _transformedCalendarId = ((String) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_calendarIdType").getGenericType(), null, calendarId));
            final Date _transformedStartDate = ((Date) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_startDateType").getGenericType(), null, startDate));
            final Date _transformedEndDate = ((Date) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_endDateType").getGenericType(), null, endDate));
            final String _transformedTimeZone = ((String) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_timeZoneType").getGenericType(), null, timeZone));
            final String _transformedSummary = ((String) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_summaryType").getGenericType(), null, summary));
            final String _transformedDescription = ((String) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_descriptionType").getGenericType(), null, description));
            final String _transformedLocation = ((String) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_locationType").getGenericType(), null, location));
            final List<String> _transformedGuestList = ((List<String> ) evaluateAndTransform(getMuleContext(), event, CreateEventMessageProcessor.class.getDeclaredField("_guestListType").getGenericType(), null, guestList));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((CalendarConnector) object).createEvent(_transformedCalendarId, _transformedStartDate, _transformedEndDate, _transformedTimeZone, _transformedSummary, _transformedDescription, _transformedLocation, _transformedGuestList);
                }

            }
            , this, event);
            overwritePayload(event, resultPayload);
            return event;
        } catch (MessagingException messagingException) {
            messagingException.setProcessedEvent(event);
            throw messagingException;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("createEvent"), event, e);
        }
    }

}
