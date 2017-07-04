package com.bannerdot;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static com.bannerdot.R.id.bd;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class VpActivity extends AppCompatActivity {
    BezierBannerDot bezierBannerDot;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private int[] resource={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
    private List<ImageView> viewlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp);
        viewPager = (ViewPager) this.findViewById(R.id.vp);
        bezierBannerDot = (BezierBannerDot) this.findViewById(bd);
        for (int i = 0; i < resource.length ; i++) {
            ImageView imageView=new ImageView(VpActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewlist.add(imageView) ;
        }
        pagerAdapter=new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        bezierBannerDot.attachToViewpager(viewPager);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return resource.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewlist.get(position));
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView v = viewlist.get(position);
            v.setImageResource(resource[position]);
            container.addView(v);
            return v;
        }
    }
}
