package com.wisdom;

import com.wisdom.client.annotation.WisdomComponent;
import com.wisdom.client.annotation.WisdomValue;
import org.junit.Test;
import org.springframework.stereotype.Service;

public class AnnotationTest {

    @Test
    public void AnnotationTest() throws Exception {
        System.out.println(TestClasss.class.isAnnotationPresent(WisdomComponent.class));
        System.out.println(TestClasss.class.isAnnotationPresent(WisdomValue.class));
        System.out.println(TestClasss.class.isAnnotationPresent(Service.class));
    }


    @WisdomComponent
    public class TestClasss {

        @WisdomValue(propertyName = "name", value = "111")
        private String name;
    }
}
