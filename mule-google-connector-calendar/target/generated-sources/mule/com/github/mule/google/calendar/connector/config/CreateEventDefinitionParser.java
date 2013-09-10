
package com.github.mule.google.calendar.connector.config;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.config.AbstractDefinitionParser.ParseDelegate;
import com.github.mule.google.calendar.connector.processors.CreateEventMessageProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CreateEventDefinitionParser
    extends AbstractDefinitionParser
{


    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(CreateEventMessageProcessor.class.getName());
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        parseProperty(builder, element, "calendarId", "calendarId");
        parseProperty(builder, element, "startDate", "startDate");
        parseProperty(builder, element, "endDate", "endDate");
        parseProperty(builder, element, "timeZone", "timeZone");
        parseProperty(builder, element, "summary", "summary");
        parseProperty(builder, element, "description", "description");
        parseProperty(builder, element, "location", "location");
        parseListAndSetProperty(element, builder, "guestList", "guest-list", "guest-list", new ParseDelegate<String>() {


            public String parse(Element element) {
                return element.getTextContent();
            }

        }
        );
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "password", "password");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachProcessorDefinition(parserContext, definition);
        return definition;
    }

}
