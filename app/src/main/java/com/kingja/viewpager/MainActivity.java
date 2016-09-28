package com.kingja.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private LinearLayout ll_dot;
    private int[] idsArr = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
    private View[] dotArr = new View[idsArr.length];
    private ImageView[] imgArr = new ImageView[idsArr.length];
    private AutoRunnable mAutoRunnable;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp = (ViewPager) findViewById(R.id.vp);
        ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
        initDot();
        initImg();
        initDate();
        startScroll();


    }

    private void startScroll() {
        mAutoRunnable = new AutoRunnable();
        mAutoRunnable.startScroll();
    }

    class AutoRunnable implements Runnable {
        public void startScroll() {
            mHandler.removeCallbacks(mAutoRunnable);
            mHandler.postDelayed(mAutoRunnable, 1000);
        }

        @Override
        public void run() {
            mHandler.removeCallbacks(mAutoRunnable);
            int currentItem = vp.getCurrentItem();
            vp.setCurrentItem(++currentItem);
            mHandler.postDelayed(mAutoRunnable, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mAutoRunnable);
    }

    private void initDate() {
        vp.setAdapter(new AutoPagerAdapter());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < idsArr.length; i++) {
                    if (i == position % idsArr.length) {
                        dotArr[i].setBackgroundResource(R.drawable.cycle_selected);
                    } else {
                        dotArr[i].setBackgroundResource(R.drawable.cycle_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initImg() {
        for (int i = 0; i < idsArr.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundResource(idsArr[i]);
            imgArr[i] = imageView;
        }
    }

    private void initDot() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
        layoutParams.setMargins(0, 0, 20, 0);
        for (int i = 0; i < idsArr.length; i++) {
            View view = new View(this);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.cycle_selected);
            } else {
                view.setBackgroundResource(R.drawable.cycle_normal);
            }
            ll_dot.addView(view, layoutParams);
            dotArr[i] = view;

        }
    }

    class AutoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgArr[position % idsArr.length]);
            return imgArr[position % idsArr.length];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgArr[position % idsArr.length]);
        }
    }
}
