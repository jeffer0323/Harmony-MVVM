package com.eholee.harmony_arch.viewmodel;


import com.eholee.harmony_arch.annotation.NonNull;
import ohos.aafwk.ability.AbilityPackage;
import ohos.app.Context;

/**
 * 应用上下文感知 {@link ViewModel}。
 * <p>
 * 子类必须有一个接受 {@link AbilityPackage} 作为唯一参数的构造函数。
 * <p>
 */
public class HarmonyViewModel extends ViewModel {
    private Context mApplication;

    public HarmonyViewModel(@NonNull Context application) {
        mApplication = application;
    }

    /**
     * Return the application.
     */
    @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
    @NonNull
    public <T extends Context> T getApplication() {
        return (T) mApplication;
    }
}