package com.eholee.harmony_arch.activedata;

import ohos.aafwk.ability.ILifecycle;
import ohos.aafwk.ability.Lifecycle;
import ohos.aafwk.abilityjet.activedata.ActiveData;
import ohos.aafwk.abilityjet.activedata.DataObserver;

/**
 * Author : Jeffer
 * Date : 2021/6/15
 * Desc : 封装ActiveData，对lifecycle绑定
 */
public class MutableActiveData<T>  extends ActiveData<T> {

    public void addObserver(Lifecycle lifecycle, DataObserver<T> observer , boolean always) {
        if (lifecycle==null||lifecycle.getLifecycleState() == Lifecycle.Event.ON_STOP) {
            // ignore
            return;
        }
        if (observer == null)
            return;
        observer.setLifecycle(lifecycle);
        addObserver(observer , always);
    }

    @Override
    public void setData(T value) {
        super.setData(value);
    }
}
