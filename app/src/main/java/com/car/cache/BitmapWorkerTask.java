package com.car.cache;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;


import com.car.cache.disk.LruDiskCache;
import com.car.util.BitmapUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 异步加载图片任务
 *
 * @author blue
 *
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap>
{
	// 异步加载图片工具
	private AsyncImageLoader imageLoader;
	// 显示图片的控件所在的视图
	private View view;
	// 图片url地址
	protected String imageUrl;

	// 生成图片的宽度
	private int reqWidth;
	// 生成图片的高度
	private int reqHeight;

	public BitmapWorkerTask(AsyncImageLoader imageLoader, View view, int reqWidth, int reqHeight)
	{
		this.imageLoader = imageLoader;
		this.view = view;
		this.reqWidth = reqWidth;
		this.reqHeight = reqHeight;
	}

	@Override
	protected Bitmap doInBackground(String... params)
	{
		imageUrl = params[0];
		FileDescriptor fileDescriptor = null;
		FileInputStream fileInputStream = null;
		// 硬盘缓存读取对象
		LruDiskCache.Snapshot snapShot = null;
		try
		{
			// 生成图片URL对应的key
			final String key = imageLoader.hashKeyForDisk(imageUrl);
			// 查找key对应的缓存
			snapShot = imageLoader.diskCache.get(key);
			if (snapShot == null)
			{
				// 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存
				LruDiskCache.Editor editor = imageLoader.diskCache.edit(key);
				if (editor != null)
				{
					OutputStream outputStream = editor.newOutputStream(0);
					// 网络获取Bitmap写入指定输出流
					if (downloadUrlToStream(imageUrl, outputStream))
					{
						// 提交生效
						editor.commit();
					} else
					{
						// 放弃此次写入
						editor.abort();
					}
				}
				// 缓存被写入后，再次查找key对应的缓存
				snapShot = imageLoader.diskCache.get(key);
			}
			if (snapShot != null)
			{
				// 读取缓存文件
				fileInputStream = (FileInputStream) snapShot.getInputStream(0);
				fileDescriptor = fileInputStream.getFD();
			}
			// 将缓存数据解析成Bitmap对象
			Bitmap bitmap = null;
			if (fileDescriptor != null)
			{
				//压缩图片
				bitmap = BitmapUtil.decodeSampledBitmap(fileDescriptor, reqWidth, reqHeight);
			}
			if (bitmap != null)
			{
				// 将Bitmap对象添加到内存缓存当中
				imageLoader.addBitmapToMemoryCache(params[0], bitmap);
			}
			return bitmap;
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (fileDescriptor == null && fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		super.onPostExecute(result);
		// 根据Tag找到相应的ImageView控件
		ImageView imageView = (ImageView) view.findViewWithTag(imageUrl);
		if (imageView != null)
		{
			// 加载成功显示图片
			if (result != null)
			{
				imageView.setImageBitmap(result);
			}
			// 加载失败显示图片
			else
			{
				if (imageLoader.loadfailBitmap != null)
				{
					imageView.setImageBitmap(imageLoader.loadfailBitmap);
				}
			}
		}
		// 从任务集合中移除当前任务
		imageLoader.taskCollection.remove(this);
	}

	/**
	 * 建立http请求，获取Bitmap对象写入输出流
	 *
	 * @author blue
	 * @param imageUrl
	 *            图片url地址
	 * @param outputStream
	 *            写入的输出流
	 * @return
	 */
	private boolean downloadUrlToStream(String imageUrl, OutputStream outputStream)
	{
		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try
		{
			final URL url = new URL(imageUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
			out = new BufferedOutputStream(outputStream, 8 * 1024);
			int b;
			while ((b = in.read()) != -1)
			{
				out.write(b);
			}
			return true;
		} catch (final IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (urlConnection != null)
			{
				urlConnection.disconnect();
			}
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			} catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
}
