package com.car.http;

import java.io.File;
import java.io.FileNotFoundException;

import android.os.Handler;

import com.car.application.LocalApplication;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 文件上传
 * 
 * @author blue
 * 
 */
public class AsyncHttpUpload
{
	// 线程通信
	private Handler handler;

	// 下载地址
	private String url = "";

	// 请求参数
	private RequestParams params = new RequestParams();

	public AsyncHttpUpload(Handler handler)
	{
		this.handler = handler;
	}

	// 设置请求参数
	public void setParams(String account, String password, File file) throws FileNotFoundException
	{
		params.addBodyParameter("account", account);
		params.addBodyParameter("password", password);
		params.addBodyParameter("file", file);
	}

	// 文件上传
	public void fileUpload()
	{
		LocalApplication.getInstance().httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>()
		{

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				// 回送消息
				handler.sendEmptyMessage(-1);
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading)
			{
				super.onLoading(total, current, isUploading);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0)
			{
				// 回送消息
				handler.sendEmptyMessage(1);
			}
		});
	}
}
