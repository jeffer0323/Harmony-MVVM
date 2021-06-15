package com.eholee.harmony_arch.annotation;

import java.lang.annotation.*;
// 暂未处理， 先定义
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface Nullable {
    String value() default "";
}
