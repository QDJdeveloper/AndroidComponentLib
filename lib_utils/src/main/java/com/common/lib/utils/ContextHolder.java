package com.common.lib.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 创建时间: 2019/09/30 16:35 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class ContextHolder {
  private static Context sAppContext;

  public static void setAppContext(@NonNull Context context) {
    sAppContext = context;
  }

  /**
   * @return 获取Application Context，返回的Context不可用于UI相关的操作（如获取资源，主题，属性等）
   */
  @NonNull public static Context appContext() {
    if (BuildConfig.DEBUG) {
      if (sAppContext == null) {
        throw new NullPointerException("you must call CommonSdk.init() first");
      }
    }
    return sAppContext;
  }
}
