package com.student.base.attribute.ge;

import com.student.base.attribute.component.AttributeComponent;
import com.student.base.component.event.GameEvent;

/**
 * @author : luoyong
 * @date : 2021-03-20 22:56
 **/

public class AttributeRefreshEvent extends GameEvent {

    private AttributeComponent<?> component;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AttributeRefreshEvent(Object source) {
        super(source);
    }

    public static GameEvent valueOf(Object source, AttributeComponent<?> component) {
        AttributeRefreshEvent event = new AttributeRefreshEvent(source);
        event.component = component;
        return event;
    }
}
