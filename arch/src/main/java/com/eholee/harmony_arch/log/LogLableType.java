package com.eholee.harmony_arch.log;

import com.eholee.harmony_arch.annotation.IntDef;
import ohos.hiviewdfx.HiLog;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.SOURCE;


@Documented
@Retention(SOURCE)
@Target({FIELD , PARAMETER})
@IntDef({HiLog.LOG_APP , HiLog.DEBUG , HiLog.ERROR , HiLog.FATAL , HiLog.INFO , HiLog.WARN})
public @interface LogLableType {
    int value() default HiLog.LOG_APP;
}