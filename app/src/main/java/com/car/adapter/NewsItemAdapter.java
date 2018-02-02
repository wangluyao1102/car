package com.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.car.activity.R;
import com.car.cache.AsyncImageLoader;
import com.car.entity.NewsItem;
import com.car.util.DisplayUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2017-10-17.
 */

public class NewsItemAdapter extends SimpleBaseAdapter<NewsItem> {
    private ListView listView;
    public NewsItemAdapter(Context c, List<NewsItem> datas,ListView listView) {
        super(c, datas);
        this.listView=listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EntityHolder entityHolder = null;
        if (convertView == null)
        {
            entityHolder = new EntityHolder();

            convertView = layoutInflater.inflate(R.layout.news_item, null);

            ViewUtils.inject(entityHolder, convertView);

            convertView.setTag(entityHolder);
        } else
        {
            entityHolder = (EntityHolder) convertView.getTag();
        }

        entityHolder.item_tv_title.setText(datas.get(position).title);

        // 给imageview设置一个tag，保证异步加载图片时不会乱序h
        entityHolder.item_iv_img.setTag(datas.get(position).cover_image);
        // 开启异步加载图片,显示大小为100dp*100dp
        AsyncImageLoader.getInstance(c).loadBitmaps(listView, entityHolder.item_iv_img, datas.get(position).cover_image, DisplayUtil.dip2px(c, 100), DisplayUtil.dip2px(c, 100));
        return convertView;
    }
    private class EntityHolder
    {
        @ViewInject(R.id.item_tv_title)
        TextView item_tv_title;
        @ViewInject(R.id.item_iv_img)
        ImageView item_iv_img;
    }

}
