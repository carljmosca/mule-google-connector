
package com.github.mule.google.calendar.connector.config;

import javax.annotation.Generated;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/google-calendar</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class GoogleCalendarNamespaceHandler
    extends NamespaceHandlerSupport
{


    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        registerBeanDefinitionParser("config", new CalendarConnectorConfigDefinitionParser());
        registerBeanDefinitionParser("create-event", new CreateEventDefinitionParser());
        registerBeanDefinitionParser("create-calendar", new CreateCalendarDefinitionParser());
        registerBeanDefinitionParser("find-calendar", new FindCalendarDefinitionParser());
        registerBeanDefinitionParser("delete-calendar", new DeleteCalendarDefinitionParser());
    }

}
