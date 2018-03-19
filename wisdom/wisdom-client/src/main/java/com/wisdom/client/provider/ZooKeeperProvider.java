package com.wisdom.client.provider;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.wisdom.client.event.NodeUpdateEvent;
import com.wisdom.client.event.WisdomEvent;
import com.wisdom.client.event.WisdomEventPublisher;
import com.wisdom.client.event.WisdomListener;
import com.wisdom.client.remote.Config;
import com.wisdom.common.model.Url;
import com.wisdom.constants.ZookeeperConstants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ZooKeeperProvider extends AbstractProvider implements WisdomEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperProvider.class.getName());

    protected final Object monitor = new Object();

    private final transient CuratorFramework client;

    private final WisdomListener listener;

    private final WisdomEventPublisher publisher;

    private volatile transient TreeCache treeCache;

    public ZooKeeperProvider(Url url, WisdomListener listener) throws Exception {
        super(url);
        synchronized (monitor) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(getUrl().getRegisterPath(), new RetryNTimes(10, 5000));
            client.start();
            LOGGER.info("zookeeper start successful");
            this.client = client;
            clientRootCheck();
            this.isStart = true;
            this.listener = listener;
            this.publisher = this;
            zooKeeperNodelistener();
        }
    }

    @Override
    protected void doRegister() throws Exception {
        createZooKeeperNode(getUrl().getCurrentRegisterNode(), CreateMode.EPHEMERAL);
    }

    @Override
    public void close() throws Exception {
        synchronized (monitor) {
            treeCache.close();
            client.close();
        }
    }

    @Override
    public List<Config> pull() throws Exception {
        if (!nodeExists(getUrl().getSubscribeNode())) {
            return null;
        }

        List<String> childrenNodes = client.getChildren().forPath(getUrl().getSubscribeNode());
        LOGGER.info("pull childrenNodes values {} from node {}", childrenNodes, getUrl().getSubscribeNode());
        if (CollectionUtils.isEmpty(childrenNodes)) {
            return null;
        }

        List<Config> configs = new ArrayList<>(childrenNodes.size());
        byte[] bytes = null;
        StringBuilder stringBuilder = null;
        for (String node : childrenNodes) {
            stringBuilder = new StringBuilder().append(getUrl().getSubscribeNode()).append("/").append(node);
            bytes = client.getData().forPath(stringBuilder.toString());
            configs.add(JSON.parseObject(new String(bytes), Config.class));
        }
        childrenNodes = null;
        return configs;
    }

    @Override
    public void publishEvent(WisdomEvent wisdomEvent) {
        this.listener.onWisdomEvent(wisdomEvent);
    }

    private static final String securityValidation(final TreeCacheEvent event) {
        // 只能为此格式 /wisdom/provider/htdcfs/name&version
        String nodePath = event.getData().getPath();
        String[] nodes = nodePath.split("/");
        if (nodes.length == 5) {
            if (nodes[4].split("&").length == 2) {
                return nodes[4];
            }
        }
        return null;
    }

    private void zooKeeperNodelistener() throws Exception {
        if (null == treeCache) {
            treeCache = new TreeCache(client, getUrl().getSubscribeNode());
            treeCache.start();
            try {
                TreeCacheListener treeCacheListener = new TreeCacheListener() {
                    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                        LOGGER.info("request Zookeeper event is {}", event);
                        synchronized (monitor) {
                            if (null == event || null == event.getData() || null == event.getData().getPath()
                                    || !event.getData().getPath().startsWith(getUrl().getSubscribeNode())) {
                                return;
                            }

                            final TreeCacheEvent requestEvent = event;

                            if (TreeCacheEvent.Type.NODE_ADDED.equals(requestEvent.getType())
                                    || TreeCacheEvent.Type.NODE_UPDATED.equals(requestEvent.getType())) {
                                if (!Strings.isNullOrEmpty(ZooKeeperProvider.securityValidation(requestEvent))) {
                                    Config config = JSON.parseObject(new java.lang.String(requestEvent.getData().getData()), Config.class);
                                    publisher.publishEvent(new NodeUpdateEvent(config));
                                }
                            }
                        }
                    }
                };
                treeCache.getListenable().addListener(treeCacheListener);
                LOGGER.info("add treeCache Listener successful");
            } catch (Exception e) {
                LOGGER.error("process treeCacheListener error", e);
            }
        }
    }

    private void clientRootCheck() throws Exception {
        createZooKeeperNode(ZookeeperConstants.CLIENT_ROOT_PATH + "/" + getUrl().getAppId(), CreateMode.PERSISTENT);
    }

    private void createZooKeeperNode(String nodePath, CreateMode createMode) throws Exception {
        String[] nodes = nodePath.split("/");
        StringBuilder sb = new StringBuilder();
        String node = null;
        for (int i = 1; i < nodes.length; i++) {
            node = sb.append("/").append(nodes[i]).toString();
            if (!nodeExists(node)) {
                client.create()
                        .withMode(createMode).
                        forPath(node, String.valueOf(System.currentTimeMillis()).getBytes());
                LOGGER.info("create zookeeper node : {} successful", node);
            }
        }
    }

    private boolean nodeExists(String node) throws Exception {
        Stat currentNode = client.checkExists().forPath(node);
        if (null == currentNode) {
            return false;
        }
        return true;
    }
}
