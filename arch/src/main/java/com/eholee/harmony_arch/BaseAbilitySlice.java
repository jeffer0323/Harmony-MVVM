package com.eholee.harmony_arch;

import com.eholee.harmony_arch.viewmodel.ViewModelStore;
import com.eholee.harmony_arch.viewmodel.ViewModelStoreOwner;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.Lifecycle;
import ohos.aafwk.ability.LifecycleStateObserver;

/**
 * Author : Jeffer
 * Date : 2021/3/16
 * Desc : Slice base
 */
public class BaseAbilitySlice extends AbilitySlice implements ViewModelStoreOwner {
    private ViewModelStore mViewModelStore;

    @Override
    protected void onActive() {
        super.onActive();
        getLifecycle().addObserver((LifecycleStateObserver) (event, intent) -> {
            if (event == Lifecycle.Event.ON_STOP){
                getViewModelStore().clear();
            }
        });
    }

    @Override
    public ViewModelStore getViewModelStore() {
        if (getAbility() == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (getLifecycle().getLifecycleState() == Lifecycle.Event.UNDEFINED) {
            throw new IllegalStateException("AbilitySlice 生命周期未定义时不能使用getViewModelStore该方法");
        }

        if (mViewModelStore == null) {
            mViewModelStore = new ViewModelStore();
        }

        return mViewModelStore;
    }


}
