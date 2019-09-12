package com.neuqer.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被注入的实现类注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Provider {

    /**
     * 是否是覆写的 Provider 方法，用于标识继承默认Provider的Provider实现
     */
    boolean override() default false;
}
