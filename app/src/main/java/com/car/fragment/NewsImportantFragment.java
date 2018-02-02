package com.car.fragment;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.car.activity.R;
import com.car.adapter.NewsHeadAdapter;
import com.car.adapter.NewsItemAdapter;
import com.car.application.LocalApplication;
import com.car.entity.NewsItem;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.util.JListKit;
import com.car.view.ProgressWheel;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnScroll;
import com.lidroid.xutils.view.annotation.event.OnScrollStateChanged;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;


/**
 * 资讯-要闻
 *
 * @author blue
 */
public class NewsImportantFragment extends BaseFragment
{



	@ViewInject(R.id.ptr)
	PtrFrameLayout ptr;
	@ViewInject(R.id.news_important_lv)
	ListView news_important_lv;
	@ViewInject(R.id.pw)
	ProgressWheel pw;
	// 数据源
	private List<NewsItem> datas = JListKit.newArrayList();
	private List<NewsItem> headList=JListKit.newArrayList();
	// 适配器
	private NewsItemAdapter adapter;
	// 是否为最后一行
	private boolean isLastRow = false;
	// 是否还有更多数据
	private boolean isMore = true;
	// 是否正在加载数据
	private boolean isLoading = false;
	// 加载布局
	private LinearLayout loading_llyt;
	private Integer pageIndex=0;
	private Integer pageSize=20;
	//幻灯片
	private ViewPager new_head_vp;
	private FrameLayout news_head_view;
	private ViewPager news_head_vp;
	private NewsHeadAdapter headAdapter;

