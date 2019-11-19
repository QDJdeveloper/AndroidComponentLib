package co.review.lib_net.http;

import co.review.lib_net.converter.FastJsonConverterFactory;
import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建时间: 2019/11/19 12:19 <br>
 * 作者: qiudengjiao <br>
 * 描述: retrofit 结合 ohkhttp 封装 配置并链式调用
 */
public class RetrofitHelper {

  private Retrofit retrofit;
  private Retrofit.Builder retrofitBuilder;
  private String baseUrl;

  private static class SingleHolder {
    private static final RetrofitHelper INSTANCE = new RetrofitHelper();
  }

  public static RetrofitHelper getInstance() {
    return SingleHolder.INSTANCE;
  }

  public RetrofitHelper() {
    buildRetrofitBuilder();
  }

  /**
   * 初始化okhttp
   *
   * @return
   */
  public RetrofitHelper init() {
    OkClient.getInstance().init();
    return this;
  }

  /**
   * 封装okclient的超时使retrofit链式调用
   *
   * @param timeout
   * @return
   */
  public RetrofitHelper connectTimeout(int timeout) {
    OkClient.getInstance().connectTimeout(timeout);
    return this;
  }

  /**
   * 封装okclient的拦截器使retrofit链式调用
   *
   * @param interceptor
   * @return
   */
  public RetrofitHelper addInterceptor(Interceptor interceptor) {
    if (interceptor != null) {
      OkClient.getInstance().addInterceptor(interceptor);
    }
    return this;
  }

  /**
   * 初始化retrofit 配置
   *
   * @return
   */
  public RetrofitHelper buildRetrofitBuilder() {
    retrofitBuilder = new Retrofit.Builder()
        .addConverterFactory(FastJsonConverterFactory.create())
        //.addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    return this;
  }

  /**
   * 主机域名配置
   * 注意！！！！  必须以“/” 结尾
   *
   * @param url
   * @return
   */
  public RetrofitHelper baseUrl(String url) {
    this.baseUrl = url;
    return this;
  }

  public RetrofitHelper build() {
    retrofit = retrofitBuilder
        .baseUrl(baseUrl)
        .client(OkClient.getInstance().getOkHttpClientBuilder().build())
        .build();
    return this;
  }

  /**
   * create you ApiService
   * Create an implementation of the API endpoints defined by the {@code service} interface.
   */
  public <T> T create(final Class<T> service) {
    if (service == null) {
      throw new RuntimeException("Api service is null!");
    }
    return retrofit.create(service);
  }

}
