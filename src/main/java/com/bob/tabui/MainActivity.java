package com.bob.tabui;

import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import java.util.*;


public class MainActivity extends FragmentActivity implements OnClickListener {
    private ViewPager viewPager;//v4包下的一个控件，即就是一个可以左右滑动切换的东东
    private FragmentPagerAdapter adapter;//和ListView一个道理，集合——>适配器——>控件
    private List<Fragment> fragments = new ArrayList<>();

    /*
    底部的三个按钮以及所在布局，这里我们把这三个布局添加点击时间监听即可。三个按钮用来修改其背景图
     */
    private LinearLayout tab01;
    private LinearLayout tab02;
    private LinearLayout tab03;

    private ImageButton ib01;
    private ImageButton ib02;
    private ImageButton ib03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initView();//集中实例化这几个控件
        initEvent();

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {//定义一个数据适配器
            @Override//相当于添加数据了
            public Fragment getItem(int position) {//position即就是当前被选中或者要显示的数据在数据集合里的下标
                return fragments.get(position);//返回需要显示的控件
            }

            @Override
            public int getCount() {//获取数据集大小
                return fragments.size();
            }
        };
        viewPager.setAdapter(adapter);//将适配器进行最后一步的操作，安装到控件中

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {//为ViewPager添加监听以随其动作更新ui，这点不同于BaseAdapter
            //主要原因在于我们需要将ViewPager和底部按钮进行联动处理，否则也用不着监听了

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                Log.i("logging", position+"");
                resetTabBtn();//一定要在修改对应选中的按钮状态时先重置所有按钮，否则会让前边的效果影响后边
                switch (position) {//在响应该页被选中时，需要同时修改底部对应的按钮状态
                    case 0:
                        ib01.setImageResource(R.mipmap.one_clicked);
                        break;
                    case 1:
                        ib02.setImageResource(R.mipmap.two_clicked);
                        break;
                    case 2:
                        ib03.setImageResource(R.mipmap.one_clicked);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //viewPager.setCurrentItem(0);//默认选中的是第一页，当然啦，我们也可以在这个地方进行自定义
        /**
         *需要说明的是，如果你不设置这个或者设置为和系统默认的一样的话，onPageSelected是不会被调用的
         * api: this method will be invoked when a new page becomes selected;
         * 接下来的读者自行脑补吧，哈哈～～～
         */
    }

    protected void resetTabBtn() {//重置每一个按钮的图标为灰暗
        ib01.setImageResource(R.mipmap.one_normal);
        ib02.setImageResource(R.mipmap.two_normal);
        ib03.setImageResource(R.mipmap.three_normal);
    }

    private void initView() {

        tab01 = (LinearLayout) findViewById(R.id.ll_first);
        tab02 = (LinearLayout) findViewById(R.id.ll_second);
        tab03 = (LinearLayout) findViewById(R.id.ll_third);

        ib01 = (ImageButton) findViewById(R.id.btn_first);//在这里我觉得不用以上三个布局进行查找也可以，博友如有高见，还请指出
        ib01.setImageResource(R.mipmap.one_clicked);//如果默认第一个，那就直接在这里进行选定高亮操作吧

        ib02 = (ImageButton) findViewById(R.id.btn_second);
        ib03 = (ImageButton) findViewById(R.id.btn_third);

        Tab01 frag01 = new Tab01();//实例化3个Fragment
        Tab02 frag02 = new Tab02();
        Tab03 frag03 = new Tab03();
        fragments.add(frag01);//添加到集合
        fragments.add(frag02);
        fragments.add(frag03);
    }

    private void initEvent() {
        tab01.setOnClickListener(this);
        tab02.setOnClickListener(this);
        tab03.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {//点击底部按钮对ViewPager进行切换
        int currentIndex = 0;
        switch (view.getId()) {//只需要选择被选中页，底部按钮会自动在onPageSelected中更新
            case R.id.ll_first:
                currentIndex = 0;
                break;
            case R.id.ll_second:
                currentIndex = 1;
                break;
            case R.id.ll_third:
                currentIndex = 2;
        }
        viewPager.setCurrentItem(currentIndex);//点击后切换当前选中page，或者说是fragment
    }


/*
    这一段是按照翔神写的，以上做了相应的重构
    public void setSelect(int i)
    {
        switch (i) {//在响应该页被选中时，需要同时修改底部对应的按钮状态
            case 0:
                ((ImageButton) findViewById(R.id.btn_first)).setImageResource(R.mipmap.one_clicked);
                break;
            case 1:
                ((ImageButton) findViewById(R.id.btn_second)).setImageResource(R.mipmap.two_clicked);
                break;
            case 2:
                ((ImageButton) findViewById(R.id.btn_third)).setImageResource(R.mipmap.one_clicked);
        }
        viewPager.setCurrentItem(i);//设置当前第i页为选中状态
    }*/
}
