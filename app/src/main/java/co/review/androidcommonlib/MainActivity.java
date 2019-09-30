package co.review.androidcommonlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.common.lib.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    initUI();
  }

  private void initUI() {
    findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogUtil.e("MainActivity","MainActivity");
      }
    });
  }
}
