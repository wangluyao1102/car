package com.car.http;

import java.io.File;

import android.os.Handler;

import com.car.application.LocalApplication;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 下载文件
 * 
 * @author blue
 * 
 */
public class AsyncHttpDownload
{
	// 线程通信
	private Handler handler;

	// 下载地址
	private String url = "";

	// 保存路径
	private String savePath = "";

	public AsyncHttpDownload(Handler handler)
	{
		this.handler = handler;
	}

	// 下载文件
	public void downloadFile()
	{
		HttpHandler<File> httpHandler = LocalApplication.getInstance().httpUtils.download(url, savePath, true, true, new RequestCallBack<File>()
		{

			@Override
			public void onSuccess(ResponseInfo<File> arg0)
			{
				handler.sendEmptyMessage(1);
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading)
			{
				super.onLoading(total, current, isUploading);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				handler.sendEmptyMessage(-1);
			}
		});

		// 调用cancel()方法停止下载
		httpHandler.cancel();
	}
}
