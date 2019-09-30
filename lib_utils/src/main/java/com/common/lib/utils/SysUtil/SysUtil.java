package com.common.lib.utils.SysUtil;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 创建时间: 2019/09/30 17:32 <br>
 * 作者: qiudengjiao <br>
 * 描述:  SysUtil 获得和系统ROM相关的信息
 */
public class SysUtil {

  /**
   * Don't let anyone instantiate this class.
   */
  private SysUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * checkPermissions
   *
   * @return true or false
   */
  public static boolean checkPermissions(Context context, String permission) {
    PackageManager localPackageManager = context.getPackageManager();
    return localPackageManager.checkPermission(permission, context.getPackageName())
        == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * 检查某个uid或者pid是否有permission权限
   *
   * @return true or false
   */
  public static boolean checkPermissionByID(Context context, String permission, int uid, int pid) {
    return context.checkPermission(permission, pid, uid) == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * 检查调用者是否有permission权限
   *
   * @return true or false
   */
  public static boolean checkCallingPermission(Context context, String permission) {
    return context.checkCallingPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * 检查调用者或自己是否有permission权限
   *
   * @return true or false
   */
  public static boolean checkCallingOrSelfPermission(Context context, String permission) {
    return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * get OS number
   *
   * @return the type of 4.4.2
   */
  public static String getSysVersion(@NonNull Context context) {
    String osVersion = "";
    if (SysUtil.checkPermissions(context, "android.permission.READ_PHONE_STATE")) {
      osVersion = Build.VERSION.RELEASE;
      return osVersion;
    } else {
      return null;
    }
  }

  /** device model */
  public static String getModel() {
    return Build.MODEL;
  }

  /** device factory */
  public String getFactory() {
    return Build.MANUFACTURER;
  }

  /** device manu time */
  public String getManuTime() {
    return String.valueOf(Build.TIME);
  }

  /** device system build version */
  public String getManuID() {
    return Build.ID;
  }

  /** device language */
  public String getLanguage() {
    return Locale.getDefault().getLanguage();
  }

  /** device system time zone */
  public String getTimeZone() {
    return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
  }

  /** device is rooted or not */
  public boolean isRooted() {
    int kSystemRootStateUnknow = -1;
    int kSystemRootStateDisable = 0;
    int kSystemRootStateEnable = 1;
    int systemRootState = kSystemRootStateUnknow;

    if (systemRootState == kSystemRootStateEnable) {
      return true;
    } else if (systemRootState == kSystemRootStateDisable) {
      return false;
    }
    File f = null;
    final String kSuSearchPaths[] = {
        "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"
    };
    try {
      for (int i = 0; i < kSuSearchPaths.length; i++) {
        f = new File(kSuSearchPaths[i] + "su");
        if (f != null && f.exists()) {
          systemRootState = kSystemRootStateEnable;
          return true;
        }
      }
    } catch (Exception e) {
      return false;
    }
    systemRootState = kSystemRootStateDisable;
    return false;
  }

  /** get CPU name */
  @SuppressWarnings("resource") public String getCpuName() {
    try {
      FileReader fr = new FileReader("/proc/cpuinfo");
      BufferedReader br = new BufferedReader(fr);
      String text = br.readLine();
      String[] array = text.split(":\\s+", 2);
      return array[1];
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
