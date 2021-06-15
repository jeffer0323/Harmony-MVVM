package com.eholee.harmony_arch.viewmodel;


import com.eholee.harmony_arch.annotation.NonNull;

/**
 * Ability/alice实现该接口，当配置发生变化时（旋转屏幕）该模型仓中的数据不会被销毁。 当需要主动销毁时，调用
 * {@link ViewModelStore#clear()}
 *
 */
@SuppressWarnings("WeakerAccess")
public interface ViewModelStoreOwner {
    /**
     * Returns owned {@link ViewModelStore}
     *
     * @return a {@code ViewModelStore}
     */
    @NonNull
    ViewModelStore getViewModelStore();
}