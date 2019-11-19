package co.review.lib_net.rx;

import co.review.lib_net.base.BaseResponse;
import io.reactivex.functions.Function;
import rx.Observable;

/**
 * 创建时间: 2019/11/19 17:54 <br>
 * 作者: qiudengjiao <br>
 * 描述: 错误拦截包括网络错误 code非正常等
 */
public class HttpFailResponseFunc <T> implements Function<Throwable, Observable<BaseResponse<T>>> {

  @Override
  public Observable<BaseResponse<T>> apply(Throwable throwable) throws Exception {
    return Observable.error(ExceptionHandle.handleException(throwable));
  }
}
