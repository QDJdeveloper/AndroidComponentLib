package co.review.lib_net.interceptor;

import co.review.lib_net.updownload.DownProgressListener;
import co.review.lib_net.updownload.ProgressResponseBody;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建时间: 2019/11/19 16:03 <br>
 * 作者: qiudengjiao <br>
 * 描述: 文件下载进度拦截器
 */
public class DownLoadInterceptor implements Interceptor{
  private DownProgressListener downProgressListener;

  public DownLoadInterceptor(DownProgressListener downProgressListener) {
    this.downProgressListener = downProgressListener;
  }

  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    Request.Builder builder = chain.request().newBuilder();

    Response originalResponse = chain.proceed(builder.build());
    return originalResponse
        .newBuilder()
        .body(new ProgressResponseBody(originalResponse.body(),downProgressListener))
        .build();
  }

}
