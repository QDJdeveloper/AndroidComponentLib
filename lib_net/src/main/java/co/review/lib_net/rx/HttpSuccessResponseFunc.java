package co.review.lib_net.rx;

import android.text.TextUtils;
import co.review.lib_net.base.BaseResponse;
import co.review.lib_net.http.NetConst;
import com.alibaba.fastjson.JSON;
import io.reactivex.functions.Function;

/**
 * 创建时间: 2019/11/19 17:56 <br>
 * 作者: qiudengjiao <br>
 * 描述: 接口响应解析
 */
public class HttpSuccessResponseFunc<T> implements Function<BaseResponse, BaseResponse<T>> {

  private Class<T> clazz;

  public HttpSuccessResponseFunc(Class clazz) {
    this.clazz = clazz;
  }

  @Override
  public BaseResponse<T> apply(BaseResponse baseResponse) throws Exception {
    if (TextUtils.equals(baseResponse.getCode(), NetConst.SUCCESS)) {
      baseResponse.setResult(JSON.parseObject(baseResponse.getData(), clazz));
      return baseResponse;
    } else {
      throw new ExceptionHandle.ServerException(baseResponse.getCode(), baseResponse.getMsg());
    }
  }
}
