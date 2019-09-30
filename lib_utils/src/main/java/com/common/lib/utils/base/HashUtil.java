package com.common.lib.utils.base;

import com.common.lib.utils.BuildConfig;
import com.common.lib.utils.LogUtil;
import com.common.lib.utils.StringUtil;
import com.common.lib.utils.io.CloseableUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 创建时间: 2019/09/30 17:29 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class HashUtil {


  public static final String HASH_MD5 = "MD5";
  public static final String HASH_SHA1 = "SHA1";
  private static final String TAG = HashUtil.class.getSimpleName();

  /**
   * Don't let anyone instantiate this class.
   */
  private HashUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * 生成字节数组对应的hash值
   *
   * @param hashName MD5 或 SHA1
   * @param buf 要hash的数组
   * @return 成功返hash值，失败返空
   */
  public static byte[] getByteHash(String hashName, byte[] buf) {
    MessageDigest m;
    try {
      m = MessageDigest.getInstance(hashName);
      m.update(buf);
      return m.digest();
    } catch (Exception e) {
      if (BuildConfig.DEBUG) {
        LogUtil.e(TAG, "", e);
      }
    }

    return null;
  }

  //public static byte[] getFileHash(String hashName, File file) {
  //  try {
  //    FileInputStream fis = new FileInputStream(file);
  //    return getInputStreamHash(hashName, fis);
  //  } catch (Exception e) {
  //    if (Env.DEBUG) {
  //      Log.e(TAG, "", e);
  //    }
  //  }
  //  return null;
  //}

  /**
   * 生成文件对应的hash值
   *
   * @param hashName MD5 或 SHA1
   * @param fileName 要hash的文件
   * @return 成功返hash值，失败返空
   */
  public static byte[] getFileHash(String hashName, String fileName) {
    File file = new File(fileName);
    FileInputStream in = null;
    try {
      in = new FileInputStream(fileName);
      MappedByteBuffer byteBuffer =
          in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
      MessageDigest m = MessageDigest.getInstance(hashName);
      m.update(byteBuffer);
      return m.digest();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      CloseableUtil.closeSilently(in);
    }
    return null;
  }

  /**
   * 生成文件对应的md5值
   *
   * @param fileName 要hash的文件
   * @return 成功返hash值，失败返空
   */
  public static byte[] getFileMd5(String fileName) {
    return getFileHash(HASH_MD5, fileName);
  }

  /**
   * 计算 InputStream 的 Hash，并完成之后关闭 InputStream
   *
   * @param hashName MD5 或 SHA1
   * @param is 要hash的流
   * @return 成功返hash值，失败返空
   */
  public static byte[] getInputStreamHash(String hashName, InputStream is) {
    byte[] buffer = new byte[1024];
    MessageDigest m;
    try {
      m = MessageDigest.getInstance(hashName);

      int numRead;

      do {
        numRead = is.read(buffer);
        if (numRead > 0) {
          m.update(buffer, 0, numRead);
        }
      } while (numRead != -1);

      return m.digest();
    } catch (Exception e) {
      if (BuildConfig.DEBUG) {
        LogUtil.e(TAG, "", e);
      }
    } finally {
      CloseableUtil.closeSilently(is);
    }

    return null;
  }

  private static String getStringHash(String hashName, String toCrypt) {
    try {
      MessageDigest m = MessageDigest.getInstance(hashName);
      m.update(toCrypt.getBytes());
      byte[] arrayOfByte = m.digest();
      return StringUtil.toHexString(arrayOfByte);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 计算字符串的MD5
   * 结果为32位小写
   * @param toCrypt 要计算的字符串
   */
  public static String md5(String toCrypt) {
    return getStringHash(HASH_MD5, toCrypt);
  }

  /**
   * 计算文件的MD5值(文件的MD5值指的是文件内容的md5值)是否和参数md5一致
   */
  public static boolean isMD5Match(String fileName, String md5) {
    try {
      byte[] md5Bytes = getFileHash(HASH_MD5, fileName);
      if (md5Bytes == null) return false;

      String fileMD5 = StringUtil.toHexString(md5Bytes);
      //String fileMD5 = getMd5ByFile(fileName);
      LogUtil.d(TAG, "isMD5Match:" + fileMD5 + " compareTo:" + md5);
      if (fileMD5.compareToIgnoreCase(md5) == 0) {
        return true;
      }
    } catch (Exception ignore) {
      //ignore
    }
    return false;
  }

  /**
   * 计算字符串的SHA1
   * 40位 小写
   * @param toCrypt 要计算的字符串
   */
  public static String sha1(String toCrypt) {
    return getStringHash(HASH_SHA1, toCrypt);
  }

}
