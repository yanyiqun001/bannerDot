package com.bannerdot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;



/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class DemoActivity extends AppCompatActivity {
    BezierBannerDotDemo bd;
    float mStartX;
    int TOUCH_MOVE_MAX = 700;
    float progress;
    //向右滑 向左滚动
    public static int DIRECTION_LEFT = 1;
    //向左滑 向右滚动
    public static int DIRECTION_RIGHT = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        bd = (BezierBannerDotDemo) this.findViewById(R.id.bd);
        findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        if (x - mStartX > 0) {
                            progress = x - mStartX >= TOUCH_MOVE_MAX ? 1 : (x - mStartX) / (TOUCH_MOVE_MAX);
                            bd.setDirection(DIRECTION_RIGHT);
                            bd.setProgress(progress);
                        } else if (x - mStartX < 0) {
                            progress = mStartX - x >= TOUCH_MOVE_MAX ? 1 : (mStartX - x) / (TOUCH_MOVE_MAX);
                            bd.setDirection(DIRECTION_LEFT);
                            bd.setProgress(progress);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (progress >= 1.0f) {
                            bd.resetProgress();
                            return true;
                        }
                        return true;
                }
                return false;
            }
        });
    }


}
