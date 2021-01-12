package com.example.p_scrollview_tablayout_viewpager_fragment_listview_viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomViewPager mViewPager;
    List<Fragment> mFragments;

    String[] mTitles = new String[]{
            "主页", "微博", "相册"
    };
    private TabLayout mTabLayout;
    private MyScrollview scroll;
    private LinearLayout layout;
    private RelativeLayout rl_layout;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 第一步，初始化ViewPager和TabLayout
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mTabLayout =  findViewById(R.id.tabs);
        layout = (LinearLayout) findViewById(R.id.layout);
        rl_layout =  findViewById(R.id.rl_layout);
        scroll =  findViewById(R.id.scroll);
//        iv =  findViewById(R.id.iv);
        setupViewPager();
    }



    private void setupViewPager() {

        mFragments = new ArrayList<>();
        ListFragment listFragment = new ListFragment(mViewPager, 0);
        ListFragment2 listFragment2 = new ListFragment2(mViewPager, 1);
        ListFragment3 listFragment3 = new ListFragment3(mViewPager, 2);
        mFragments.add(listFragment);
        mFragments.add(listFragment2);
        mFragments.add(listFragment3);

        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter =
                new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        scroll.setScrollViewListener(new MyScrollview.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollview scrollView, int l, int t, int oldl, int oldt) {

                if (t >iv.getHeight() && mTabLayout.getParent() == layout) {
                    layout.removeView(mTabLayout);
                    rl_layout.addView(mTabLayout);
                } else if (t < iv.getHeight() && mTabLayout.getParent() == rl_layout) {
                    rl_layout.removeView(mTabLayout);
                    layout.addView(mTabLayout);
                }
            }
        });

    }
}