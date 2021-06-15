package com.eholee.harmony_arch.viewmodel;

import com.eholee.harmony_arch.annotation.MainThread;
import com.eholee.harmony_arch.annotation.NonNull;
import ohos.aafwk.ability.AbilityPackage;
import ohos.app.Context;

import java.lang.reflect.InvocationTargetException;


/**
 * 为范围提供 {@code ViewModels} 的实用程序类。
 * 可以获取{@code Ability} 或{@code Slice} 的默认{@code ViewModelProvider}
 */
@SuppressWarnings("WeakerAccess")
public class ViewModelProvider {

    private static final String DEFAULT_KEY =
            "com.eholee.harmony_arch.viewmodel.ViewModelProvider.DefaultKey";

    /**
     * {@code Factory} 接口的实现负责实例化 ViewModel。
     */
    public interface Factory {

        /**
         * 创建给定 {@code Class} 的新实例。
         * <p>
         *
         * @param modelClass 一个 {@code Class} 实例
         * @param <T> ViewModel 的类型参数。
         * @return 一个新创建的 ViewModel
         */
        @NonNull
        <T extends ViewModel> T create(@NonNull Class<T> modelClass);
    }


    /**
     * {@code Factory} 接口的实现负责实例化 ViewModel。
     * <p>
     * 这是 {@link Factory} 的更高级版本，它接收为请求指定的密钥
     * {@link ViewModel}。
     */
    abstract static class KeyedFactory implements Factory {

