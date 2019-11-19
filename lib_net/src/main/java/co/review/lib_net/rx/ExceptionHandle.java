package co.review.lib_net.rx;

import android.net.ParseException;
import co.review.lib_net.http.NetConst;
import co.review.lib_net.utils.LogUtils;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import retrofit2.adapter.rxjava.HttpException;

/**
 * 创建时间: 2019/11/19 14:49 <br>
 * 作者: qiudengjiao <br>
 * 描述: 网络异常处理
 */
public class ExceptionHandle {

  private static final int UNAUTHORIZED = 401;
  private static final int FORBIDDEN = 403;
  private static final int NOT_FOUND = 404;
  private static final int REQUEST_TIMEOUT = 408;
  private static final int INTERNAL_SERVER_ERROR = 500;
  private static final int BAD_GATEWAY = 502;
  private static final int SERVICE_UNAVAILABLE = 503;
  private static final int GATEWAY_TIMEOUT = 504;

  public static ResponseThrowable handleException(Throwable e) {

    ResponseThrowable ex;
    if (e instanceof HttpException) {
      HttpException httpException = (HttpException) e;
      ex = new ResponseThrowable(httpException.code() + "", NetConst.TIP);
      switch (httpException.code()) {
        case UNAUTHORIZED:
        case FORBIDDEN:
        case NOT_FOUND:
        case REQUEST_TIMEOUT:
        case GATEWAY_TIMEOUT:
        case INTERNAL_SERVER_ERROR:
        case BAD_GATEWAY:
        case SERVICE_UNAVAILABLE:
        default:
          ex.codeMessage = NetConst.TIP;
          break;
      }
      //Timber.e("网络异常信息（httpException）： %s", httpException.code());
      return ex;
    } else if (e instanceof ServerException) {//服务端异常
      ServerException resultException = (ServerException) e;
      ex = new ResponseThrowable(resultException.code, resultException.codeMessage);
      //ex.codeMessage=resultException.codeMessage;
      //Timber.e("接口异常信息（ServerException）： %s", resultException.code);
      return ex;
    } else if (e instanceof JSONException
        || e instanceof ParseException) {
      ex = new ResponseThrowable(ERROR.PARSE_ERROR, NetConst.TIP);
      //Timber.e("解析异常信息： %s", e.getMessage());
      return ex;
    } else if (e instanceof ConnectException) {
      ex = new ResponseThrowable(ERROR.NETWORD_ERROR, NetConst.TIP);
      //Timber.e("连接异常信息（ConnectException）： %s", e.getMessage());
      return ex;
    } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
      ex = new ResponseThrowable(ERROR.SSL_ERROR, NetConst.TIP);
      //Timber.e("ssl异常信息： %s", e.getMessage());
      return ex;
    } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
      ex = new ResponseThrowable(ERROR.TIMEOUT_ERROR, NetConst.TIP);
      //Timber.e("connect或者socket异常信息： %s", e.getMessage());
      return ex;
    } else if (e instanceof ClassCastException) {
      ex = new ResponseThrowable(ERROR.CLASS_ERROR, NetConst.TIP);
      //Timber.e("类型转换异常（ClassCastException）： %s", e.getMessage());
      return ex;
    } else if (e instanceof UnknownHostException) {
      ex = new ResponseThrowable(ERROR.NO_ADDRESS_ERROR, NetConst.TIP);
      //Timber.e("未知主机异常（UnknownHostException）： %s", e.getMessage());
      return ex;
    } else {//未知错误 rx等
      ex = new ResponseThrowable(ERROR.UNKNOWN, NetConst.TIP);
      //Timber.e("未知异常： %s", e.getMessage());
      return ex;
    }
  }


  /**
   * 约定异常
   */
  public class ERROR {
    /**
     * 自定义错误码 -1 对应于系统级异常（未返回结果集）与正常返回结果集status字段对应
     */
    public static final String status = "-1";
    /**
     * 未知错误
     */
    public static final String UNKNOWN = 1000 + "";
    /**
     * 解析错误
     */
    public static final String PARSE_ERROR = 1001 + "";
    /**
     * 网络错误
     */
    public static final String NETWORD_ERROR = 1002 + "";
    /**
     * 协议出错
     */
    public static final String HTTP_ERROR = 1003 + "";

    /**
     * 证书出错
     */
    public static final String SSL_ERROR = 1005 + "";

    /**
     * 连接超时
     */
    public static final String TIMEOUT_ERROR = 1006 + "";
    /**
     * 类型转换错误
     */
    public static final String CLASS_ERROR = 1007 + "";
    /**
     * 连接不上主机
     */
    public static final String NO_ADDRESS_ERROR = 1008 + "";
  }

  public static class ResponseThrowable extends RuntimeException {
    public String code;
    public String codeMessage;

    public ResponseThrowable(String code, String codeMessage) {
      this.code = code;
      this.codeMessage = codeMessage;
    }

    @Override
    public String toString() {
      return "ResponeThrowable{" +
          "code='" + code + '\'' +
          ", codeMessage='" + codeMessage + '\'' +
          '}';
    }
  }

  /**
   * 自定义接口级异常
   */
  public static class ServerException extends RuntimeException {
    public String code;
    public String codeMessage;

    public ServerException(String code, String codeMessage) {

      this.code = code;
      this.codeMessage = codeMessage;
    }
  }
}
