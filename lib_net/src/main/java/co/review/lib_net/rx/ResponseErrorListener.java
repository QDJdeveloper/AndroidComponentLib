package co.review.lib_net.rx;

/**
 * 创建时间: 2019/11/19 14:48 <br>
 * 作者: qiudengjiao <br>
 * 描述: 响应异常监听器
 */
public interface ResponseErrorListener {
  boolean handleResponseError(String code, String msg);
}
