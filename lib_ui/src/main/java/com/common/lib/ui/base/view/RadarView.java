package com.common.lib.ui.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建时间: 2019/12/27 14:41 <br>
 * 作者: qiudengjiao <br>
 * 描述: 自定义雷达图
 */
public class RadarView extends View {

  private int mCount = 6;
  private float mAngle = (float) (Math.PI * 2 / mCount);

  private float mRadius;
  private int mCenterX;
  private int mCenterY;
  private String[] mTitles = { "A", "B", "C", "D", "E", "F" };
  private double[] mData = { 100, 60, 60, 60, 100, 50, 10, 20 }; //各维度分值
  private float maxValue = 100;             //数据最大值
  private Paint mPaint;
  private Paint mValuePaint;
  private Paint mTextPaint;

  public RadarView(Context context) {
    super(context);
    init();
  }

  public RadarView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  /**
   * 初始化
   */
  private void init() {

    mCount = Math.min(mData.length,mTitles.length);
    mPaint = new Paint();
    mPaint.setColor(Color.GRAY);
    mPaint.setStyle(Paint.Style.STROKE);

    mValuePaint = new Paint();
    mValuePaint.setAntiAlias(true);
    mValuePaint.setColor(Color.BLUE);
    mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    mTextPaint = new Paint();
    mTextPaint.setTextSize(20);
    mTextPaint.setStyle(Paint.Style.FILL);
    mTextPaint.setColor(Color.BLACK);
  }

  /**
   * 设置标题
   * @param titles
   */
  public void setTitles(String[] titles) {
    this.mTitles = titles;
  }

  /**
   * 设置数值
   * @param data
   */
  public void setData(double[] data) {
    this.mData = data;
  }


  public float getMaxValue() {
    return maxValue;
  }
  /**
   * 设置最大数值
   * @return
   */
  public void setMaxValue(float maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * 设置蜘蛛网颜色
   * @param color
   */
  public void setMainPaintColor(int color){
    mPaint.setColor(color);
  }

  /**
   * 设置标题颜色
   * @param color
   */
  public void setTextPaintColor(int color){
    mTextPaint.setColor(color);
  }

  /**
   * 设置覆盖局域颜色
   * @param color
   */
  public void setValuePaintColor(int color){
    mValuePaint.setColor(color);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    mRadius = Math.min(h, w)/2*0.9f;
    mCenterX = w / 2;
    mCenterY = h / 2;
    postInvalidate();

    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawPolygon(canvas);
    drawLines(canvas);
    drawText(canvas);
    drawRegion(canvas);
  }

  /**
   * 绘制正多边形
   */
  private void drawPolygon(Canvas canvas) {
    Path path = new Path();
    float r = mRadius / (mCount - 1);//r是蜘蛛丝之间的间距
    for (int i = 0; i < mCount; i++) {
      float curR = r * i;//当前半径
      path.reset();
      for (int j = 0; j < mCount; j++) {
        if (j == 0) {
          path.moveTo(mCenterX + curR, mCenterY);
        } else {
          //根据半径，计算出蜘蛛丝上每个点的坐标
          float x = (float) (mCenterX + curR * Math.cos(mAngle * j));
          float y = (float) (mCenterY + curR * Math.sin(mAngle * j));
          path.lineTo(x, y);
        }
      }
      path.close();//闭合路径
      canvas.drawPath(path, mPaint);
    }
  }

  /**
   * 绘制直线
   */
  private void drawLines(Canvas canvas) {
    Path path = new Path();
    for (int i = 0; i < mCount; i++) {
      path.reset();
      path.moveTo(mCenterX, mCenterY);
      float x = (float) (mCenterX + mRadius * Math.cos(mAngle * i));
      float y = (float) (mCenterY + mRadius * Math.sin(mAngle * i));
      path.lineTo(x, y);
      canvas.drawPath(path, mPaint);
    }
  }

  /**
   * 绘制文字
   */
  private void drawText(Canvas canvas) {
    Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    float fontHeight = fontMetrics.descent - fontMetrics.ascent;
    for (int i = 0; i < mCount; i++) {
      float x = (float) (mCenterX + (mRadius + fontHeight / 2) * Math.cos(mAngle * i));
      float y = (float) (mCenterY + (mRadius + fontHeight / 2) * Math.sin(mAngle * i));
      if (mAngle * i >= 0 && mAngle * i <= Math.PI / 2) {//第4象限
        canvas.drawText(mTitles[i], x, y, mTextPaint);
      } else if (mAngle * i >= 3 * Math.PI / 2 && mAngle * i <= Math.PI * 2) {//第3象限
        canvas.drawText(mTitles[i], x, y, mTextPaint);
      } else if (mAngle * i > Math.PI / 2 && mAngle * i <= Math.PI) {//第2象限
        float dis = mTextPaint.measureText(mTitles[i]);//文本长度
        canvas.drawText(mTitles[i], x - dis, y, mTextPaint);
      } else if (mAngle * i >= Math.PI && mAngle * i < 3 * Math.PI / 2) {//第1象限
        float dis = mPaint.measureText(mTitles[i]);//文本长度
        canvas.drawText(mTitles[i], x - dis, y, mTextPaint);
      }
    }
  }

  /**
   * 绘制区域
   */
  private void drawRegion(Canvas canvas) {
    Path path = new Path();
    mValuePaint.setAlpha(255);
    for (int i = 0; i < mCount; i++) {
      double percent = mData[i] / maxValue;
      float x = (float) (mCenterX + mRadius * Math.cos(mAngle * i) * percent);
      float y = (float) (mCenterY + mRadius * Math.sin(mAngle * i) * percent);
      if (i == 0) {
        path.moveTo(x, mCenterY);
      } else {
        path.lineTo(x, y);
      }
      //绘制小圆点
      canvas.drawCircle(x, y, 10, mValuePaint);
    }
    mValuePaint.setStyle(Paint.Style.STROKE);
    canvas.drawPath(path, mValuePaint);
    mValuePaint.setAlpha(127);
    //绘制填充区域
    mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    canvas.drawPath(path, mValuePaint);
  }
}
