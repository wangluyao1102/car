package com.car.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.fragment.FindFragment;
import com.car.fragment.NewsFragment;
import com.car.fragment.PriceFragment;
import com.car.fragment.QuestionFragment;
import com.car.fragment.SelfFragment;
import com.car.view.ToastMaker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 主界面
 * 
 * @author blue
 */
public class MainActivity extends BaseActivity
{
	// 资讯
	@ViewInject(R.id.car_llyt_news)
	LinearLayout car_llyt_news;
	@ViewInject(R.id.car_iv_news)
	ImageView car_iv_news;
	@ViewInject(R.id.car_tv_news)
	TextView car_tv_news;
	// 找车
	@ViewInject(R.id.car_llyt_find)
	LinearLayout car_llyt_find;
	@ViewInject(R.id.car_iv_find)
	ImageView car_iv_find;
	@ViewInject(R.id.car_tv_find)
	TextView car_tv_find;
	// 降价
	@ViewInject(R.id.car_llyt_price)
	LinearLayout car_llyt_price;
	@ViewInject(R.id.car_iv_price)
	ImageView car_iv_price;
	@ViewInject(R.id.car_tv_price)
	TextView car_tv_price;
	// 问答
	@ViewInject(R.id.car_llyt_question)
	LinearLayout car_llyt_question;
	@ViewInject(R.id.car_iv_question)
	ImageView car_iv_question;
	@ViewInject(R.id.car_tv_question)
	TextView car_tv_question;
	// 我的
	@ViewInject(R.id.car_llyt_self)
	LinearLayout car_llyt_self;
	@ViewInject(R.id.car_iv_self)
	ImageView car_iv_self;
	@ViewInject(R.id.car_tv_self)
	TextView car_tv_self;

	// 点击退出时记录时间
	private long firstTime = 0;
	// 选中的索引
	private int chooseIndex = -1;

	// fragment事务
	private FragmentTransaction ft;
    //是否Activity发生了回收
    private boolean isRecycle=false;



	@Override
	protected int getLayoutId()
	{
		return R.layout.activity_main;
	}

	@Override
	protected void initParams()
	{
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null)
		{
			viewOnClick(car_llyt_news);
		}
		Log.d("test", "onCreate");
	}


    //当发生Activity销毁时调用
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putBoolean("isRecycle",true);
		outState.putInt("chooseIndex",chooseIndex);

	}
    //Activity重建调用
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		isRecycle=savedInstanceState.getBoolean("isRecycle");
		chooseIndex=savedInstanceState.getInt("chooseIndex");
	}
    //Activity重建后状态恢复
	@Override
	protected void onResume()
	{
		super.onResume();
	    if(isRecycle){
			tabBgChange(chooseIndex);
		}
	}






	// 控件点击事件
	@OnClick({ R.id.car_llyt_news, R.id.car_llyt_find, R.id.car_llyt_price, R.id.car_llyt_question, R.id.car_llyt_self })
	public void viewOnClick(View view)
	{
		ft = getSupportFragmentManager().beginTransaction();

		switch (view.getId()) {
		// 资讯
		case R.id.car_llyt_news:

			if (chooseIndex != 0)
			{
				chooseIndex = 0;
				tabBgChange(chooseIndex);
				ft.replace(R.id.car_flyt_content, NewsFragment.instantiate(MainActivity.this, NewsFragment.class.getName(), null), "newsfragment");
			}

			break;
		// 找车
		case R.id.car_llyt_find:

			if (chooseIndex != 1)
			{
				chooseIndex = 1;
				tabBgChange(chooseIndex);
				ft.replace(R.id.car_flyt_content, FindFragment.instantiate(MainActivity.this, FindFragment.class.getName(), null), "findfragment");
			}

			break;
		// 降价
		case R.id.car_llyt_price:

			if (chooseIndex != 2)
			{
				chooseIndex = 2;
				tabBgChange(chooseIndex);
				ft.replace(R.id.car_flyt_content, PriceFragment.instantiate(MainActivity.this, PriceFragment.class.getName(), null), "pricefragment");
			}

			break;
		// 问答
		case R.id.car_llyt_question:

			if (chooseIndex != 3)
			{
				chooseIndex = 3;
				tabBgChange(chooseIndex);
				ft.replace(R.id.car_flyt_content, QuestionFragment.instantiate(MainActivity.this, QuestionFragment.class.getName(), null), "questionfragment");
			}

			break;
		// 我的
		case R.id.car_llyt_self:

			if (chooseIndex != 4)
			{
				chooseIndex = 4;
				tabBgChange(chooseIndex);
				ft.replace(R.id.car_flyt_content, SelfFragment.instantiate(MainActivity.this, SelfFragment.class.getName(), null), "selffragment");
			}

			break;

		default:
			break;
		}
		ft.commit();
	}

	// 切换tab背景
	private void tabBgChange(int index)
	{
		switch (index) {
		case 0:

			car_iv_news.setSelected(true);
			car_tv_news.setTextColor(getResources().getColor(R.color.car_cl_choose));

			car_iv_find.setSelected(false);
			car_tv_find.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_price.setSelected(false);
			car_tv_price.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_question.setSelected(false);
			car_tv_question.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_self.setSelected(false);
			car_tv_self.setTextColor(getResources().getColor(R.color.car_cl_unchoose));

			break;
		case 1:

			car_iv_find.setSelected(true);
			car_tv_find.setTextColor(getResources().getColor(R.color.car_cl_choose));

			car_iv_news.setSelected(false);
			car_tv_news.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_price.setSelected(false);
			car_tv_price.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_question.setSelected(false);
			car_tv_question.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_self.setSelected(false);
			car_tv_self.setTextColor(getResources().getColor(R.color.car_cl_unchoose));

			break;
		case 2:

			car_iv_price.setSelected(true);
			car_tv_price.setTextColor(getResources().getColor(R.color.car_cl_choose));

			car_iv_find.setSelected(false);
			car_tv_find.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_news.setSelected(false);
			car_tv_news.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_question.setSelected(false);
			car_tv_question.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_self.setSelected(false);
			car_tv_self.setTextColor(getResources().getColor(R.color.car_cl_unchoose));

			break;
		case 3:

			car_iv_question.setSelected(true);
			car_tv_question.setTextColor(getResources().getColor(R.color.car_cl_choose));

			car_iv_find.setSelected(false);
			car_tv_find.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_news.setSelected(false);
			car_tv_news.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_price.setSelected(false);
			car_tv_price.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_self.setSelected(false);
			car_tv_self.setTextColor(getResources().getColor(R.color.car_cl_unchoose));

			break;
		case 4:

			car_iv_self.setSelected(true);
			car_tv_self.setTextColor(getResources().getColor(R.color.car_cl_choose));

			car_iv_find.setSelected(false);
			car_tv_find.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_news.setSelected(false);
			car_tv_news.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_price.setSelected(false);
			car_tv_price.setTextColor(getResources().getColor(R.color.car_cl_unchoose));
			car_iv_question.setSelected(false);
			car_tv_question.setTextColor(getResources().getColor(R.color.car_cl_unchoose));

			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed()
	{
		long secondTime = System.currentTimeMillis();
		// 如果两次按键时间间隔大于1000毫秒，则不退出
		if (secondTime - firstTime > 1000)
		{
			ToastMaker.showShortToast("再按一次退出客户端");
			firstTime = secondTime;// 更新firstTime
		} else
		{
			finish();
		}
	}

}
