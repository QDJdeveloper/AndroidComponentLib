package co.review.lib_net.http;

import android.support.v4.util.ArrayMap;
import co.review.lib_net.interceptor.DownLoadInterceptor;
import co.review.lib_net.interceptor.HeaderInterceptor;
import co.review.lib_net.interceptor.LoggingInterceptor;
import co.review.lib_net.interceptor.UpLoadInterceptor;
import co.review.lib_net.updownload.DownProgressListener;
import co.review.lib_net.updownload.UpProgressListener;
import okhttp3.Interceptor;

/**
 * 创建时间: 2019/11/19 12:13 <br>
 * 作者: qiudengjiao <br>
 * 描述: 网络参数配置 链式调用
 */
public class HttpClient {

  protected ArrayMap<String, String> headers;
  protected String baseUrl;
  protected DownProgressListener downProgressListener;
  protected UpProgressListener upProgressListener;
  private int TIMEOUT = 60;
  /**
   * 网络 api 封装初始化
   */
  public HttpClient() {
    this.headers = new ArrayMap<>();
    init();
  }

  /**
   * 初始化 Retrofit
   */
  private void init() {
    RetrofitHelper.getInstance().init();
  }

  /**
   * 主机域名配置
   *
   * @param baseUrl
   * @return
   */
  public HttpClient baseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  /**
   * 自定义请求头配置
   *
   * @param name  自定义头名称
   * @param value 对应值
   * @return
   */
  public HttpClient addHeader(String name, String value) {
    headers.put(name, value);
    return this;
  }

  /**
   * socket连接、读写超时配置
   *
   * @param timeOut
   * @return
   */
  public HttpClient setTimeOut(int timeOut) {
    this.TIMEOUT = timeOut;
    return this;
  }

  /**
   * 进度监听上传
   *
   * @param upProgressListener
   * @return
   */
  public HttpClient setUpProgressListener(UpProgressListener upProgressListener) {
    this.upProgressListener = upProgressListener;
    return this;
  }

  /**
   * 进度监听上传
   *
   * @param downProgressListener
   * @return
   */
  public HttpClient setDownProgressListener(DownProgressListener downProgressListener) {
    this.downProgressListener = downProgressListener;
    return this;
  }

  /**
   * 生产apiService
   *
   * @return
   */
  public ApiService getApiService() {
    return RetrofitHelper.getInstance()
        .connectTimeout(TIMEOUT)
        .addInterceptor(new LoggingInterceptor())
        .addInterceptor(InterceptorHeaderFactory())
        .addInterceptor(InterceptorUpFactory())
        .addInterceptor(InterceptorDownFactory())
        .baseUrl(baseUrl)
        .build()
        .create(ApiService.class);
  }

  /**
   * 生成service
   *
   * @param service
   * @param <T>
   * @return
   */
  public <T> T getService(Class<T> service) {
    return RetrofitHelper.getInstance()
        .connectTimeout(TIMEOUT)
        .addInterceptor(new LoggingInterceptor())
        .addInterceptor(InterceptorHeaderFactory())
        .addInterceptor(InterceptorUpFactory())
        .addInterceptor(InterceptorDownFactory())
        .baseUrl(baseUrl)
        .build()
        .create(service);
  }

  /**
   * 添加请求头拦截器
   *
   * @return
   */
  protected Interceptor InterceptorHeaderFactory() {
    if (headers != null || headers.size() != 0) {
      return new HeaderInterceptor(headers);
    }
    return null;
  }

  /**
   * 未设置进度监听时就不再添加拦截器
   */
  private Interceptor InterceptorUpFactory() {
    if (upProgressListener != null) {
      return new UpLoadInterceptor(upProgressListener);
    }
    return null;
  }

  /**
   * 下载进度拦截器添加
   *
   * @return
   */
  private Interceptor InterceptorDownFactory() {
    if (downProgressListener != null) {
      return new DownLoadInterceptor(downProgressListener);
    }
    return null;
  }

}
