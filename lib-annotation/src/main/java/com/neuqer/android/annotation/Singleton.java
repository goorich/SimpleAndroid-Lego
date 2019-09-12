package com.neuqer.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单例注解，标识这个被注入的实现是单例方式提供
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Singleton {
}
