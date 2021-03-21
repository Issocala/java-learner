package com.student.base.component.event;

import java.util.EventObject;

/**
 * @author : luoyong
 * @date : 2021-03-20 22:56
 **/
public abstract class GameEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public GameEvent(Object source) {
        super(source);
    }
}
