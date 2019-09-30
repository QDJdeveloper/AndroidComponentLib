package com.common.lib.utils.io;

import android.database.Cursor;
import android.support.annotation.Nullable;
import com.common.lib.utils.LogUtil;
import java.io.Closeable;
import java.io.IOException;
import java.util.zip.ZipFile;

/**
 * 创建时间: 2019/09/30 16:15 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class CloseableUtil {


  private static final String TAG = CloseableUtil.class.getSimpleName();

  /**
   * Don't let anyone instantiate this class.
   */
  private CloseableUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * 关闭 {@code closeable} 对象，如果关闭时抛出异常，则按 {@code logPolicy}指定的方案打Log
   *
   * @param closeable 要关闭的对象，实现了{@link Closeable}接口
   * @param logPolicy 出现异常时打Log的方案，可能的值参考 {@link LogUtil.LOG_POLICY}
   */
  public static void close(@Nullable Closeable closeable, @LogUtil.LOG_POLICY int logPolicy) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        LogUtil.doLog(e, logPolicy);
      }
    }
  }

  /**
   * 关闭 {@code closeable} 对象，不向上抛出异常
   *
   * @param closeable 要关闭的对象，实现了{@link Closeable}接口
   */
  public static void closeSilently(@Nullable Closeable closeable) {
    close(closeable, LogUtil.LOG_SILENT);
  }

  /**
   * 关闭 {@code closeable} 对象，向上抛出异常
   *
   * @param closeable 要关闭的对象，实现了{@link Closeable}接口
   */
  public static void close(@Nullable Closeable closeable) throws IOException {
    if (closeable != null) {
      closeable.close();
    }
  }

  /**
   * 关闭 {@code closeable} 对象，如果关闭时抛出异常，则按 {@code logPolicy}指定的方案打Log
   * <p>
   * {@link ZipFile} API 19 才实现 {@link Closeable} 接口，需要单独重载
   *
   * @param zipFile 要关闭的对象
   */
  public static void closeSilently(@Nullable ZipFile zipFile) {
    close(zipFile,LogUtil.LOG_SILENT);
  }

  /**
   * 关闭 {@code closeable} 对象，如果关闭时抛出异常，则按 {@code logPolicy}指定的方案打Log
   * <p>
   * {@link ZipFile} API 19 才实现 {@link Closeable} 接口，需要单独重载
   *
   * @param zipFile 要关闭的对象
   * @param logPolicy 出现异常时打Log的方案，可能的值参考 {@link LogUtil.LOG_POLICY}
   */
  public static void close(@Nullable ZipFile zipFile, @LogUtil.LOG_POLICY int logPolicy) {
    if (zipFile != null) {
      try {
        zipFile.close();
      } catch (IOException e) {
        LogUtil.doLog(e, logPolicy);
      }
    }
  }

  /**
   * 关闭 {@code closeable} 对象
   * <p>
   * {@link Cursor} API 16才实现{@link Closeable}接口，需要单独重载。{@link Cursor#close()}不抛出异常
   *
   * @param cursor 要关闭的对象
   */
  public static void close(@Nullable Cursor cursor) {
    if (cursor != null) {
      cursor.close();
    }
  }


}