        /**
         * 创建给定 {@code Class} 的新实例。
         *
         * @param key 与请求的 ViewModel 关联的键
         * @param modelClass 一个 {@code Class} 其实例被请求
         * @param <T> ViewModel 的类型参数。
         * @return 一个新创建的 ViewModel
         */
        @NonNull
        public abstract <T extends ViewModel> T create(@NonNull String key,
                @NonNull Class<T> modelClass);

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            throw new UnsupportedOperationException("create(String, Class<?>) must be called on "
                    + "implementaions of KeyedFactory");
        }
    }

    private final Factory mFactory;
    private final ViewModelStore mViewModelStore;


    /**
     * 创建 {@code ViewModelProvider}。这将创建 {@code ViewModels}
     * 并将它们保留在给定 {@code ViewModelStoreOwner} 的存储中。
     * <p>
     * 此方法将使用
     * {@link HasDefaultViewModelProviderFactory#getDefaultViewModelProviderFactory() 默认工厂}
     * 如果所有者实现了 {@link HasDefaultViewModelProviderFactory}。否则，一个
     * {@link NewInstanceFactory} 将被使用。
     */
    public ViewModelProvider(@NonNull ViewModelStoreOwner owner) {
        this(owner.getViewModelStore(), owner instanceof HasDefaultViewModelProviderFactory
                ? ((HasDefaultViewModelProviderFactory) owner).getDefaultViewModelProviderFactory()
                : NewInstanceFactory.getInstance());
    }


    /**
     * 创建 {@code ViewModelProvider}，它将通过给定的方式创建 {@code ViewModels}
     * {@code Factory} 并将它们保留在给定 {@code ViewModelStoreOwner} 的存储中。
     *
     * @param owner 一个 {@code ViewModelStoreOwner}，其 {@link ViewModelStore} 将用于
     * 保留 {@code ViewModels}
     * @param factory a {@code Factory} 用于实例化
     * 新的 {@code ViewModels}
     */
    public ViewModelProvider(@NonNull ViewModelStoreOwner owner, @NonNull Factory factory) {
        this(owner.getViewModelStore(), factory);
    }

    /**
     * 创建 {@code ViewModelProvider}，它将通过给定的方式创建 {@code ViewModels}
     * {@code Factory} 并将它们保留在给定的 {@code store} 中。
     *
     * @param store {@code ViewModelStore} 将存储 ViewModel 的位置。
     * @param factory factory a {@code Factory} 将用于实例化
     * 新的 {@code ViewModels}
     */
    public ViewModelProvider(@NonNull ViewModelStore store, @NonNull Factory factory) {
        mFactory = factory;
        mViewModelStore = store;
    }

    /**
     * 返回一个现有的 ViewModel 或在范围内创建一个新的（通常是一个片段或
     * 一个活动），与此 {@code ViewModelProvider} 相关联。
     * <p>
     * 创建的 ViewModel 与给定的范围相关联，并将被保留
     * 只要作用域是活动的（例如，如果它是一个活动，直到它
     * 完成或进程被终止）。
     *
     * @param modelClass ViewModel 的类，如果没有则创建它的实例
     *                   展示。
     * @param <T> ViewModel 的类型参数。
     * @return 一个 ViewModel，它是给定类型 {@code T} 的一个实例。
     */
    @NonNull
    @MainThread
    public <T extends ViewModel> T get(@NonNull Class<T> modelClass) {
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return get(DEFAULT_KEY + ":" + canonicalName, modelClass);
    }

    /**
     * 返回一个现有的 ViewModel 或在范围内创建一个新的（通常是一个片段或
     * 一个活动），与此 {@code ViewModelProvider} 相关联。
     * <p>
     * 创建的 ViewModel 与给定的范围相关联，并将被保留
     * 只要作用域是活动的（例如，如果它是一个活动，直到它
     * 完成或进程被终止）。
     *
     * @param key 用于标识 ViewModel 的键。
     * @param modelClass ViewModel 的类，如果没有则创建它的实例
     *                   展示。
     * @param <T> ViewModel 的类型参数。
     * @return 一个 ViewModel，它是给定类型 {@code T} 的一个实例。
     */
    @NonNull
    @MainThread
    public <T extends ViewModel> T get(@NonNull String key, @NonNull Class<T> modelClass) {
        ViewModel viewModel = mViewModelStore.get(key);

        if (modelClass.isInstance(viewModel)) {
            //noinspection unchecked
            return (T) viewModel;
        } else {
            //noinspection StatementWithEmptyBody
            if (viewModel != null) {
                // TODO: log a warning.
            }
        }
        if (mFactory instanceof KeyedFactory) {
            viewModel = ((KeyedFactory) mFactory).create(key, modelClass);
        } else {
            viewModel = mFactory.create(modelClass);
        }
        mViewModelStore.put(key, viewModel);
        //noinspection unchecked
        return (T) viewModel;
    }

    /**
     * 简单工厂，它在给定类上调用空构造函数。
     */
    public static class NewInstanceFactory implements Factory {
        private static NewInstanceFactory sInstance;

        /**
         * 检索 NewInstanceFactory 的单例实例。
         *
         * @return 一个有效的 {@link NewInstanceFactory}
         */
        @NonNull
        static NewInstanceFactory getInstance() {
            if (sInstance == null) {
                sInstance = new NewInstanceFactory();
            }
            return sInstance;
        }


        @SuppressWarnings("ClassNewInstance")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection TryWithIdenticalCatches
            try {
                return modelClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
    }

    /**
     * {@link Factory} which may create {@link HarmonyViewModel} and
     * {@link ViewModel}, which have an empty constructor.
     */
    public static class HarmonyViewModelFactory extends NewInstanceFactory {

        private static HarmonyViewModelFactory sInstance;
        /**
         * 检索 AndroidViewModelFactory 的单例实例。
         *
         * @param application 传入 {@link HarmonyViewModel} 的应用程序
         * @return 一个有效的 {@link HarmonyViewModelFactory}
         */
        @NonNull
        public static HarmonyViewModelFactory getInstance(@NonNull Context application) {
            if (sInstance == null) {
                sInstance = new HarmonyViewModelFactory(application);
            }
            return sInstance;
        }

        private Context mApplication;

        /**
         * 创建一个 {@code AndroidViewModelFactory}
         *
         * @param application 传入 {@link HarmonyViewModel} 的应用程序
         */
        public HarmonyViewModelFactory(@NonNull Context application) {
            mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (HarmonyViewModel.class.isAssignableFrom(modelClass)) {
                //noinspection TryWithIdenticalCatches
                try {
                    return modelClass.getConstructor(AbilityPackage.class).newInstance(mApplication);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (InstantiationException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Cannot create an instance of " + modelClass, e);
                }
            }
            return super.create(modelClass);
        }
    }
}
