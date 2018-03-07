package com.wisdom.client.remote;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ConfigResult extends Response {

    private List<Config> configList;

    public List<Config> getConfigList() {
        return configList;
    }

    public void setConfigList(List<Config> configList) {
        this.configList = configList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("configList", configList)
                .toString();
    }
}
