package com.bannerdot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class BezierBannerDotDemo extends View {
    private Paint mCirclePaint;
    private Paint mPointPaint;
    private Path mPath = new Path();
    private Path mPath2 = new Path();
    //间隔距离
    private float distance = 80;
    //被选中圆初始半径
    private float mRadius = 50;
    //被选中圆变化半径
    private float mChangeRadius;
    //辅助圆变化半径
    private float mSupportChangeRadius;
    //被选中圆圆心左边
    float mCenterPointX;
    float mCenterPointY;
    //辅助圆圆心坐标
    float mSupportCircleX;
    float mSupportCircleY;
    //第一阶段运动进度
    private float mProgress = 0;
    //第二阶段运动进度
    private float mProgress2 = 0;
    //整体运动进度 也是原始进度
    private float mOriginProgress;
    //第一阶段运动
    private int MOVE_STEP_ONE=1;
    //第二阶段运动
    private int MOVE_STEP_TWO=2;
    float controlPointX;
    float controlPointY;
    float mStartX;
    float mStartY;
    float endPointX ;
    float endPointY;
    private int mDrection;
    //向右滑 向左滚动
    public static int DIRECTION_LEFT = 1;
    //向左滑 向右滚动
    public static int DIRECTION_RIGHT = 2;

    Interpolator accelerateinterpolator = new AccelerateDecelerateInterpolator();


    public BezierBannerDotDemo(Context context) {
        this(context, null);
    }

    public BezierBannerDotDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierBannerDotDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        moveToNext();
    }

    private void initPaint() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        p.setDither(true);
        mCirclePaint = p;


        Paint p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(0xFF000000);
        p2.setStyle(Paint.Style.FILL);
        p2.setAntiAlias(true);
        p2.setDither(true);
        mPointPaint = p2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = (int) (mRadius * 2 * 2 +  distance + getPaddingLeft() + getPaddingRight());
        int height = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom());

        int mHeight, mWidth;

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = Math.min(widthSize, width);
        } else {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(heightSize, height);
        } else {
            mHeight = heightSize;
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.drawCircle(mSupportCircleX, mSupportCircleY, mSupportChangeRadius, mCirclePaint);
        canvas.drawCircle(mCenterPointX, mCenterPointY, mChangeRadius, mCirclePaint);
        canvas.drawPath(mPath, mCirclePaint);

        canvas.drawCircle(controlPointX,controlPointY,5,mPointPaint);
        canvas.drawCircle(mStartX,mStartY,5,mPointPaint);
        canvas.drawCircle(endPointX,endPointY,5,mPointPaint);
        canvas.restore();

    }

    public void setProgress(float progress) {
        mOriginProgress=progress;
        if(progress<=0.5){
            mProgress = progress/0.5f;
            mProgress2=0;
        }else{
            mProgress2=(progress-0.5f)/0.5f;
            mProgress=1;
        }
        if(mDrection==DIRECTION_RIGHT) {
            moveToNext();
        }else{
            moveToPrivious();
        }

        invalidate();
    }

    /**
     * 向右移动
     */
    private void moveToNext() {
        mPath.reset();
        mPath2.reset();
        float mRadiusProgress = accelerateinterpolator.getInterpolation(mOriginProgress);
        //选中圆圆心
        mCenterPointX = getValue(getCenterPointAt(1),getCenterPointAt(2)-mRadius,MOVE_STEP_TWO);
        mCenterPointY = mRadius;
        //选中圆半径
        mChangeRadius = getValue(mRadius,0,mRadiusProgress);
        //起点与选中圆圆心间的角度
        double radian = Math.toRadians(getValue(45, 0,MOVE_STEP_ONE));
        //X轴距离圆心距离
        float mX = (float) (Math.sin(radian) * mChangeRadius);
        //Y轴距离圆心距离
        float mY = (float) (Math.cos(radian) * mChangeRadius);

        //辅助圆圆心
        mSupportCircleX = getValue(getCenterPointAt(1)+mRadius, getCenterPointAt(2),MOVE_STEP_ONE);
        mSupportCircleY = mRadius;
        //辅助圆半径
        mSupportChangeRadius = getValue(0, mRadius, mRadiusProgress);
        //终点与辅助圆圆心间的角度
        double supportradian = Math.toRadians(getValue(0, 45,MOVE_STEP_TWO));
        //X轴距离圆心距离
        float msupportradianX = (float) (Math.sin(supportradian) * mSupportChangeRadius);
        //Y轴距离圆心距离
        float msupportradianY = (float) (Math.cos(supportradian) * mSupportChangeRadius);
        //起点
        mStartX = mCenterPointX + mX;
        mStartY = mCenterPointY - mY;

        //终点
        endPointX = mSupportCircleX-msupportradianX;
        endPointY = mRadius - msupportradianY;
        //控制点
        controlPointX = getValueForAll(2 * mRadius, getCenterPointAt(2)-mRadius);
        controlPointY = mRadius;
        //移动至起点
        mPath.moveTo(mStartX, mStartY);
        //形成闭合区域
        mPath.quadTo(controlPointX, controlPointY, endPointX, endPointY);
        mPath.lineTo(endPointX, mRadius + msupportradianY);
        mPath.quadTo(controlPointX, mRadius , mStartX, mStartY + 2 * mY);
        mPath.lineTo(mStartX, mStartY);

    }
    /**
     * 向左移动(与向右过程大致相同)
     */
    private void moveToPrivious() {
        mPath.reset();
        mPath2.reset();
        float mRadiusProgress = accelerateinterpolator.getInterpolation(mOriginProgress);
        //选中圆
        mCenterPointX = getValue(getCenterPointAt(2),getCenterPointAt(1)+mRadius,MOVE_STEP_TWO);
        mCenterPointY = mRadius;
        mChangeRadius = getValue(mRadius,0,mRadiusProgress);

        double radian = Math.toRadians(getValue(45, 0,MOVE_STEP_ONE));
        float mX = (float) (Math.sin(radian) * mChangeRadius);
        float mY = (float) (Math.cos(radian) * mChangeRadius);

        //辅助圆
        mSupportCircleX = getValue(getCenterPointAt(2)-mRadius, getCenterPointAt(1),MOVE_STEP_ONE);
        mSupportCircleY = mRadius;
        mSupportChangeRadius = getValue(0, mRadius, mRadiusProgress);

        double supportradian = Math.toRadians(getValue(0, 45,MOVE_STEP_TWO));
        float msupportradianX = (float) (Math.sin(supportradian) * mSupportChangeRadius);
        float msupportradianY = (float) (Math.cos(supportradian) * mSupportChangeRadius);

        mStartX = mCenterPointX - mX;
        mStartY = mCenterPointY - mY;

        endPointX = mSupportCircleX+msupportradianX;
        endPointY = mRadius - msupportradianY;

        controlPointX = getValueForAll(getCenterPointAt(2)-mRadius, getCenterPointAt(1)+mRadius);
        controlPointY = mRadius;

        mPath.moveTo(mStartX, mStartY);
        mPath.quadTo(controlPointX, controlPointY, endPointX, endPointY);
        mPath.lineTo(endPointX, mRadius + msupportradianY);
        mPath.quadTo(controlPointX, mRadius , mStartX, mStartY + 2 * mY);
        mPath.lineTo(mStartX, mStartY);
    }

    /**
     * 获取当前值(适用分阶段变化的值)
     * @param start 初始值
     * @param end  终值
     * @param step  第几活动阶段
     * @return
     */
    public float getValue(float start, float end, int step) {
        if(step==MOVE_STEP_ONE) {
            return start + (end - start) * mProgress;
        }else{

            return start + (end - start) * mProgress2;
        }
    }
    /**
     * 获取当前值（适用全过程变化的值）
     * @param start 初始值
     * @param end  终值
     * @return
     */
    public float getValueForAll(float start, float end){
         return start + (end - start) * mOriginProgress;
    }

    /**
     * 通过进度获取当前值
     * @param start 初始值
     * @param end 终值
     * @param progress 当前进度
     * @return
     */
    public float getValue(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    /**
     * 获取圆心X坐标
     * @param index 第几个圆
     * @return
     */
    private float getCenterPointAt(int index) {
        if (index == 1) {
            return mRadius;
        }
        return mRadius*3+distance;
    }
    /**
     * 设置当前方向
     */
    public void setDirection(int direction) {
        mDrection = direction;
    }
    /**
     * 重置进度
     */
    public void resetProgress() {
        mProgress = 0;
        mProgress2 = 0;
        mOriginProgress = 0;
    }
}
