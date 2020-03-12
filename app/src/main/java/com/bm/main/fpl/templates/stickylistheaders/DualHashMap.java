package com.bm.main.fpl.templates.stickylistheaders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

/**
 * simple two way hashmap
 * @author lsjwzh
 */
class DualHashMap<TKey, TValue> {
    @NonNull
    HashMap<TKey, TValue> mKeyToValue = new HashMap<TKey, TValue>();
    @NonNull
    HashMap<TValue, TKey> mValueToKey = new HashMap<TValue, TKey>();

    public void put(TKey t1, TValue t2){
        remove(t1);
        removeByValue(t2);
        mKeyToValue.put(t1, t2);
        mValueToKey.put(t2, t1);
    }

    @Nullable
    public TKey getKey(TValue value){
        return mValueToKey.get(value);
    }
    @Nullable
    public TValue get(TKey key){
        return mKeyToValue.get(key);
    }

    public void remove(TKey key){
        if(get(key)!=null){
            mValueToKey.remove(get(key));
        }
        mKeyToValue.remove(key);
    }
    public void removeByValue(TValue value){
        if(getKey(value)!=null){
            mKeyToValue.remove(getKey(value));
        }
        mValueToKey.remove(value);
    }
}
