package com.common.lib.utils;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.common.lib.utils.StringUtil.trim;
import static com.common.lib.utils.io.IoStreamUtil.streamToBytes;

/**
 * 创建时间: 2019/09/30 14:54 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class GsonUtil {


  private static Gson mGson = new Gson();

  /**
   * 将成对象转化JSON字符串
   */
  public static String toJsonStr(Object src) {
    return mGson.toJson(src);
  }

  /**
   * 将JSON字符串转化成对象
   */
  public static <T> T getData(String result, Class<T> cls) {
    try {
      return mGson.fromJson(trim(result), cls);
    } catch (Exception e) {
      LogUtil.e("JsonUtil", e.getMessage());
      return null;
    }
  }

  public static <T> T getObjectFromAssets(Context context, Class<T> cls, String fileName) {
    try {
      InputStream is = context.getAssets().open(fileName);
      byte[] data = streamToBytes(is);
      String result = new String(data, "utf-8");
      return getData(result, cls);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 从assets文件中获取jsonObject
   */
  public static JsonObject getJsonObjectFromAssets(Context context, String fileName) {
    try {
      InputStream is = context.getAssets().open(fileName);
      byte[] data = streamToBytes(is);
      String result = new String(data, "utf-8");
      return getData(result, JsonObject.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将字符串转化成对象集合
   */
  public static <T> List<T> getListData(String result,Class clazz) {
    try {
      if (!TextUtils.isEmpty(result)) {
        Gson gson = new Gson();
        Type type = new ParameterizedTypeImpl(clazz);
        return gson.fromJson(result,type);
      } else {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private  static class ParameterizedTypeImpl implements ParameterizedType {
    Class clazz;

    public ParameterizedTypeImpl(Class clz) {
      clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
      return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
      return List.class;
    }

    @Override
    public Type getOwnerType() {
      return null;
    }
  }

}
