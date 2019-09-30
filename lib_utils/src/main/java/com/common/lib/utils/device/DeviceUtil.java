package com.common.lib.utils.device;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.common.lib.utils.LogUtil;
import com.common.lib.utils.StringUtil;
import com.common.lib.utils.SysUtil.SysUtil;
import com.common.lib.utils.base.HashUtil;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 创建时间: 2019/09/30 17:28 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class DeviceUtil {
  private static final String TAG = DeviceUtil.class.getSimpleName();
  private static final String UUID_STATICS = "uuid_statics";
  private static final String DEVICE_ID = "lianjia_device_id";
  private static final String IDID = "IDID";

  private static final String CONFIG = "config";
  private static Point sScreenSize = new Point(-1, -1);  //初始化默认值
  private static int sStatusBarHeight;

  private static final String KEY_IMEI = "IMEI";
  private static String sImei;
  private static String sAndroidId;
  private static String sMacAddress;

  /**
   * Don't let anyone instantiate this class.
   */
  private DeviceUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * <p>获取设备唯一ID号。</p>
   * <p>策略为：</p>
   * <p>第一优先序列为移动设备的IMEI或者CDMA手机的MEID、ESN</p>
   * <p>第二优先序列为android系统提供的androidId</p>
   * <p>第三优先序列为android系统属性里存储的ro.serialno</p>
   * <p>第四优先序列为自动生成随机数</p>
   *
   * @param context add <uses-permission android:name="READ_PHONE_STATE" />
   */
  public static String getDeviceID(@NonNull Context context) {

    if (context == null) {
      return "";
    }

    SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    String deviceId = sp.getString(DEVICE_ID, "");

    if (!TextUtils.isEmpty(deviceId)) {
      return deviceId;
    }

    deviceId = getDeviceIdByPhoneInfo(context);

    if (TextUtils.isEmpty(deviceId)) {
      deviceId = getDeviceIdByAndroidSystem(context);
    }

    if (TextUtils.isEmpty(deviceId)) {
      deviceId = getDeviceIdBySystemProperties(context);
    }

    if (TextUtils.isEmpty(deviceId)) {
      deviceId = HashUtil.md5(UUID.randomUUID().toString());
    }

    // 保存到config文件中
    editor.putString(DEVICE_ID, deviceId);
    editor.commit();

    return deviceId;
  }

  /**
   * 针对动态权限获取的情况
   * 重新按照规则获取设备ID，并且存到本地。
   *
   */
  public static String getDeviceIDEx(@NonNull Context context) {

    if (context == null) {
      return "";
    }

    String deviceId = getDeviceIdByPhoneInfo(context);

    if (TextUtils.isEmpty(deviceId)) {
      deviceId = getDeviceIdByAndroidSystem(context);
    }

    if (TextUtils.isEmpty(deviceId)) {
      deviceId = getDeviceIdBySystemProperties(context);
    }

    if (TextUtils.isEmpty(deviceId)) {
      deviceId = HashUtil.md5(UUID.randomUUID().toString());
    }

    // 保存到config文件中
    SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(DEVICE_ID, deviceId);
    editor.commit();

    return deviceId;
  }

  /**
   * 获取移动设备的IMEI或者CDMA手机的MEID、ESN
   *
   * @param context 上下文对象
   * @return 移动设备的IMEI或者CDMA手机的MEID、ESN，或者为空（如果没有{@link Manifest.permission#READ_PHONE_STATE}权限）
   */
  @SuppressLint("MissingPermission")
  public static String getDeviceIdByPhoneInfo(Context context) {
    String deviceId = null;

    if (SysUtil.checkPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
      try {
        TelephonyManager tm =
            (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
          String backId = deviceId;  //判断deviceId是否全为0
          backId = backId.replace("0", "");
          if (TextUtils.isEmpty(backId)) deviceId = "";
        }
      } catch (Exception e) {
        // ignore
      }
    }

    return deviceId;
  }

  /**
   * 获取android系统提供的androidId
   *
   * @param context 上下文对象
   * @return android系统提供的androidId`
   */
  public static String getDeviceIdByAndroidSystem(Context context) {
    String androidId = null;
    ContentResolver resolver = context.getContentResolver();
    if (resolver != null) {
      try {
        androidId =
            android.provider.Settings.System.getString(resolver, Settings.Secure.ANDROID_ID);
      } catch (Exception e) {
        //ignore
      }
    }
    return androidId;
  }

  /**
   * 获取android系统属性里存储的ro.serialno
   *
   * @return android系统属性里存储的ro.serialno
   */
  public static String getDeviceIdBySystemProperties() {
    String deviceId = null;
    try {
      Class c = Class.forName("android.os.SystemProperties");
      Method get = c.getMethod("get", new Class[] { String.class, String.class });
      deviceId = (String) get.invoke(c, new Object[] { "ro.serialno", "" });
    } catch (Exception t) {
      //ignore
    }
    return deviceId;
  }

  @SuppressLint("HardwareIds")
  public static String getDeviceIdBySystemProperties(@NonNull Context context) {
    String deviceId = null;
    try {
      deviceId = getDeviceIdBySystemProperties();
      if (TextUtils.isEmpty(deviceId)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
          deviceId = Build.getSerial();
        } else {
          deviceId = Build.SERIAL;
        }
      }
    } catch (Exception t) {
      //ignore
    }
    if(Build.UNKNOWN.equals(deviceId)){
      deviceId = null;
    }

    return deviceId;
  }

  /**
   * To determine whether it contains a gyroscope
   *
   * @return boolean
   */
  public static boolean hasGravity(@NonNull Context context) {
    SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    if (manager == null) {
      return false;
    }
    return true;
  }


  /**
   * 获取屏幕高度x宽度
   */
  public static String getScreenSize(@NonNull Context context) {
    DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
    if (context.getResources().getConfiguration().orientation
        == Configuration.ORIENTATION_PORTRAIT) {
      return dm2.widthPixels + "x" + dm2.heightPixels;
    } else {
      return dm2.heightPixels + "x" + dm2.widthPixels;
    }
  }

  /**
   * 获取屏幕宽度和高度，单位为px
   */
  public static Point getScreenMetrics(@NonNull Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    int w_screen = dm.widthPixels;
    int h_screen = dm.heightPixels;
    return new Point(w_screen, h_screen);
  }

  /**
   * 获取屏幕长宽比
   */
  public static float getScreenRate(@NonNull Context context) {
    Point P = getScreenMetrics(context);
    float H = P.y;
    float W = P.x;
    return (H / W);
  }

  /**
   * 获取屏幕宽度
   */
  public static int getScreenWidth(@NonNull Context context) {

    if (sScreenSize.x <= 0 || sScreenSize.y <= 0) {
      WindowManager wm =
          (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
      Display display = wm.getDefaultDisplay();
      //getSize方法里面直接对Point的x,y赋值,没有检测和创建Point。如果Point等于空,会有NPE。
      sScreenSize = new Point();
      display.getSize(sScreenSize);
    }

    return sScreenSize.x;
  }

  /**
   * 获取屏幕高度
   */
  public static int getScreenHeight(@NonNull Context context) {

    if (sScreenSize.x <= 0 || sScreenSize.y <= 0) {
      WindowManager wm =
          (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
      Display display = wm.getDefaultDisplay();
      //getSize方法里面直接对Point的x,y赋值,没有检测和创建Point。如果Point等于空,会有NPE。
      sScreenSize = new Point();
      display.getSize(sScreenSize);
    }

    return sScreenSize.y;
  }

  /**
   * 获取状态栏高度
   */
  public static int getStatusBarHeight(@NonNull Context context) {

    if (sStatusBarHeight == 0) {
      int resourceId = context.getApplicationContext()
          .getResources()
          .getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
        sStatusBarHeight =
            context.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
      }
    }
    return sStatusBarHeight;
  }

  //每次安装生成新的,生命周期对应一次安装
  //注意只在统计的时候使用
  public static String getUUID(@NonNull Context context) {
    String uuid = readUUID(context);
    if (TextUtils.isEmpty(uuid)) {
      uuid = generateUUID(context);
    }
    return uuid;
  }

  private static String readUUID(@NonNull Context context) {
    SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    String uuid = sp.getString(UUID_STATICS, "").trim();
    return uuid;
  }

  private static synchronized String generateUUID(@NonNull Context context) {
    SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    String uuid = sp.getString(UUID_STATICS, "").trim();
    if (!TextUtils.isEmpty(uuid)) {
      return uuid;
    }
    SharedPreferences.Editor editor = sp.edit();
    uuid = UUID.randomUUID().toString();
    String uuidString = uuid.toString();
    // 保存到config文件中
    editor.putString(UUID_STATICS, uuidString);
    editor.commit();
    return uuidString;
  }

  @SuppressLint("MissingPermission")
  public static String getUDID(Context context) {
    if (context == null) {
      return "";
    }

    SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    String deviceId = sp.getString(IDID, "").trim();

    if (TextUtils.isEmpty(deviceId)) {
      // 如果config文件中没有存储IDID，则需要重新获取IDID,首先考虑deviceId
      if (checkHasPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
        try {
          TelephonyManager tm =
              (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
          deviceId = tm != null ? StringUtil.trim(tm.getDeviceId()) : "";
        } catch ( Exception e) {
          e.printStackTrace();
        }
      }

      // 如果没有获取到deviceId,则通过getMyUUID获取
      if (TextUtils.isEmpty(deviceId) || "000000000000000".equals(deviceId)) {
        OpenUDIDManager.sync(context);
        if (OpenUDIDManager.isInitialized()) {
          deviceId = OpenUDIDManager.getOpenUDID();
        }
        if (TextUtils.isEmpty(deviceId)) {
          UUID uuid = UUID.randomUUID();
          String uuidString = uuid.toString();
          if (!TextUtils.isEmpty(uuidString) && uuidString.contains("-")) {
            uuidString = uuidString.replace("-", "");
          }
          deviceId = uuidString;
        }

        if (TextUtils.isEmpty(deviceId)) {
          // 产生一个8位随机数(10000000~99999999)
          Random random = new Random();
          int randomNum = random.nextInt(89999999) + 10000000;
          deviceId = String.valueOf(randomNum);
        }
      }
      // 保存到config文件中
      editor.putString(IDID, deviceId);
      editor.commit();
    }
    return StringUtil.trim(deviceId);
  }

  /**
   * checkHasPermissions
   * @return true or false
   */
  public static boolean checkHasPermissions(Context context, String permission) {
    try {
      return SysUtil.checkPermissions(context, permission);
    } catch (Throwable e) {
      LogUtil.i(TAG, e.getMessage());
      return false;
    }
  }

  /**
   * 获取IMEI
   *
   * @param context Context
   * @return IMEI
   */
  @SuppressLint("MissingPermission")
  public static String getIMEI(Context context) {
    if (!TextUtils.isEmpty(sImei)) {
      return sImei;
    }

    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    String imei = sp.getString(KEY_IMEI, "");

    if (!TextUtils.isEmpty(imei)) {
      sImei = imei;
      return imei;
    }

    try {
      if (!SysUtil.checkPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
        return null;
      }
      TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      if (tm != null) {
        imei = tm.getDeviceId();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    SharedPreferences.Editor editor = sp.edit();
    editor.putString(KEY_IMEI, imei);
    editor.apply();

    sImei = imei;
    return imei;
  }

  /**
   * 获取android系统提供的androidId
   *
   * @param context 上下文对象
   * @return android系统提供的androidId
   */
  public static String getAndroidID(Context context) {
    if (!TextUtils.isEmpty(sAndroidId)) {
      return sAndroidId;
    }
    String androidId = "";
    ContentResolver resolver = context.getContentResolver();
    if (resolver != null) {
      try {
        androidId =
            android.provider.Settings.System.getString(resolver, Settings.Secure.ANDROID_ID);
      } catch (Exception e) {
        //ignore
      }
    }
    sAndroidId = androidId;
    return androidId;
  }

  /**
   * 获取手机的MAC地址
   *
   * @return mac address
   */
  private static final String marshmallowMacAddress = "02:00:00:00:00:00";

  public static String getMacAddress(Context context) {

    if (!TextUtils.isEmpty(sMacAddress)) {
      return sMacAddress;
    }

    try {
      if (!SysUtil.checkPermissions(context, Manifest.permission.ACCESS_WIFI_STATE)) {
        return "";
      }
      WifiManager wifiMan =
          (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
      WifiInfo wifiInfo = wifiMan.getConnectionInfo();

      if (wifiInfo != null && marshmallowMacAddress.equals(wifiInfo.getMacAddress())) {
        String result = null;
        try {
          result = getMacAddressByInterface();
          if (result != null) {
            sMacAddress = result;
            return result;
          }
        } catch (Exception e) {
          //ignore
        }
      } else {
        if (wifiInfo != null && wifiInfo.getMacAddress() != null) {
          sMacAddress = wifiInfo.getMacAddress();
          return sMacAddress;
        } else {
          return "";
        }
      }
      sMacAddress = marshmallowMacAddress;
      return marshmallowMacAddress;
    } catch (Exception e) {
      //ignore
    }
    return "";
  }

  private static String getMacAddressByInterface() {
    try {
      List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface nif : all) {
        if (nif.getName().equalsIgnoreCase("wlan0")) {
          byte[] macBytes = nif.getHardwareAddress();
          if (macBytes == null) {
            return "";
          }

          StringBuilder res1 = new StringBuilder();
          for (byte b : macBytes) {
            res1.append(String.format("%02X:", b));
          }

          if (res1.length() > 0) {
            res1.deleteCharAt(res1.length() - 1);
          }
          return res1.toString();
        }
      }
    } catch (Exception e) {
      //ignore
    }
    return null;
  }

}
