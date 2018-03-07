package com.wisdom.client.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;

public class WisdomBean {

    private volatile Object springObject;

    private volatile String springBeanName;

    private HashSet<String> referenceProperty;

    private Map<Field, FieldWisdomInfo> fieldWisdomInfoMap;

    private Timestamp registerTime;

    private Timestamp updateTime;

    public String getSpringBeanName() {
        return springBeanName;
    }

    public void setSpringBeanName(String springBeanName) {
        this.springBeanName = springBeanName;
    }

    public Map<Field, FieldWisdomInfo> getFieldWisdomInfoMap() {
        return fieldWisdomInfoMap;
    }

    public void setFieldWisdomInfoMap(Map<Field, FieldWisdomInfo> fieldWisdomInfoMap) {
        this.fieldWisdomInfoMap = fieldWisdomInfoMap;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Object getSpringObject() {
        return springObject;
    }

    public void setSpringObject(Object springObject) {
        this.springObject = springObject;
    }

    public HashSet<String> getReferenceProperty() {
        return referenceProperty;
    }

    public void setReferenceProperty(HashSet<String> referenceProperty) {
        this.referenceProperty = referenceProperty;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("springObject", springObject)
                .append("springBeanName", springBeanName)
                .append("referenceProperty", referenceProperty)
                .append("fieldWisdomInfoMap", fieldWisdomInfoMap)
                .append("registerTime", registerTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
