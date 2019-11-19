package co.review.lib_net.interceptor;

import co.review.lib_net.updownload.ProgressRequestBody;
import co.review.lib_net.updownload.UpProgressListener;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建时间: 2019/11/19 16:11 <br>
 * 作者: qiudengjiao <br>
 * 描述: 文件上传进度拦截器
 */
public class UpLoadInterceptor implements Interceptor {

  private UpProgressListener upProgressListener;

  public UpLoadInterceptor(UpProgressListener upProgressListener) {
    this.upProgressListener = upProgressListener;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();

    Request request = original.newBuilder()
        .method(original.method(), new ProgressRequestBody(original.body(), upProgressListener))
        .build();
    return chain.proceed(request);
  }


}
