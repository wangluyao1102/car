package com.car.application;

import java.io.File;

import android.util.DisplayMetrics;

import com.car.activity.R;
import com.car.cache.AsyncImageLoader;
import com.car.exception.BaseExceptionHandler;
import com.car.exception.LocalFileHandler;
import com.car.util.JFileKit;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * 在开发应用时都会和Activity打交道，而Application使用的就相对较少 Application是用来管理应用程序的全局状态的，比如载入资源文件 在应用程序启动的时候Application会首先创建，然后才会根据情况(Intent)启动相应的Activity或者Service
 * 
 * @author blue
 * 
 */
public class LocalApplication extends BaseApplication
{
	private static LocalApplication instance;

	public DbUtils dbUtils = null;

	public HttpUtils httpUtils = null;

	// 当前屏幕的高宽
	public int screenW = 0;
	public int screenH = 0;

	// 单例模式中获取唯一的MyApplication实例
	public static LocalApplication getInstance()
	{
		if (instance == null)
		{
			instance = new LocalApplication();
		}
		return instance;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		// 初始化数据库
//		dbUtils = DbUtils.create(LocalDaoConfig.getInstance(getApplicationContext()));

		// 初始化网络模块
		httpUtils = new HttpUtils();

		// 创建APP崩溃日志目录
		File appFolder = new File(JFileKit.getDiskCacheDir(this) + "/log");
		if (!appFolder.exists())
		{
			appFolder.mkdirs();
		}

		instance = this;

		// 得到屏幕的宽度和高度
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenW = dm.widthPixels;
		screenH = dm.heightPixels;

		// 设置加载中显示图片
		AsyncImageLoader.getInstance(this).setLoadingDrawable(R.mipmap.empty_photo);
		// 设置加载失败显示图片
		AsyncImageLoader.getInstance(this).setLoadFailDrawable(R.mipmap.ic_launcher);






	}

	@Override
	public BaseExceptionHandler getDefaultUncaughtExceptionHandler()
	{
		return new LocalFileHandler(applicationContext);
	}
}
