package com.eholee.harmony_arch;

import com.eholee.harmony_arch.annotation.NonNull;
import com.eholee.harmony_arch.viewmodel.ViewModelStore;
import com.eholee.harmony_arch.viewmodel.ViewModelStoreOwner;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.Lifecycle;
import ohos.aafwk.ability.LifecycleStateObserver;

/**
 * Author : Jeffer
 * Date : 2021/6/15
 * Desc : Ability 基类
 */
public class BaseAbility extends Ability implements ViewModelStoreOwner {


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

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (getAbilityPackage() == null) {
            throw new IllegalStateException("Your ability is not yet attached to the "
                    + "AbilityPackage instance. You can't request ViewModel before onActive call.");
        }
        if (mViewModelStore == null) {
            NonConfigurationInstances nc =
                    (NonConfigurationInstances) getLastStoredDataWhenConfigChanged();
            if (nc != null) {
                // Restore the ViewModelStore from NonConfigurationInstances
                mViewModelStore = nc.viewModelStore;
            }
            if (mViewModelStore == null) {
                mViewModelStore = new ViewModelStore();
            }
        }

        return mViewModelStore;
    }

    @Override
    public Object onStoreDataWhenConfigChange() {
        Object custom = onStoreCustomDataWhenConfigChange();

        ViewModelStore viewModelStore = mViewModelStore;
        if (viewModelStore == null) {
            // No one called getViewModelStore(), so see if there was an existing
            // ViewModelStore from our last NonConfigurationInstance
            NonConfigurationInstances nc =
                    (NonConfigurationInstances) getLastStoredDataWhenConfigChanged();
            if (nc != null) {
                viewModelStore = nc.viewModelStore;
            }
        }

        if (viewModelStore == null && custom == null) {
            return null;
        }

        NonConfigurationInstances nci = new NonConfigurationInstances();
        nci.custom = custom;
        nci.viewModelStore = viewModelStore;
        return nci;
    }

    static final class NonConfigurationInstances {
        Object custom;
        ViewModelStore viewModelStore;
    }

    @Override
    public Object getLastStoredDataWhenConfigChanged() {
        return super.getLastStoredDataWhenConfigChanged();
    }

    /**
     * 子类可重写，添加自定义数据
     * @return
     */
    protected Object onStoreCustomDataWhenConfigChange(){
        return null;
    }



}
