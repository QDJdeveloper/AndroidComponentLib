package co.review.androidcommonlib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import co.review.androidcommonlib.R;
import com.common.lib.ui.base.BaseActivity;

/**
 * 创建时间: 2019/12/26 20:11 <br>
 * 作者: qiudengjiao <br>
 * 描述: 自定义 View
 */
public class CustomViewActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coustom_view);
    initUI();
    initClickListener();
  }

  private void initUI() {

  }

  private void initClickListener() {
    pieViewClick();
    radarViewClick();
  }

  private void pieViewClick() {
    findViewById(R.id.btn_pie_view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startPieViewActivity();
      }
    });
  }

  private void radarViewClick() {
    findViewById(R.id.btn_radar_view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startRadarActivity();
      }
    });
  }

  private void startPieViewActivity() {
    Intent intent = new Intent(this, PieViewActivity.class);
    startActivity(intent);
  }

  private void startRadarActivity() {
    Intent intent = new Intent(this,RadarViewActivity.class);
    startActivity(intent);
  }
}
