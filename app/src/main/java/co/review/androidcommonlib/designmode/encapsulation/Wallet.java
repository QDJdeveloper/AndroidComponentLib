package co.review.androidcommonlib.designmode.encapsulation;

import co.review.androidcommonlib.designmode.exception.InvalidAmountException;
import java.math.BigDecimal;

/**
 * 创建时间: 2020/01/15 10:56 <br>
 * 作者: qiudengjiao <br>
 * 描述: 封装练习
 *
 * 从代码中，我们可以发现，Wallet 类主要有四个属性（也可以叫作成员变量），也就是我们前面定义中提到的信息或者数据。
 * 其中，id 表示钱包的唯一编号，
 * createTime 表示钱包创建的时间，
 * balance 表示钱包中的余额，
 * balanceLastModifiedTime 表示上次钱包余额变更的时间。我们参照封装特性，对钱包的这四个属性的访问方式进行了限制。
 * 调用者只允许通过下面这六个方法来访问或者修改钱包里的数据
 */
public class Wallet {

  private String id;
  private long createTime;
  private BigDecimal balance;
  private long balanceLastModifiedTime;

  public Wallet() {
    this.id = creatId();
    this.createTime = System.currentTimeMillis();
    this.balance = BigDecimal.ZERO;
    this.balanceLastModifiedTime = System.currentTimeMillis();
  }

  private String creatId() {
    return "";
  }

  public String getId() {
    return id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public long getBalanceLastModifiedTime() {
    return balanceLastModifiedTime;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void increaseBalance(BigDecimal increaseAmount) throws InvalidAmountException {
    if (increaseAmount.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidAmountException("No InvalidAmount");
    }
    balance.add(increaseAmount);
    this.balanceLastModifiedTime = System.currentTimeMillis();
  }

  public void decreaseBalance(BigDecimal increaseAmount) throws InvalidAmountException {
    if (increaseAmount.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidAmountException("No InvalidAmount");
    }
    balance.subtract(increaseAmount);
    this.balanceLastModifiedTime = System.currentTimeMillis();
  }
}
