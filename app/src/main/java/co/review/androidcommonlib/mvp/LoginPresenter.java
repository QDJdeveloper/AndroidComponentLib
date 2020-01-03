package co.review.androidcommonlib.mvp;

/**
 * 创建时间: 2020/01/02 16:51 <br>
 * 作者: qiudengjiao <br>
 * 描述: 登录 Presenter
 */
public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {

  private ILoginView loginView;
  private LoginInteractor loginInteractor;

  LoginPresenter(ILoginView loginView, LoginInteractor loginInteractor) {
    this.loginView = loginView;
    this.loginInteractor = loginInteractor;
  }

  public void login(String username, String password) {
    if (loginView != null) {
      loginView.showProgress();
    }
    loginInteractor.login(username, password, this);
  }

  @Override
  public void onUsernameError() {
    if (loginView != null) {
      loginView.setUsernameError();
      loginView.hideProgress();
    }
  }

  @Override
  public void onPasswordError() {
    if (loginView != null) {
      loginView.setPasswordError();
      loginView.hideProgress();
    }
  }

  @Override
  public void onSuccess() {
    if (loginView != null) {
      loginView.jumpToMainActivity();
      loginView.hideProgress();
    }
  }

  public void onDestroy() {
    loginView = null;
  }
}
