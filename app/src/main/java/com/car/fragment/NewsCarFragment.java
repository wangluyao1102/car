package com.car.fragment;


import com.car.activity.R;
import com.car.view.ProgressWheel;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 资讯-新车
 * 
 * @author blue
 */
public class NewsCarFragment extends BaseFragment
{
	@ViewInject(R.id.pw)
	ProgressWheel pw;
	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_car_main;
	}

	@Override
	protected void initParams()
	{   //加载文字
		pw.setText("loading");
		//开始旋转加载
		pw.spin();
		//停止
		//pw.stopSpinning();
	}

}
