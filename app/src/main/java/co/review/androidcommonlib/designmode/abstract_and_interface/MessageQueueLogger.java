package co.review.androidcommonlib.designmode.abstract_and_interface;

import java.util.logging.Level;

/**
 * 创建时间: 2020/01/16 15:38 <br>
 * 作者: qiudengjiao <br>
 * 描述: 抽象类的子类: 输出日志到消息中间件(比如kafka)
 */
public class MessageQueueLogger extends Logger {

  private MessageQueueClient msgQueueClient;

  public MessageQueueLogger(String name, boolean enabled, Level minPermittedLevel,
      MessageQueueClient msgQueueClient) {
    super(name, enabled, minPermittedLevel);
    this.msgQueueClient = msgQueueClient;
  }

  @Override
  protected void doLog(Level level, String message) {
    // 格式化level和message,输出到消息中间件
    msgQueueClient.send();
  }
}
