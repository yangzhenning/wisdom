package com.wisdom.client.resource;

import java.net.URL;

public class ResourceLoader {

    public Resource getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }
}
