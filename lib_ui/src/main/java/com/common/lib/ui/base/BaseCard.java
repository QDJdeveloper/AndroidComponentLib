package com.common.lib.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建时间: 2019/12/25 10:31 <br>
 * 作者: qiudengjiao <br>
 * 描述: Card 基类
 */
public abstract class BaseCard {

  private Context mContext;
  private View mView;

  public BaseCard(Context context, @NonNull ViewGroup root) {
    this(context, root, false);
  }

  /**
   * 创建card
   *
   * @param root 需要添加的rootView,不可以为空
   * @param attachToRoot 是否添加到rootView
   */
  public BaseCard(Context context, @NonNull ViewGroup root, boolean attachToRoot) {
    mContext = context;
    createView(context, root, attachToRoot);
  }

  private void createView(Context context, ViewGroup root, boolean attachToRoot) {
    mView = LayoutInflater.from(context).inflate(onBindLayoutId(), root, attachToRoot);
    onViewCreated(mView);
  }

  protected abstract void onViewCreated(View view);

  protected abstract int onBindLayoutId();

  public View getView() {
    return mView;
  }

  protected Context getContext() {
    return mContext;
  }
}
