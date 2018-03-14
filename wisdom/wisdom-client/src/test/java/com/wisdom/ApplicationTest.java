package com.wisdom;

import com.wisdom.client.context.PropertieWisdomApplicationContext;
import org.junit.Test;

public class ApplicationTest {

    @Test
    public void applicationTest() throws Exception {

        PropertieWisdomApplicationContext applicationContext = new
                PropertieWisdomApplicationContext("wisdom.properties");
    }
}
