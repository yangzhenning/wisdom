package com.wisdom.client.remote;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Config extends Response {

    private String propertyName;

    private String propertyVersion;

    private String propertyValue;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("propertyName", propertyName)
                .append("propertyVersion", propertyVersion)
                .append("propertyValue", propertyValue)
                .toString();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyVersion() {
        return propertyVersion;
    }

    public void setPropertyVersion(String propertyVersion) {
        this.propertyVersion = propertyVersion;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
