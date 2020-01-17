package co.review.androidcommonlib.designmode.abstract_and_interface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;

/**
 * 创建时间: 2020/01/16 15:33 <br>
 * 作者: qiudengjiao <br>
 * 描述: 抽象类的子类：输出日志到文件
 */
public class FileLogger extends Logger {

  private Writer fileWriter;

  public FileLogger(String name, boolean enabled, Level minPermittedLevel, String filepath)
      throws IOException {
    super(name, enabled, minPermittedLevel);
    this.fileWriter = new FileWriter(filepath);
  }


  @Override
  protected void doLog(Level level, String message) {
    // 格式化level和message,输出到日志文件
    try {
      fileWriter.write("");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
