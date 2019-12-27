package co.review.androidcommonlib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import co.review.androidcommonlib.R;
import com.common.lib.ui.base.BaseActivity;
import com.common.lib.ui.base.view.RadarView;

/**
 * 创建时间: 2019/12/27 14:25 <br>
 * 作者: qiudengjiao <br>
 * 描述: 自定义雷达图 Activity
 */
public class RadarViewActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_radar_view);
    initUI();
  }

  private void initUI() {

    initRadarView();
  }

  private void initRadarView() {
    RadarView radarView = findViewById(R.id.radar_view);
    radarView.setValuePaintColor(R.color.colorAccent);
  }
}
