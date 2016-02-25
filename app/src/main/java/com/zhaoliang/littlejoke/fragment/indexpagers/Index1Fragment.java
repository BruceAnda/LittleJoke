package com.zhaoliang.littlejoke.fragment.indexpagers;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhaoliang.littlejoke.R;
import com.zhaoliang.littlejoke.constant.Constant;
import com.zhaoliang.littlejoke.domain.IndexRequestParams;
import com.zhaoliang.littlejoke.domain.IndexResponse;
import com.zhaoliang.littlejoke.fragment.base.BaseFragment;
import com.zhaoliang.littlejoke.widget.SuperSwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 首页
 */
@ContentView(R.layout.fragment_index1)
public class Index1Fragment extends BaseFragment {

    private static final int UPDATE_DATA = 1;
    private static final int PULL = 2;
    private static final int PUSH = 3;
    private OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private IndexRequestParams param;

    @ViewInject(R.id.list_view)
    private ListView listView;


    private ListAdapter adapter;

    @ViewInject(R.id.swipe_refresh)
    private SuperSwipeRefreshLayout swipeRefreshLayout;

    private boolean pull;
    private boolean push;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_DATA:
                    adapter.notifyDataSetChanged();
                    break;
                case PULL:
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    break;
                case PUSH:
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setLoadMore(false);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ListAdapter(new ArrayList<IndexResponse.DataEntity>());
        listView.setAdapter(adapter);

        client = new OkHttpClient();
        param = new IndexRequestParams("get_article", "2016-02-22 04:04:00", "old", 10);

        post();

        // init SuperSwipeRefreshLayout
        swipeRefreshLayout.setHeaderViewBackgroundColor(0xff888888);
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setFooterView(createFooterView());
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);

                        pull = true;
                        post();
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        // pull distance
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        swipeRefreshLayout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);

                        push = true;
                        post();
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {
                        // TODO Auto-generated method stub

                    }

                });
    }

    private void post() {
        RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(param));
        Request request = new Request.Builder()
                .url(Constant.baseUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                IndexResponse indexResponse = com.alibaba.fastjson.JSON.parseObject(response.body().string(), IndexResponse.class);
                if (pull) {
                    adapter.dataEntities.addAll(0, indexResponse.getData());
                    pull = false;
                    mHandler.sendEmptyMessage(PULL);
                } else if (push) {
                    adapter.dataEntities.addAll(indexResponse.getData());
                    push = false;
                    mHandler.sendEmptyMessage(PUSH);
                }
                adapter.dataEntities.addAll(indexResponse.getData());
                mHandler.sendEmptyMessage(UPDATE_DATA);
            }
        });
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    class ListAdapter extends BaseAdapter {

        List<IndexResponse.DataEntity> dataEntities;

        public ListAdapter(List<IndexResponse.DataEntity> dataEntities) {
            this.dataEntities = dataEntities;
        }

        @Override
        public int getCount() {
            return dataEntities.size();
        }

        @Override
        public IndexResponse.DataEntity getItem(int position) {
            return dataEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.index1_list_item, null);
                convertView.setTag(viewHolder);
                x.view().inject(viewHolder, convertView);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            IndexResponse.DataEntity item = getItem(position);
            viewHolder.tvTitle.setText(item.getTitle());
            viewHolder.tvContent.setText(item.getContent());
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.tv_index1_title)
            TextView tvTitle;
            @ViewInject(R.id.tv_index1_content)
            TextView tvContent;
        }
    }
}
