package com.eholee.harmony_arch.log;

import com.eholee.harmony_arch.BuildConfig;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * Author : Jeffer
 * Date : 2021/6/15
 * Desc : 日志管理
 */
public class L {

    private static final String TAG = "eholee";
    private static final int DOMAIN = 0x11111;
    //HiLogLabel hiLogLabel = new HiLogLabel(HiLog.LOG_APP , DOMAIN , TAG);

    public static void error(HiLogLabel logLabel , String format , Object... args){
        if (BuildConfig.DEBUG){
            HiLog.error(logLabel , format , args);
        }
    }

    public static void error(@LogLableType int type , int domain , String tag , String format , Object... args){
        HiLogLabel temp = new HiLogLabel(type , domain , tag);
        error(temp , format , args);
    }

    public static void error(@LogLableType int type , int domain  ,  String tag , String logContent){
        error(type , domain , tag , logContent , null);
    }

    public static void error(@LogLableType int type ,  String tag , String logContent){
        error(type ,DOMAIN , tag , logContent , null);
    }

    public static void error(@LogLableType int type ,  String tag , String format  , Object... args){
        error(type ,DOMAIN , tag , format , args);
    }

    public static void error(String tag , String logContent){
        error(HiLog.LOG_APP ,DOMAIN , tag , logContent , null);
    }

    public static void error(String tag , String format , Object... args){
        error(HiLog.LOG_APP ,DOMAIN , tag , format , args);
    }

    public static void error(String logContent){
        error(HiLog.LOG_APP ,DOMAIN , TAG , logContent , null);
    }

    public static void error(String format , Object... args){
        error(HiLog.LOG_APP ,DOMAIN , TAG , format , args);
    }






    public static void info(HiLogLabel logLabel , String format , Object... args){
        if (BuildConfig.DEBUG){
            HiLog.info(logLabel , format , args);
        }
    }

    public static void info(@LogLableType int type , int domain , String tag , String format , Object... args){
        HiLogLabel temp = new HiLogLabel(type , domain , tag);
        info(temp , format , args);
    }

    public static void info(@LogLableType int type , int domain  ,  String tag , String logContent){
        info(type , domain , tag , logContent , null);
    }

    public static void info(@LogLableType int type ,  String tag , String logContent){
        info(type ,DOMAIN , tag , logContent , null);
    }

    public static void info(@LogLableType int type ,  String tag , String format  , Object... args){
        info(type ,DOMAIN , tag , format , args);
    }

    public static void info(String tag , String logContent){
        info(HiLog.LOG_APP ,DOMAIN , tag , logContent , null);
    }

    public static void info(String tag , String format , Object... args){
        info(HiLog.LOG_APP ,DOMAIN , tag , format , args);
    }

    public static void info(String logContent){
        info(HiLog.LOG_APP ,DOMAIN , TAG , logContent , null);
    }

    public static void info(String format , Object... args){
        info(HiLog.LOG_APP ,DOMAIN , TAG , format , args);
    }



}
