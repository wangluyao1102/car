package com.car.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.car.application.LocalApplication;
import com.car.cache.AsyncImageLoader;
import com.car.entity.NewsImageItem;
import com.car.entity.NewsItem;
import com.car.util.ConstantsUtil;
import com.car.view.photoview.PhotoView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class NewsPictureActivity  extends BaseActivity {
    // 获取的数据
    private List<NewsImageItem> dataList;
    @ViewInject(R.id.car_picture_iv_back)
    ImageView car_picture_iv_back;
    @ViewInject(R.id.car_picture_tv_index)
    TextView car_picture_tv_index;
    @ViewInject(R.id.car_picture_vp)
    ViewPager car_picture_vp;


    @Override
    protected int getLayoutId() {
        return R.layout.content_news_picture;
    }

    @Override
    protected void initParams() {
       car_picture_tv_index.setText("1/"+dataList.size());

        // 绑定适配器
        car_picture_vp.setAdapter(new ViewPagerAdapter());
        //ViewPagerChangeListener
        car_picture_vp.setOnPageChangeListener(new ViewPagerChangeListener());
        car_picture_vp.setCurrentItem(0);

    }

    // 静态方法启动activity
    public static void startActivity(Context context, String datas) {
        Intent intent = new Intent(context, NewsPictureActivity.class);
        intent.putExtra("datas", datas);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataList = JSONArray.parseArray(getIntent().getStringExtra("datas"), NewsImageItem.class);
        super.onCreate(savedInstanceState);
    }

    // 控件点击事件
    @OnClick(R.id.car_picture_iv_back)
    public void viewOnClick(View view)
    {
        finish();
    }


    private class ViewPagerAdapter extends PagerAdapter{
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
        @Override
        public int getCount() {
            return dataList.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.news_picture_item, null);

            PhotoView picture_iv_item = (PhotoView) view.findViewById(R.id.picture_iv_item);
            // 给imageview设置一个tag，保证异步加载图片时不会乱序
            picture_iv_item.setTag( dataList.get(position).url);
            // 开启异步加载图片,显示图片宽度为screenW
            AsyncImageLoader.getInstance(NewsPictureActivity.this).loadBitmaps(view, picture_iv_item,  dataList.get(position).url, LocalApplication.getInstance().screenW, 0);
            container.addView(view);
            return view;
        }
    }

    // viewpager切换监听器
    private class ViewPagerChangeListener implements ViewPager.OnPageChangeListener
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
            car_picture_tv_index.setText((arg0 + 1) + "/" + dataList.size());
        }

    }
}
