package com.common.lib.utils;

/**
 * 创建时间: 2019/09/30 16:31 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */

import android.content.Context;
import android.support.annotation.NonNull;
import com.common.lib.utils.init.CommonSdkDependency;
import com.common.lib.utils.java.Objects;

public class CommonSdk {

  private static CommonSdkDependency sSdkDependency;

  /**
   * 初始化SDK，使用前CommonSdk前必须先初始化。使用CommonSDK的SDK不必做初始化，最终的SDK做初始化就可以了
   */
  public static void init(@NonNull Context context, @NonNull CommonSdkDependency dependency) {
    Objects.requireNonNull(dependency, "dependency must not be null");

    Objects.requireNonNull(context, "context must not be null");

    sSdkDependency = dependency;

    ContextHolder.setAppContext(context);
  }

  /**
   * 使用前必须先调 init 做初始化
   */
  @NonNull public static CommonSdkDependency getDependency() {
    Objects.requireNonNull(sSdkDependency,
        "dependency must not be null, please use CommonSdk.init() firstly");
    return sSdkDependency;
  }
}
