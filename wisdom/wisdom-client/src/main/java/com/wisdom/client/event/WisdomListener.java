package com.wisdom.client.event;

import java.util.EventListener;

public interface WisdomListener<E extends WisdomEvent> extends EventListener {

    void onWisdomEvent(E event);

}
