package com.car.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.car.activity.R;
import com.car.util.JListKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * 资讯
 * 
 * @author blue
 */
public class NewsFragment extends BaseFragment
{

	// 要闻
	@ViewInject(R.id.news_tv_important)
	TextView news_tv_important;
	// 新车
	@ViewInject(R.id.news_tv_car)
	TextView news_tv_car;
	// 导购
	@ViewInject(R.id.news_tv_buy)
	TextView news_tv_buy;
	// 试车
	@ViewInject(R.id.news_tv_try)
	TextView news_tv_try;
	// 图解
	@ViewInject(R.id.news_tv_picture)
	TextView news_tv_picture;
	// 行业
	@ViewInject(R.id.news_tv_market)
	TextView news_tv_market;
	// viewpager
	@ViewInject(R.id.news_vp)
	ViewPager news_vp;

	private List<Fragment> fragmentList = JListKit.newArrayList();
	// 要闻
	private NewsImportantFragment importantFragment;
	// 新车
	private NewsCarFragment carFragment;
	// 导购
	private NewsBuyFragment buyFragment;
	// 试车
	private NewsTryFragment tryFragment;
	// 图解
	private NewsPictureFragment pictureFragment;
	// 行情
	private NewsMarketFragment marketFragment;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_news_main;
	}
	// 控件点击事件
	@OnClick({ R.id.news_tv_important, R.id.news_tv_car, R.id.news_tv_buy, R.id.news_tv_try, R.id.news_tv_picture, R.id.news_tv_market })
	public void viewOnClick(View view){
		switch (view.getId()){
			case R.id.news_tv_important:

				news_vp.setCurrentItem(0);

				break;
			case R.id.news_tv_car:

				news_vp.setCurrentItem(1);

				break;
			case R.id.news_tv_buy:

				news_vp.setCurrentItem(2);

				break;
			case R.id.news_tv_try:

				news_vp.setCurrentItem(3);

				break;
			case R.id.news_tv_picture:

				news_vp.setCurrentItem(4);

				break;
			case R.id.news_tv_market:

				news_vp.setCurrentItem(5);

				break;

			default:
				break;
		}
	}




	@Override
	protected void initParams()
	{
		importantFragment = new NewsImportantFragment();
		carFragment = new NewsCarFragment();
		buyFragment = new NewsBuyFragment();
		tryFragment = new NewsTryFragment();
		pictureFragment = new NewsPictureFragment();
		marketFragment = new NewsMarketFragment();

		fragmentList.add(importantFragment);
		fragmentList.add(carFragment);
		fragmentList.add(buyFragment);
		fragmentList.add(tryFragment);
		fragmentList.add(pictureFragment);
		fragmentList.add(marketFragment);


		news_vp.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager())
		{

			@Override
			public int getCount()
			{
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return fragmentList.get(arg0);
		}
		});
		// 默认选中第一页卡
		news_vp.setCurrentItem(0);
		// 绑定页卡切换监听器
		news_vp.setOnPageChangeListener(new DefineOnPageChangeListener());
	}

	// viewpager视图切换监听器
	public class DefineOnPageChangeListener implements ViewPager.OnPageChangeListener
	{
		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageSelected(int arg0)
		{
			switch (arg0)
			{
				// 要闻
				case 0:

					news_tv_important.setBackgroundResource(R.drawable.news_ic_selected);
					news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

					news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_choose));
					news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

					break;
				// 新车
				case 1:

					news_tv_car.setBackgroundResource(R.drawable.news_ic_selected);
					news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

					news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_choose));
					news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

					break;
				// 导购
				case 2:

					news_tv_buy.setBackgroundResource(R.drawable.news_ic_selected);
					news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

					news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_choose));
					news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

					break;
				// 试车
				case 3:

					news_tv_try.setBackgroundResource(R.drawable.news_ic_selected);
					news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

					news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_choose));
					news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

					break;
				// 图解
				case 4:

					news_tv_picture.setBackgroundResource(R.drawable.news_ic_selected);
					news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

					news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_choose));
					news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

					break;
				// 行情
				case 5:

					news_tv_market.setBackgroundResource(R.drawable.news_ic_selected);
					news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));

					news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_choose));
					news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
					news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

					break;
			}
		}

	}

}
