package com.eholee.harmony_arch.viewmodel;


import com.eholee.harmony_arch.annotation.NonNull;

/**
 * Interface that marks a {@link ViewModelStoreOwner} as having a default
 * {@link ViewModelProvider.Factory} for use with
 * {@link ViewModelProvider#ViewModelProvider(ViewModelStoreOwner)}.
 */
public interface HasDefaultViewModelProviderFactory {
    /**
     * Returns the default {@link ViewModelProvider.Factory} that should be
     * used when no custom {@code Factory} is provided to the
     * {@link ViewModelProvider} constructors.
     *
     * @return a {@code ViewModelProvider.Factory}
     */
    @NonNull
    ViewModelProvider.Factory getDefaultViewModelProviderFactory();
}
