package co.review.lib_net;

import co.review.lib_net.http.HttpClient;

/**
 * 创建时间: 2019/11/15 16:34 <br>
 * 作者: qiudengjiao <br>
 * 描述: 网络请求总入口
 */
public class HttpProvider {

  private static class Single {
    private static final HttpProvider INSTANCE = new HttpProvider();
  }

  public static HttpProvider getInstance() {
    return Single.INSTANCE;
  }

  /**
   * 网络客户端实例化
   *
   * @return
   */
  private HttpClient getClient() {
    return new HttpClient();
  }

}
