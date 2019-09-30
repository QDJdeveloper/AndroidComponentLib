package com.common.lib.utils;

import android.support.annotation.IntDef;
import android.util.Log;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建时间: 2019/09/30 14:31 <br>
 * 作者: qiudengjiao <br>
 * 描述: LogUtil
 */
public class LogUtil {


  final static String TAG = LogUtil.class.getSimpleName();

  /**
   * 发生异常时，忽略异常，不打Log
   */
  public static final int LOG_SILENT = 1;
  /**
   * 发生异常时，只打印一行{@link Throwable#getMessage()}
   */
  public static final int LOG_ONE_LINE = 2;
  /**
   * 发生异常时，打印完整的调用堆栈
   */
  public static final int LOG_STACK_TRACE = 3;

  /**
   * Don't let anyone instantiate this class.
   */
  private LogUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  public static boolean isDebug() {
    try {
      return CommonSdk.getDependency().isDebug();
    } catch (Throwable e){
      // 默认不打印log
      return false;
    }
  }

  /**
   * 不写文件的debug日志
   */
  private static void d(File logFile) {
    if (isDebug()) {
      Log.d(TAG, TAG + " : Log to file : " + logFile);
    }
  }

  public static void d(String tag, String msg) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.d(TAG, tag + " : " + msg);
    }
  }

  public static void d(String tag, String msg, Throwable error) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.d(TAG, tag + " : " + msg, error);
    }
  }

  /**
   * 不写文件的verbose日志
   */
  private static void v(File backfile) {
    if (isDebug()) {
      Log.v(TAG, TAG + " : Create back log file : " + backfile.getName());
    }
  }

  public static void v(String tag, String msg) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.v(TAG, tag + " : " + msg);
    }
  }

  public static void v(String tag, String msg, Throwable error) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.v(TAG, tag + " : " + msg, error);
    }
  }

  public static void i(String tag, String msg) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.i(TAG, tag + " : " + msg);
    }
  }

  public static void i(String tag, String msg, Throwable error) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.i(TAG, tag + " : " + msg, error);
    }
  }

  /**
   * 不写文件的w
   */
  private static void w() {
    if (isDebug()) {
      Log.w(TAG, "Unable to create external cache directory");
    }
  }

  public static void w(String tag, String msg) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.w(TAG, tag + " : " + msg);
    }
  }

  public static void w(String tag, String msg, Throwable error) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.w(TAG, tag + " : " + msg, error);
    }
  }

  /**
   * 不写文件的错误日志
   */
  private static void e(String msg, Exception e) {
    if (isDebug()) {
      Log.e(TAG, msg, e);
    }
  }

  public static void e(String tag, String msg) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.e(TAG, tag + " : " + msg);
    }
  }

  public static void e(String tag, String msg, Throwable error) {
    if (isDebug()) {
      tag = Thread.currentThread().getName() + ":" + tag;
      Log.e(TAG, tag + " : " + msg, error);
    }
  }

  public static void doLog(Throwable throwable, @LOG_POLICY int logPolicy) {
    switch (logPolicy) {
      case LOG_SILENT:
        break;

      case LOG_ONE_LINE:
        w(TAG, throwable.getMessage());
        break;

      case LOG_STACK_TRACE:
        throwable.printStackTrace();
        break;

      default:
        break;
    }
  }

  @IntDef({ LOG_SILENT, LOG_ONE_LINE, LOG_STACK_TRACE }) @Retention(RetentionPolicy.SOURCE)
  public @interface LOG_POLICY {
  }

}
