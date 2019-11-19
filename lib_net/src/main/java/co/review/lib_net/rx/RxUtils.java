package co.review.lib_net.rx;

import co.review.lib_net.base.BaseResponse;
import co.review.lib_net.ui.IView;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * rx相关工具类
 */

public class RxUtils {

  private RxUtils() {

  }

  /**
   * 数据流转换解析器
   */
  public static <T> ObservableTransformer<BaseResponse, BaseResponse<T>> parseJSON(
      final Class<T> clazz) {

    return new ObservableTransformer<BaseResponse, BaseResponse<T>>() {
      @Override
      public Observable<BaseResponse<T>> apply(Observable<BaseResponse> netResponseObservable) {
        return netResponseObservable.map(new HttpSuccessResponseFunc<T>(clazz))
            .onErrorResumeNext(new HttpFailResponseFunc());
      }
    };
  }

  /**
   * view生命周期绑定
   */
  public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
    if (view instanceof LifecycleProvider) {
      return ((LifecycleProvider) view).bindToLifecycle();
    } else {
      throw new IllegalArgumentException("view isn't activity or fragment");
    }
  }
}
