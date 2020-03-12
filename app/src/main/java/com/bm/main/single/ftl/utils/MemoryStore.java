package com.bm.main.single.ftl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;


import com.bm.main.pos.SBFApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MemoryStore
{
  //  private static BaseActivity memoryStorePreferences;
  private static SBFApplication memoryStorePreferences;

    @NonNull
    private static HashMap<String, Object> map = new HashMap<>();

    @Nullable
    public static Object get(String key, boolean toDatabase, int dataType)
    {
        Object o = map.get(key);

        if(o == null && toDatabase)
        {
//            Log.d(Tag.INFO, "Ambil dari cache.");

            if(dataType == DataType.INT)
            {
                o = Integer.parseInt(getFromCache( key));
            }
            else if(dataType == DataType.JSON_OBJECT)
            {
                try
                {
                    o = new JSONObject(getFromCache( key));
                }
                catch (JSONException e)
                {
                    Log.e("ERROR", e.getMessage());
                }
            }
            else if(dataType == DataType.JSON_ARRAY)
            {
                try
                {
                    o = new JSONArray(getFromCache( key));
                }
                catch (JSONException e)
                {
                    Log.e("ERROR", e.getMessage());
                }
            }
            else
            {
                o = getFromCache( key);
            }
        }

        return o;
    }

    @Nullable
    public static Object get(String key, boolean toDatabase)
    {
        return get( key, toDatabase, DataType.STRING);
    }

    @Nullable
    public static Object get(String key)
    {
        return get( key, true);
    }

    public static void set(String key, @Nullable Object object)
    {
        map.put(key, object);

        if(object != null)
        {
            cacheThis( key);
        }
    }

    public static void cacheThis( String key)
    {
        cacheThis( key, get( key).toString());
    }

    public static void cacheThis( String key, String value)
    {
        Cache cache = new Cache(memoryStorePreferences);
        cache.open();

        String isExists = getFromCache( key);

        if(isExists == null)
        {
            cache.save(key, value);
        }
        else
        {
            cache.update(key, value);
        }

        cache.close();
    }

    public static String getFromCache( String key)
    {
        Cache cache = new Cache(memoryStorePreferences);
        cache.open();

        String value = cache.read(key);

        cache.close();

        return value;
    }

    public static void configThis(String key)
    {
        configThis( key, get( key).toString());
    }

    public static void configThis( String key, String value)
    {
        Config config = new Config(memoryStorePreferences);
        config.open();

        String isExists = getFromConfig( key);

        if(isExists == null)
        {
            config.save(key, value);
        }
        else
        {
            config.update(key, value);
        }

        config.close();
    }

    public static String getFromConfig( String key)
    {
        Config config = new Config(memoryStorePreferences);
        config.open();

        String value = config.read(key);

        config.close();

        return value;
    }

    public static void initialize()
    {
        if(memoryStorePreferences == null)
        {
            memoryStorePreferences =  SBFApplication.getInstance();//.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        }
    }
}
