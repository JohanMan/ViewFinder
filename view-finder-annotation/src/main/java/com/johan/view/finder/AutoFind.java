package com.johan.view.finder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Johan on 2018/9/9.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AutoFind {
    String value() default "";
}
