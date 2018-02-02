package com.car.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.car.view.DialogMaker;
import com.car.view.DialogMaker.DialogCallBack;
import com.lidroid.xutils.ViewUtils;

/**
 * basefragment
 * 
 * @author blue
 */
public abstract class BaseFragment extends Fragment
{
	protected Context context;
	protected Dialog dialog;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(getLayoutId(), container, false);
		ViewUtils.inject(this, view);
		initParams();

		return view;
	}

	/**
	 * 初始化布局
	 * 
	 * @author blue
	 */
	protected abstract int getLayoutId();
	
	/**
	 * 参数设置
	 * 
	 * @author blue
	 */
	protected abstract void initParams();

	/**
	 * 弹出对话框
	 * 
	 * @author blue
	 */
	public Dialog showAlertDialog(String title, String msg, String[] btns, final DialogCallBack callBack, boolean isCanCancelabel, final boolean isDismissAfterClickBtn, Object tag)
	{
		if (null == dialog || !dialog.isShowing())
		{
			dialog = DialogMaker.showCommonAlertDialog(context, title, msg, btns, callBack, isCanCancelabel, isDismissAfterClickBtn, tag);
		}
		return dialog;
	}

	/**
	 * 等待对话框
	 * 
	 * @author blue
	 */
	public Dialog showWaitDialog(String msg, boolean isCanCancelabel, Object tag)
	{
		if (null == dialog || !dialog.isShowing())
		{
			dialog = DialogMaker.showCommenWaitDialog(context, msg, null, isCanCancelabel, tag);
		}
		return dialog;
	}

	/**
	 * 关闭对话框
	 * 
	 * @author blue
	 */
	public void dismissDialog()
	{
		if (null != dialog && dialog.isShowing())
		{
			dialog.dismiss();
		}
	}
}
