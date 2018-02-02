package com.car.cache.memory;

import android.graphics.Bitmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于LRU算法的内存缓存
 * 
 * @author blue
 * 
 */
public class LruMemoryCache implements MemoryCacheAware<String, Bitmap>
{
	// LRU缓存
	private final LinkedHashMap<String, Bitmap> cache;

	// 最大缓存空间
	private final int maxSize;

	// 当前缓存空间
	private int currentSize;

	public LruMemoryCache(int maxSize)
	{
		if (maxSize <= 0)
		{
			throw new IllegalArgumentException("maxSize <= 0");
		}
		this.maxSize = maxSize;
		// 初始化队列按照访问顺序从少到多排列
		this.cache = new LinkedHashMap<String, Bitmap>(0, 0.75f, true);
	}

	/**
	 * 将图片加入链表
	 */
	@Override
	public final boolean put(String key, Bitmap value)
	{
		if (key == null || value == null)
		{
			throw new NullPointerException("key == null || value == null");
		}

		synchronized (this)
		{
			currentSize += sizeOf(key, value);
			Bitmap previous = cache.put(key, value);
			if (previous != null)
			{
				currentSize -= sizeOf(key, previous);
			}
		}

		trimToSize(maxSize);
		return true;
	}

	/**
	 * 从链表中获取指定key对应的图片
	 */
	@Override
	public final Bitmap get(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key == null");
		}

		synchronized (this)
		{
			return cache.get(key);
		}
	}

	/**
	 * 从链表中移除指定key对应的图片
	 */
	@Override
	public final void remove(String key)
	{
		if (key == null)
		{
			throw new NullPointerException("key == null");
		}

		synchronized (this)
		{
			Bitmap previous = cache.remove(key);
			if (previous != null)
			{
				currentSize -= sizeOf(key, previous);
			}
		}
	}

	/**
	 * 清空缓存
	 */
	@Override
	public void clear()
	{
		trimToSize(-1);
	}

	/**
	 * 获取链表中所有key值
	 */
	@Override
	public Collection<String> keys()
	{
		return new HashSet<String>(cache.keySet());
	}

	/**
	 * 把最近最少使用的对象在缓存值达到预设定值之前从内存中移除
	 * 
	 * @author blue
	 * @param maxSize
	 */
	private void trimToSize(int maxSize)
	{
		while (true)
		{
			String key;
			Bitmap value;
			synchronized (this)
			{
				if (currentSize < 0 || (cache.isEmpty() && currentSize != 0))
				{
					throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
				}

				if (currentSize <= maxSize || cache.isEmpty())
				{
					break;
				}

				Map.Entry<String, Bitmap> toEvict = cache.entrySet().iterator().next();
				if (toEvict == null)
				{
					break;
				}
				key = toEvict.getKey();
				value = toEvict.getValue();
				cache.remove(key);
				currentSize -= sizeOf(key, value);
			}
		}
	}

	/**
	 * 返回指定图片大小
	 * 
	 * @author blue
	 * @param key
	 * @param value
	 * @return
	 */
	private int sizeOf(String key, Bitmap value)
	{
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public synchronized final String toString()
	{
		return String.format("LruCache[maxSize=%d]", maxSize);
	}
}
