package com.wisdom.common.model;

public final class Url {

    /**
     *  订阅者存储模型
     *  wisdom
     *  -- client
     *  --- appid1
     *  ---- application1
     *  ---- application2
     *  --- appid2
     *  ---- application3
     *  ---- application4
     */

    /**
     *  提供者存储模型
     *  wisdom
     *  -- provider
     *
     *  --- application1
     *  ---- key value
     *  ---- key value
     *
     *  --- application2
     *  ---- key value
     *  ---- key value
     *
     */

    /**
     * 如果是订阅者(客户端)则url信息为:
     * role=client&appid=htdcfs&application=127.0.0.1:4545&timestamp=1235978454
     *
     * 如果是提供者,则url信息为:
     * htdcfs
     */
    // 节点Url信息
    private String url;

    // 角色
    private String role;

    // 应用名称
    private String appId;

    // 当前节点在ZK里注册信息(包含本身)
    /**
     * 客户端
     * /wisdom/client/htdcfs/url
     * /wisdom/client/htdcfs/application=192.116.0.2:4545&timestamp=1235978454
     */
    /**
     * 提供者
     * /wisdom/provider/htdcfs/key(key的组成结构为配置名称加版本号)
     *
     */
    private String currentRegisterNode;

    /**
     * 当前节点监听的节点地址
     * 如果当前角色是客户端，则监听的节点信息为 /wisdom/provider/应用名称 ，意为：监听这个节点下的所有配置信息
     *
     */
    private String subscribeNode;

    // ZK注册地址
    private String registerPath;


    // 节点信息
    private String nodeInfo;

    public String getSubscribeNode() {
        return subscribeNode;
    }

    public void setSubscribeNode(String subscribeNode) {
        this.subscribeNode = subscribeNode;
    }

    public String getCurrentRegisterNode() {
        return currentRegisterNode;
    }

    public void setCurrentRegisterNode(String currentRegisterNode) {
        this.currentRegisterNode = currentRegisterNode;
    }

    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
