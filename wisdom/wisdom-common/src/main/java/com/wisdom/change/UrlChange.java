package com.wisdom.change;

import com.wisdom.common.model.Url;
import com.wisdom.constants.ZookeeperConstants;

public class UrlChange {

    public static Url getUrl(String role, String appId, String ip, String registerPath) {
        Url url = new Url();
        url.setRole(role);
        url.setUrl(getUrl(role, appId, ip));
        url.setSubscribeNode(getClientSubscribeNode(appId));
        url.setRegisterPath(registerPath);
        url.setAppId(appId);
        url.setCurrentRegisterNode(getClientRegisterNode(appId, ip));
        return url;
    }

    public static String getClientRegisterNode(String appId, String ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(ZookeeperConstants.CLIENT_ROOT_PATH).append("/").append(appId).append("/").append(ip);
        return sb.toString();
    }

    public static String getUrl(String role, String appId, String ip) {
        StringBuffer sb = new StringBuffer();
        if (ZookeeperConstants.CLIENT_ROLE.equals(role)) {
            // role=client&appid=htdcfs&application=127.0.0.1:4545
            sb.append("role=client&appid=").append(appId).append("&application=").append(ip);
        } else {

        }
        return sb.toString();
    }

    public static String getClientSubscribeNode(String appId) {
        StringBuffer sb = new StringBuffer();
        sb.append(ZookeeperConstants.PROVIDER_ROOT_PATH).append("/").append(appId);
        return sb.toString();
    }
}
