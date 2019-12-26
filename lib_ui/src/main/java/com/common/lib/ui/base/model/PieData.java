package com.common.lib.ui.base.model;

import android.support.annotation.NonNull;

/**
 * 创建时间: 2019/12/26 18:30 <br>
 * 作者: qiudengjiao <br>
 * 描述: 自定义饼状图数据 Model
 */
public class PieData {

  // 用户关心数据
  private String name;        // 名字
  private float value;        // 数值
  private float percentage;   // 百分比

  // 非用户关心数据
  private int color = 0;      // 颜色
  private float angle = 0;    // 角度

  public PieData(@NonNull String name, @NonNull float value) {
    this.name = name;
    this.value = value;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public float getPercentage() {
    return percentage;
  }

  public void setPercentage(float percentage) {
    this.percentage = percentage;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public float getAngle() {
    return angle;
  }

  public void setAngle(float angle) {
    this.angle = angle;
  }

}
