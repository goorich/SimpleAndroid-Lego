package com.neuqer.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被注入的注解标记
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Inject {

    /**
     * 强制inject必须有provider实现
     */
    boolean force() default true;
}
