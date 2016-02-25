package com.zhaoliang.littlejoke.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zhaoliang.littlejoke.R;
import com.zhaoliang.littlejoke.activity.base.BaseActivity;
import com.zhaoliang.littlejoke.fragment.mainpagers.FollowFragment;
import com.zhaoliang.littlejoke.fragment.mainpagers.IndexFragment;
import com.zhaoliang.littlejoke.fragment.mainpagers.MeFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.tab_index)
    private TextView tabIndex;      // 首页
    @ViewInject(R.id.tab_follow)
    private TextView tabFollow;     // 关注
    @ViewInject(R.id.tab_me)
    private TextView tabMe;        // 我

    private int checkId = R.id.tab_index;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            changeTabAndPage(checkId);
        }
    }

    @Event({R.id.tab_index, R.id.tab_follow, R.id.tab_me})
    private void onTabClick(View view) {
        if (checkId == view.getId()) {
            return;
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        checkId = view.getId();
        changeTabAndPage(checkId);
    }

    /**
     * 改变tab和页面
     *
     * @param id
     */
    private void changeTabAndPage(int id) {
        tabIndex.setSelected(false);
        tabFollow.setSelected(false);
        tabMe.setSelected(false);
        switch (id) {
            case R.id.tab_index:
                tabIndex.setSelected(true);
                fragmentTransaction.replace(R.id.fl_content, IndexFragment.instantiate(this, IndexFragment.class.getName()), "IndexFragment");
                break;
            case R.id.tab_follow:
                tabFollow.setSelected(true);
                fragmentTransaction.replace(R.id.fl_content, FollowFragment.instantiate(this, FollowFragment.class.getName()), "FollowFragment");
                break;
            case R.id.tab_me:
                tabMe.setSelected(true);
                fragmentTransaction.replace(R.id.fl_content, MeFragment.instantiate(this, MeFragment.class.getName()), "MeFragment");
                break;
        }
        fragmentTransaction.commit();
    }

}
