package com.wisdom.client.model;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

public class FieldWisdomInfo {

    private String propertyName;

    private String oldValue;

    private volatile String currentValue;

    private String description;

    private String version;

    private boolean canModify;

    private Timestamp updateTime;

    private AtomicInteger updateCount = new AtomicInteger();

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public AtomicInteger getUpdateCount() {
        return updateCount;
    }

    public void updateCount() {
        updateCount.addAndGet(1);
    }
}
