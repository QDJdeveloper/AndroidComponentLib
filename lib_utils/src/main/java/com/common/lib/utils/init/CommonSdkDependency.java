package com.common.lib.utils.init;

/**
 * 创建时间: 2019/09/30 16:34 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public interface CommonSdkDependency {
  /**
   * Debug 版 SDK会做错误检查，发现错误抛运行时异常，以便尽早发现问题
   */
  boolean isDebug();
}
