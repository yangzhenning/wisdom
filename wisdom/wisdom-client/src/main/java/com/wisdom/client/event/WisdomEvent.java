package com.wisdom.client.event;

import java.util.EventObject;

public abstract class WisdomEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public WisdomEvent(Object source) {
        super(source);
    }
}
