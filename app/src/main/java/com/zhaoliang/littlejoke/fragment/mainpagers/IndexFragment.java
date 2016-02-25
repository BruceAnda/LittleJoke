package com.zhaoliang.littlejoke.fragment.mainpagers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.TabPageIndicator;
import com.zhaoliang.littlejoke.R;
import com.zhaoliang.littlejoke.fragment.base.BaseFragment;
import com.zhaoliang.littlejoke.fragment.indexpagers.Index1Fragment;
import com.zhaoliang.littlejoke.fragment.indexpagers.Index2Fragment;
import com.zhaoliang.littlejoke.fragment.indexpagers.Index3Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 首页
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {

    @ViewInject(R.id.index_pagers)
    private ViewPager indexPagers;

    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("--------------" + savedInstanceState == null);

        IndexPagerAdapter adapter = new IndexPagerAdapter(getFragmentManager());
        indexPagers.setAdapter(adapter);
        indicator.animate();
        indicator.setViewPager(indexPagers);
    }

    class IndexPagerAdapter extends FragmentPagerAdapter {

        public IndexPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Index1Fragment();
                case 1:
                    return new Index2Fragment();
                case 2:
                    return new Index3Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "推荐";
                case 1:
                    return "经典";
                case 2:
                    return "最新";
            }
            return null;
        }
    }
}
