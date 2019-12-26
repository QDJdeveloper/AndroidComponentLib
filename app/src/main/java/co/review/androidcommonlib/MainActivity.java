package co.review.androidcommonlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import co.review.androidcommonlib.activity.CustomViewActivity;
import com.common.lib.ui.base.BaseActivity;
import com.common.lib.utils.LogUtil;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initUI();
    initClickListener();
  }

  private void initUI() {

  }

  private void initClickListener() {
    logClick();
    customViewClick();
  }

  private void logClick() {
    findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogUtil.e("MainActivity", "MainActivity");
      }
    });
  }

  private void customViewClick() {
    findViewById(R.id.btn_custom_view).setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        startCustomViewActivity();
      }
    });
  }

  private void startCustomViewActivity() {
    Intent intent = new Intent(this, CustomViewActivity.class);
    startActivity(intent);
  }
}
