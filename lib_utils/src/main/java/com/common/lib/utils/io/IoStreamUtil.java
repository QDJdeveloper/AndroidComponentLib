package com.common.lib.utils.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 创建时间: 2019/09/30 16:15 <br>
 * 作者: qiudengjiao <br>
 * 描述:  对IO操作封装的简易方法
 */
public class IoStreamUtil {

  private static final String TAG = IoStreamUtil.class.getSimpleName();

  private IoStreamUtil() {
    throw new IllegalStateException("No instance");
  }

  /**
   * 从 stream 中读取文本,调用方负责关闭流
   *
   * @param is 输入流
   * @return return null if stream is null;
   */
  public static final String readStringFromStream(InputStream is) {

    if (is == null) {
      return null;
    }

    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    try {
      br = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\r\n");
      }
    } catch (Throwable ignore) {
      // ignore
    } finally {
      CloseableUtil.closeSilently(br);
    }

    return sb.toString();
  }

  /**
   * 输入流转化成字节数组,调用方负责关闭流
   *
   * @param is 输入流
   * @return return null if stream is null;
   */
  public static byte[] streamToBytes(InputStream is) {

    if (is == null) {
      return null;
    }

    byte[] b = null;
    ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
    byte[] buffer = new byte[1024];
    int len;
    try {
      while ((len = is.read(buffer)) >= 0) {
        os.write(buffer, 0, len);
      }
      b = os.toByteArray();
      os.flush();
    } catch (IOException ignore) {
      return null;
    } finally {
      CloseableUtil.closeSilently(os);
    }
    return b;
  }

}
