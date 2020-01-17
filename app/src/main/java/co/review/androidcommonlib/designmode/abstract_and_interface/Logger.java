package co.review.androidcommonlib.designmode.abstract_and_interface;

import java.util.logging.Level;

/**
 * 创建时间: 2020/01/16 15:07 <br>
 * 作者: qiudengjiao <br>
 * 描述: 抽象类练习
 */
public abstract class Logger {

  private String name;
  private boolean enabled;
  private Level minPermittedLevel;

  public Logger(String name, boolean enabled, Level minPermittedLevel) {
    this.name = name;
    this.enabled = enabled;
    this.minPermittedLevel = minPermittedLevel;
  }

  public void log(Level level, String message) {
    boolean loggable = enabled && (minPermittedLevel.intValue() <= level.intValue());
    if (!loggable) return;
    doLog(level, message);
  }

  protected abstract void doLog(Level level, String message);


}
