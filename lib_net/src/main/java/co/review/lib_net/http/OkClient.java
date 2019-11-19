package co.review.lib_net.http;

import co.review.lib_net.utils.HttpsUtils;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 创建时间: 2019/11/19 15:35 <br>
 * 作者: qiudengjiao <br>
 * 描述: okhttp 常用 api 封装以便统一底层网络 单例模式
 */
public class OkClient {
  private int DEFAULT_TIMEOUT = 60;
  private OkHttpClient okHttpClient;
  private OkHttpClient.Builder okHttpClientBuilder;

  private static class SingleOkClient {
    private static final OkClient INSTANCE = new OkClient();
  }

  public static OkClient getInstance() {
    return SingleOkClient.INSTANCE;
  }

  private OkClient() {
    buildOkHttp();
  }

  /**
   * okhttp 配置初始化
   *
   * @return
   */
  public OkClient init() {
    buildNewOkHttpBuilder();
    return this;
  }

  private void buildOkHttp() {
    okHttpClient = new OkHttpClient();
  }

  /**
   * okhttp 配置属性初始化
   */
  private void buildNewOkHttpBuilder() {
    okHttpClientBuilder = okHttpClient.newBuilder();
  }

  public OkHttpClient getOkHttpClient() {
    return okHttpClient;
  }

  public OkHttpClient.Builder getOkHttpClientBuilder() {
    return okHttpClientBuilder;
  }

  /**
   * 连接、读写超时设置
   *
   * @param timeout
   */
  public void connectTimeout(int timeout) {
    this.DEFAULT_TIMEOUT = timeout;
    okHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    okHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    okHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
  }

  /**
   * 添加拦截器
   *
   * @param interceptor
   */
  public void addInterceptor(Interceptor interceptor) {
    if (interceptor != null) {
      okHttpClientBuilder.addInterceptor(interceptor);
    }
  }

  /**
   * https自定义证书时 调用
   */
  public void allowDiyCert() {
    HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
    okHttpClientBuilder.hostnameVerifier(HttpsUtils.getHostNameVerifier())
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
  }
}