	private TextView news_head_tv;
	private TextView news_head_tv1;
	private TextView news_head_tv2;
	private TextView news_head_tv3;
	private TextView news_head_tv4;
	private TextView news_head_tv5;
	private List<TextView> textViewList=JListKit.newArrayList();
	// 当前图片的索引号
	private int currentItem = 0;
	//自动轮播定时器
	private ScheduledExecutorService scheduledExecutorService;
	private boolean isUpdate = false;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_important_main;
	}

	@Override
	protected void initParams()
	{

		//加载文字
		pw.setText("loading");
		//开始旋转加载
		pw.spin();

		//头部布局

		news_head_view =(FrameLayout) getLayoutInflater(null).inflate(R.layout.news_head_view, null);
		news_head_vp= (ViewPager) news_head_view.findViewById(R.id.news_head_vp);

		news_head_tv= (TextView) news_head_view.findViewById(R.id.news_head_tv);
		news_head_tv1= (TextView) news_head_view.findViewById(R.id.news_head_tv1);
		news_head_tv2= (TextView) news_head_view.findViewById(R.id.news_head_tv2);
		news_head_tv3= (TextView) news_head_view.findViewById(R.id.news_head_tv3);
		news_head_tv4= (TextView) news_head_view.findViewById(R.id.news_head_tv4);
		news_head_tv5= (TextView) news_head_view.findViewById(R.id.news_head_tv5);
		textViewList.add(news_head_tv1);
		textViewList.add(news_head_tv2);
		textViewList.add(news_head_tv3);
		textViewList.add(news_head_tv4);
		textViewList.add(news_head_tv5);

		headAdapter=new NewsHeadAdapter(context,headList);
		news_head_vp.setAdapter(headAdapter);
		news_head_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){


			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				currentItem = position % 5;
				news_head_tv.setText(headList.get(currentItem).title);
				for (int i = 0; i < 5; i++)
				{
					if (i == currentItem)
					{
						textViewList.get(i).setBackgroundColor(context.getResources().getColor(R.color.news_head_cl_choose));
					} else
					{
						textViewList.get(i).setBackgroundColor(context.getResources().getColor(R.color.news_head_cl_unchoose));
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});





		adapter=new NewsItemAdapter(context,datas,news_important_lv);


		// 底部布局
		loading_llyt = (LinearLayout) getLayoutInflater(null).inflate(R.layout.listview_loading_view, null);
		// 增加头部和底部显示布局
		news_important_lv.addHeaderView(news_head_view);
		news_important_lv.addFooterView(loading_llyt);
		//加载listView数据
		news_important_lv.setAdapter(adapter);

		initPtr();//加载下拉刷新
		loadHeadData();//加载头部数据
		loadData();//加载列表数据

	}


	// 加载头部数据
	private void loadHeadData()
	{


		RequestParams params = new RequestParams();
		params.addBodyParameter("pageIndex", 1+"");
		params.addBodyParameter("pageSize", pageSize+"");


		LocalApplication.getInstance().httpUtils.send(HttpRequest.HttpMethod.POST, ConstantsUtil.SERVER_URL+"catlist", params, new RequestCallBack<String>()
		{

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				// 回送消息
				pw.stopSpinning();
				pw.setVisibility(View.GONE);
				ToastMaker.showShortToast("请求失败,请检查网络后重试");

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg)
			{
				String list = JSONObject.parseObject(arg.result).getString("list");
				List<NewsItem> tmp = JSONObject.parseArray(list, NewsItem.class);
				if (JListKit.isNotEmpty(tmp))
				{
					headList.addAll(tmp);
					headAdapter.refreshDatas(headList);
					// 初始选中项
					news_head_vp.setCurrentItem(5 * 1000);
					news_head_tv.setText(headList.get(0).title);

					// 要闻推荐最多不超过5个，低于5个时指示器需要做相应调整
					if (tmp.size() < 5)
					{
						for (int i = 0; i < 5 - tmp.size(); i++)
						{
							textViewList.get(textViewList.size() - 1 - i).setVisibility(View.GONE);
						}
					}
				}


			}
		});




	}





	//加载列表数据
	private  void loadData(){
		isLoading=true;
		pageIndex++;
		RequestParams params = new RequestParams();
		params.addBodyParameter("pageIndex", pageIndex+"");
		params.addBodyParameter("pageSize", pageSize+"");


		LocalApplication.getInstance().httpUtils.send(HttpRequest.HttpMethod.POST, ConstantsUtil.SERVER_URL+"catlist", params, new RequestCallBack<String>()
		{

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{

				if(isUpdate){
					ptr.refreshComplete();
				}else{
					// 回送消息
					pw.stopSpinning();
					pw.setVisibility(View.GONE);
				}


				ToastMaker.showShortToast("请求失败,请检查网络后重试");

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg)
			{
				String list = JSONObject.parseObject(arg.result).getString("list");
				List<NewsItem> tmp = JSONObject.parseArray(list, NewsItem.class);

				pw.stopSpinning();
				pw.setVisibility(View.GONE);
				news_important_lv.setVisibility(View.VISIBLE);

				if (JListKit.isNotEmpty(tmp))
				{
					if (pageIndex == 1)
					{
						// 移除底部加载布局
						if (tmp.size() < pageSize)
						{
							news_important_lv.removeFooterView(loading_llyt);
						}
					}
					if(isUpdate){
						isUpdate=false;
						ptr.refreshComplete();
						datas.clear();
					}
					datas.addAll(tmp);
					adapter.refreshDatas(datas);
				} else
				{
					isMore = false;
					news_important_lv.removeFooterView(loading_llyt);
					ToastMaker.showShortToast("已没有更多数据");
				}
				isLoading = false;
			}
		});

	}




	@OnItemClick(R.id.news_important_lv)
	public void onItemClick(AdapterView<?> adapterView, View itemView, int position, long itemId)
	{
		ToastMaker.showShortToast(position + "");
	}

	@OnScroll(R.id.news_important_lv)
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0)
		{
			isLastRow = true;
		}
	}

	@OnScrollStateChanged(R.id.news_important_lv)
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (isLastRow && scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			if (!isLoading && isMore)
			{
				loadData();
			}
			isLastRow = false;
		}
	}




	public Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what) {
				// 切换图片
				case 1:

					if (JListKit.isNotEmpty(headList))
					{
						news_head_vp.setCurrentItem(currentItem);
					}

					break;

				default:
					break;
			}
		}

	};


	/**
	 * 换行切换任务
	 *
	 * @author Administrator
	 *
	 */
	private class ScrollTask implements Runnable
	{

		public void run()
		{
			synchronized (news_head_vp)
			{
				if(currentItem<5){
					currentItem++;
				}else{
					currentItem=0;
				}

				// 通过Handler切换图片
				handler.sendEmptyMessage(1);
			}
		}

	}


	// 初始化下拉刷新
	private void initPtr()
	{
		// header
		StoreHouseHeader header = new StoreHouseHeader(context);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, DisplayUtil.dip2px(context, 15), 0, DisplayUtil.dip2px(context, 10));
		header.initWithString("CAR CAR");
		header.setTextColor(getResources().getColor(android.R.color.black));

		ptr.setHeaderView(header);
		ptr.addPtrUIHandler(header);
		ptr.setPtrHandler(new PtrHandler()
		{

			@Override
			public void onRefreshBegin(PtrFrameLayout frame)
			{
			    isUpdate=true;
                pageIndex=0;
				loadData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
			{
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, news_important_lv, header);
			}
		});


	/*	ptr.setPtrHandler(new PtrHandler()
		{

			@Override
			public void onRefreshBegin(PtrFrameLayout frame)
			{
				isUpdate = true;
				pageIndex++;
				loadData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
			{
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, news_important_lv, header);
			}
		});*/
	}







}
