package co.review.androidcommonlib.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import co.review.androidcommonlib.MainActivity;
import co.review.androidcommonlib.R;
import com.common.lib.ui.base.BaseActivity;

/**
 * 创建时间: 2020/01/02 16:33 <br>
 * 作者: qiudengjiao <br>
 * 描述: 登录 activity
 */
public class LoginActivity extends BaseActivity implements ILoginView {

  private LoginPresenter mLonginPresenter;
  private Button mLoginBtn;
  private EditText mUserName;
  private EditText mUserPassword;
  private ProgressBar mProgressBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mLonginPresenter = new LoginPresenter(this, new LoginInteractor());

    initUI();
    initClick();
  }

  /**
   * 初始化 UI
   */
  private void initUI() {
    mLoginBtn = findViewById(R.id.btn_login);
    mUserName = findViewById(R.id.et_user_name);
    mUserPassword = findViewById(R.id.et_user_password);
    mProgressBar = findViewById(R.id.progress);
  }

  private void initClick() {
    mLoginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loginClick();
      }
    });
  }

  /**
   * 登录点击
   */
  private void loginClick() {
    mLonginPresenter.login(mUserName.getText().toString(), mUserPassword.getText().toString());
  }

  @Override
  public void showProgress() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideProgress() {
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void setUsernameError() {
    Toast.makeText(this, getResources().getString(R.string.username_error), Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void setPasswordError() {
    Toast.makeText(this, getResources().getString(R.string.password_error), Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void jumpToMainActivity() {
    startActivity(new Intent(this, MainActivity.class));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLonginPresenter.onDestroy();
  }
}
