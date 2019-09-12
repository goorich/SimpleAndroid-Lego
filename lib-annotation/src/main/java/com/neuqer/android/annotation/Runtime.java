package com.neuqer.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 统一存放 Inject 注解的类注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Runtime {
}
