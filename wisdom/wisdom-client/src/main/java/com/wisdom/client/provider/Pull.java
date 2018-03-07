package com.wisdom.client.provider;

import java.util.List;

public interface Pull<T> {

    List<T> pull() throws Exception;

}
