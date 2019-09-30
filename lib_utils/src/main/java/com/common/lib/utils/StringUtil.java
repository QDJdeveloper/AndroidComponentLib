package com.common.lib.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 创建时间: 2019/09/30 14:56 <br>
 * 作者: qiudengjiao <br>
 * 描述: StringUtil
 */
public class StringUtil {

  //服务端都是采用小写，统一一下都用小写
  private static final char[] HEX_DIGITS = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
  };

  /**
   * Don't let anyone instantiate this class.
   */
  private StringUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * 获得byte数组对应的16进制字符串
   *
   * @param bytes 要转换的字符串
   * @return 正常返还16进制字符串，异常返还空字符串。
   */
  public static final String toHexString(@Nullable byte[] bytes) {
    if (bytes == null) return "";

    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      sb.append(HEX_DIGITS[(b & 0xf0) >> 4]);
      sb.append(HEX_DIGITS[b & 0x0f]);
    }
    return sb.toString();
  }

  /**
   * 判断字符串是否为空
   * 建议直接使用TextUtils.isEmpty
   *
   * @param text 文字
   */
  @Deprecated public static boolean isEmpty(String text) {
    return TextUtils.isEmpty(text);
  }

  /**
   * 判断字符串是否为非空
   * 建议直接使用TextUtils.isEmpty
   */
  @Deprecated public static boolean isNotEmpty(String text) {
    return !isEmpty(text);
  }

  @NonNull
  public static String trim(String str) {
    return str != null ? str.trim() : "";
  }

  /**
   * 使用US Locale 调用 {@link String#format(Locale, String, Object...)}格式字符串
   */
  public static String format(@NonNull String format, Object... args) {
    return String.format(Locale.US, format, args);
  }

  /**
   * 字符串集合转字符串，以','分割
   *
   * @param data 集合
   */
  public static String stringListToString(List<String> data) {
    if (data == null) {
      return null;
    }
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < data.size(); i++) {
      sb.append(data.get(i));
      if (i != data.size() - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }

  /**
   * 去掉小数点后面的数字
   *
   * @param number 原始字符串
   */
  public static String trimPointer(String number) {
    if (null == number || 0 >= number.length() || ".".equals(number)) {
      return "";
    }

    return (trim(number).split("\\."))[0];
  }

  /**
   * 替换字符串并trim
   *
   * @param original 原始字符串
   * @param target 被替换的字符串
   * @param replacement 替换的字符串
   */
  public static String replaceAndTrim(String original, String target, String replacement) {
    if (original == null) return "";

    if (target == null || replacement == null) return original;

    return StringUtil.trim(original.replace(target, replacement));
  }

  /**
   * 字符串长度大于0 小于maxValidLength字节（一个字符按2个字节）,如果字符串为空,直接返回false,不管maxValidLength值是多少
   *
   * @param validateStr 要校验的字符串
   * @param maxValidLength 最大的字节数
   */
  public static boolean isValidLength(String validateStr, int maxValidLength) {
    if (TextUtils.isEmpty(validateStr)) {
      return false;
    }
    return validateStr.length() * 2 <= maxValidLength;
  }

  /**
   * 是否为null或空白字符串
   */
  public static boolean isBlanks(String string) {
    return string == null || string.trim().length() == 0;
  }

  /**
   * 字符串是否可以转换成数值,非10进制的数,不能带0x之类的标识符
   */
  public static boolean isNumValid(String num) {
    if (num == null) {
      return false;
    }
    try {
      if (Double.valueOf(num) <= 0) {
        return false;
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 字符串是否是数字组成
   *
   * @param str 字符串
   */
  public static boolean isNumericOnly(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    return pattern.matcher(str).matches();
  }

  /**
   * 是否是0~9的字符
   */
  public static boolean isNumeric(char ch) {
    if (ch >= '0' && ch <= '9') {
      return true;
    }
    return false;
  }

  /**
   * 是否是a~zA~Z的字符
   */
  public static boolean isLetter(char ch) {
    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
      return true;
    }
    return false;
  }

  /**
   * 是否是中文字符
   */
  public static boolean isChinese(char ch) {
    if (ch >= 0x4e00 && ch <= 0x9fa5) {
      return true;
    }
    return false;
  }

  /**
   * 获取替换Html格式的字符串
   *
   * @param data 字符串原型
   * @param args 要替换的参数
   * @return 替换后的字符串
   */
  public static CharSequence getFromHtmlString(String data, Object[] args) {
    String s = String.format(data, args);
    Spanned span = Html.fromHtml(s);
    return span;
  }

  /**
   * 替换字符串某一段的颜色
   *
   * @param data 数据源
   * @param color 要替换成的颜色
   * @param index 开始下标
   * @param length 要替换的长度
   * @return 替换后的字符串
   */
  public static CharSequence getColorString(String data, int color, int index, int length) {
    //new SpannableString() 会调用data.length(),需要判断空
    if (data == null) {
      return " ";
    }

    SpannableString ss = new SpannableString(data);

    try {
      //setSpan应该设置index和end,但是传进来的是index和length,end = index + length
      ss.setSpan(new ForegroundColorSpan(color), index, index + length,
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    } catch (IndexOutOfBoundsException exception) {
      //如果data.length 不在 start, end范围内,就抛出异常进行处理
      LogUtil.e(StringUtil.class.getSimpleName(), " ", exception);
      return ss;
    }

    return ss;
  }

  /**
   * 替换字符串颜色
   *
   * @param data 数据源
   * @param color 要替换成的颜色
   * @return 替换后的字符串
   */
  public static CharSequence getColorString(String data, int color) {
    if (data == null) {
      return null;
    }
    return getColorString(data, color, 0, data.length());
  }
}
