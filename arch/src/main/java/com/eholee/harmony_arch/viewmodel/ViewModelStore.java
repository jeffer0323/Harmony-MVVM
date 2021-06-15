package com.eholee.harmony_arch.viewmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Author : Jeffer
 * Date : 2021/3/16
 * Desc : viewmodel 存储仓库
 */
public class ViewModelStore {
    private final HashMap<String, ViewModel> mMap = new HashMap<>();

    final void put(String key, ViewModel viewModel) {
        ViewModel oldViewModel = mMap.put(key, viewModel);
        if (oldViewModel != null) {
            oldViewModel.onCleared();
        }
    }

    final ViewModel get(String key) {
        return mMap.get(key);
    }

    Set<String> keys() {
        return new HashSet<>(mMap.keySet());
    }

    /**
     *  清理内部存储的viewmodels，并且通知viewmodels要释放了
     */
    public final void clear() {
        for (ViewModel vm : mMap.values()) {
            vm.clear();
        }
        mMap.clear();
    }
}
