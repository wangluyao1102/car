package com.car.util;

import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.car.application.LocalApplication;

/**
 * sharedpreferences
 * 
 * @author blue
 */
public class AppPreferences
{

	/**
	 * SharedPreferences Key值 说明
	 * 
	 * @author blue
	 */
	public static class PreferenceKey
	{
		public static final String TEST_KEY = "test_key";
	}

	private static AppPreferences appPreferences;
	private SharedPreferences preferences;

	private AppPreferences()
	{
		preferences = LocalApplication.getInstance().sharereferences;
	}

	public static AppPreferences instance()
	{
		if (appPreferences == null)
		{
			appPreferences = new AppPreferences();
		}
		return appPreferences;
	}

	public void remove(String key)
	{
		preferences.edit().remove(key).commit();
	}

	public void clearAll()
	{
		preferences.edit().clear().commit();
	}

	/**
	 * 保存一个实体，类名为key
	 * */
	public void putObject(Object obj)
	{
		putObject(obj.getClass().getName(), obj);
	}

	/**
	 * 获取一个存储实体
	 * */
	public <T> T getObject(Class<T> c)
	{
		return getObject(c.getName(), c);
	}

	public void putString(String key, String value)
	{
		preferences.edit().putString(key, value).commit();
	}

	public String getString(String key)
	{
		return preferences.getString(key, "");
	}

	public int getInt(String key, int defValue)
	{
		return preferences.getInt(key, defValue);
	}

	public long getLong(String key, long maxValue)
	{
		return preferences.getLong(key, maxValue);
	}

	public float getFloat(String key, float defValue)
	{
		return preferences.getFloat(key, defValue);
	}

	public void putBoolean(String key, boolean value)
	{
		preferences.edit().putBoolean(key, value).commit();
	}

	public boolean getBoolean(String key, boolean def)
	{
		return preferences.getBoolean(key, def);
	}

	public void putLong(String key, long value)
	{
		preferences.edit().putLong(key, value).commit();
	}

	public void putInt(String key, int value)
	{
		preferences.edit().putInt(key, value).commit();
	}

	public void putFloat(String key, float value)
	{
		preferences.edit().putFloat(key, value).commit();
	}

	private void putObject(String key, Object object)
	{
		if (object == null)
			return;
		String value = JSON.toJSONString(object);
		preferences.edit().putString(key, value).commit();
	}

	private <T> T getObject(String key, Class<T> c)
	{
		String value = preferences.getString(key, "");
		if (value.isEmpty())
			return null;
		T t = JSON.parseObject(value, c);
		return t;
	}
}
