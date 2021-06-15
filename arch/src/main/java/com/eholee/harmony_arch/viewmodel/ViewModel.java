package com.eholee.harmony_arch.viewmodel;




import com.eholee.harmony_arch.annotation.MainThread;
import com.eholee.harmony_arch.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : Jeffer
 * Date : 2021/3/16
 * Desc :
 */
public abstract class ViewModel {
    // Can't use ConcurrentHashMap, because it can lose values on old apis (see b/37042460)
    @Nullable
    private final Map<String, Object> mBagOfTags = new HashMap<>();
    private volatile boolean mCleared = false;

    /**
     * 当这个 ViewModel 不再使用时会调用这个方法，并且会被销毁。
     * 当ViewModel观察到一些数据需要清除这个订阅时很有用
     * 防止此 ViewModel 泄漏。
     */
    @SuppressWarnings("WeakerAccess")
    protected void onCleared() {
    }

    @MainThread
    final void clear() {
        mCleared = true;

        if (mBagOfTags != null) {
            synchronized (mBagOfTags) {
                for (Object value : mBagOfTags.values()) {
                    // see comment for the similar call in setTagIfAbsent
                    closeWithRuntimeException(value);
                }
            }
        }
        onCleared();
    }

    /**
     * 设置与此视图模型关联的标签和键。
     * 如果给定的 {@code newValue} 是 {@link Closeable}，
     * 它将被关闭一次 {@link #clear()}。
     * <p>
     * 如果已为给定键设置了值，则此调用不执行任何操作并且
     * 返回当前关联的值，给定的 {@code newValue} 将被忽略
     * <p>
     * 如果 ViewModel 已经被清除，那么 close() 将在返回的对象上调用，如果
     * 它实现了 {@link Closeable}。同一个对象可能会收到多个关闭调用，所以方法
     * 应该是幂等的。
     */
    <T> T setTagIfAbsent(String key, T newValue) {
        T previous;
        synchronized (mBagOfTags) {
            //noinspection unchecked
            previous = (T) mBagOfTags.get(key);
            if (previous == null) {
                mBagOfTags.put(key, newValue);
            }
        }
        T result = previous == null ? newValue : previous;
        if (mCleared) {
            // It is possible that we'll call close() multiple times on the same object, but
            // Closeable interface requires close method to be idempotent:
            // "if the stream is already closed then invoking this method has no effect." (c)
            closeWithRuntimeException(result);
        }
        return result;
    }

    /**
     * Returns the tag associated with this viewmodel and the specified key.
     */
    @SuppressWarnings("TypeParameterUnusedInFormals")
    <T> T getTag(String key) {
        //noinspection unchecked
        synchronized (mBagOfTags) {
            return (T) mBagOfTags.get(key);
        }
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

