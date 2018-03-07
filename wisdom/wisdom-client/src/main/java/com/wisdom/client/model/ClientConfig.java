package com.wisdom.client.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClientConfig implements Serializable {

    private String appId;

    private String registerPath;

    private String ip;

    private Timestamp registerTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRegisterPath() {
        return registerPath;
    }

    public void setRegisterPath(String registerPath) {
        this.registerPath = registerPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("appId", appId)
                .append("registerPath", registerPath)
                .append("ip", ip)
                .append("registerTime", registerTime)
                .toString();
    }
}
