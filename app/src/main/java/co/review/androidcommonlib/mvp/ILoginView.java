package co.review.androidcommonlib.mvp;

/**
 * 创建时间: 2020/01/02 17:23 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public interface ILoginView {

  void showProgress();

  void hideProgress();

  void setUsernameError();

  void setPasswordError();

  void jumpToMainActivity();

}
