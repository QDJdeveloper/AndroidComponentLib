package co.review.androidcommonlib;

import android.app.Application;
import android.content.Context;
import com.common.lib.utils.CommonSdk;
import com.common.lib.utils.init.CommonSdkDependency;

/**
 * 创建时间: 2019/09/30 16:42 <br>
 * 作者: qiudengjiao <br>
 * 描述: 自定义Application
 */
public class BaseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);

    CommonSdk.init(base, new CommonSdkDependency() {
      @Override
      public boolean isDebug() {
        return BuildConfig.DEBUG;
      }
    });
  }
}
