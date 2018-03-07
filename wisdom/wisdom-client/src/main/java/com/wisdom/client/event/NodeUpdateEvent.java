package com.wisdom.client.event;

import com.wisdom.client.remote.Config;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NodeUpdateEvent extends WisdomEvent {

    public NodeUpdateEvent(Config config) {
        super(config);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("source", source)
                .toString();
    }
}
