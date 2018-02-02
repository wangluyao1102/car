package com.car.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.car.activity.NewsPictureActivity;
import com.car.activity.R;
import com.car.application.LocalApplication;
import com.car.cache.AsyncImageLoader;
import com.car.entity.NewsItem;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.util.JStringKit;
import com.car.view.ToastMaker;

/**
 * 推荐新闻适配器
 * 
 * @author blue
 * 
 */
public class NewsHeadAdapter extends PagerAdapter
{
	private List<NewsItem> datas;

	private Context context;
	private LayoutInflater layoutInflater;

	public NewsHeadAdapter(Context context, List<NewsItem> datas)
	{
		this.datas = datas;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 刷新适配器数据源
	 * 
	 * @author andrew
	 * @param datas
	 */
	public void refreshDatas(List<NewsItem> datas)
	{
		this.datas = datas;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		//return 5;
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		((ViewPager) container).removeView((View) object);
	}

	@SuppressLint("InflateParams")
	@Override
	public Object instantiateItem(ViewGroup container, final int position)
	{
		View layout = layoutInflater.inflate(R.layout.news_head_item, null);
		ImageView viewpager_item_img = (ImageView) layout.findViewById(R.id.viewpager_item_img);

		//viewpager_item_img.setTag(datas.get(position % datas.size()).cover_image);
		viewpager_item_img.setTag(datas.get(position % 5).cover_image);



		// 开启异步加载图片,显示图片比例为screenW*200dp
		AsyncImageLoader.getInstance(context).loadBitmaps(layout, viewpager_item_img,  datas.get(position % 5).cover_image, LocalApplication.getInstance().screenW, DisplayUtil.dip2px(context, 200));

		viewpager_item_img.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int curr=position % 5;

				if (JStringKit.isNotEmpty(datas.get(curr).image_list))
				{




					NewsPictureActivity.startActivity(context, datas.get(curr).image_list);
				} else
				{
					ToastMaker.showShortToast("新闻详情");
				}
			}
		});

		((ViewPager) container).addView(layout);
		return layout;
	}

	// 配合notifyDataSetChanged方法刷新viewpager
	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}
}
