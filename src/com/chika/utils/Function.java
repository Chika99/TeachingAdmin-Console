package com.chika.utils;

import com.chika.enums.Roles;

import java.lang.annotation.*;

/**
 * @author Cheng Liu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Function {
    String command();

    String message();

    Roles[] permissions() default {Roles.ANONYMOUS};
}
