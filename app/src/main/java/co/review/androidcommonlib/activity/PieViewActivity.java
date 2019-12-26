package co.review.androidcommonlib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import co.review.androidcommonlib.R;
import com.common.lib.ui.base.BaseActivity;
import com.common.lib.ui.base.model.PieData;
import com.common.lib.ui.base.view.PieView;
import java.util.ArrayList;

/**
 * 创建时间: 2019/12/26 20:27 <br>
 * 作者: qiudengjiao <br>
 * 描述: 自定义 饼状图
 */
public class PieViewActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_pie_view);

    initUI();
  }

  private void initUI() {
    PieView pieView = findViewById(R.id.pie_view);
    pieView.setData(getPieViewData());
  }

  private ArrayList<PieData> getPieViewData() {
    ArrayList<PieData> datas = new ArrayList<>();
    PieData pieData = new PieData("sloop", 60);
    PieData pieData2 = new PieData("sloop", 30);
    PieData pieData3 = new PieData("sloop", 40);
    PieData pieData4 = new PieData("sloop", 20);
    PieData pieData5 = new PieData("sloop", 20);
    datas.add(pieData);
    datas.add(pieData2);
    datas.add(pieData3);
    datas.add(pieData4);
    datas.add(pieData5);
    return datas;
  }
}
