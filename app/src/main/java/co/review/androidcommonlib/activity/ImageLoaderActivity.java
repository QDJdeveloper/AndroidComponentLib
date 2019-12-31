package co.review.androidcommonlib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import co.review.androidcommonlib.R;
import com.common.lib.ui.base.BaseActivity;
import com.common.lib.ui.base.imageloader.GlideImageView;
import com.common.lib.ui.base.imageloader.transformation.CircleTransformation;

/**
 * 创建时间: 2019/12/31 11:44 <br>
 * 作者: qiudengjiao <br>
 * 描述: 图片加载页面
 */
public class ImageLoaderActivity extends BaseActivity {

  String url2 = "http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_loader);

    initUI();
  }

  private void initUI() {
    GlideImageView mGlideImageView = findViewById(R.id.iv_glide_imageview1);
    GlideImageView mGlideImageView2 = findViewById(R.id.iv_glide_imageview2);
    GlideImageView mGlideImageView3 = findViewById(R.id.iv_glide_imageview3);
    RecyclerView mRecyclerView = findViewById(R.id.recyclerveiw_image_loader);

    mGlideImageView.load(url2, R.mipmap.image_loading);
    mGlideImageView2.load(url2, R.mipmap.image_loading, 10);
    mGlideImageView3.load(url2, R.mipmap.image_loading, new CircleTransformation());
  }
}
