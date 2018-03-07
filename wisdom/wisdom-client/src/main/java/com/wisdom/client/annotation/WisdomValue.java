package com.wisdom.client.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WisdomValue {

    String propertyName();

    String value();

    String description() default "";

    String version() default "1.0";

    boolean canModify() default true;
}
