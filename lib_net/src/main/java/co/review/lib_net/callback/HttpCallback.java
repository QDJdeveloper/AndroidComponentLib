package co.review.lib_net.callback;

import io.reactivex.disposables.Disposable;

/**
 * 创建时间: 2019/11/19 11:51 <br>
 * 作者: qiudengjiao <br>
 * 描述: rx对应的方法回调
 */
public interface HttpCallback<T> {
  /**
   * 订阅时使用
   *
   * @param d
   */
  void onSubscriber(Disposable d);

  /**
   * rx出现错误时
   *
   * @param e
   */
  void onError(Throwable e);

  /**
   * 成功时回调
   * @param t
   */
  void onNext(T t);

  /**
   * 完成时回掉
   */
  void onCompleted();
}
