package com.wisdom.client.utils;

import com.google.common.base.Strings;

import java.net.InetAddress;

public class IPUtil {

    public static String getIpAddress() throws Exception {
        String host = InetAddress.getLocalHost().getHostAddress();
        if (Strings.isNullOrEmpty(host)) {
            return host;
        }
        return host;
    }
}
