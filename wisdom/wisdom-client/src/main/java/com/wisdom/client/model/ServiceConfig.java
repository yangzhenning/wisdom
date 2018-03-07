package com.wisdom.client.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ServiceConfig implements Serializable {

    private String applicationName;

    private String applicationValue;

    private String version;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationValue() {
        return applicationValue;
    }

    public void setApplicationValue(String applicationValue) {
        this.applicationValue = applicationValue;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("applicationName", applicationName)
                .append("applicationValue", applicationValue)
                .append("version", version)
                .toString();
    }
}
