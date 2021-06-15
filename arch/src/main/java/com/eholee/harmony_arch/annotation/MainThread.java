package com.eholee.harmony_arch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

// 暂未处理， 先定义
@Documented
@Retention(CLASS)
@Target({METHOD,CONSTRUCTOR,TYPE,PARAMETER})
public @interface MainThread {
}