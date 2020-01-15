package co.review.androidcommonlib.designmode.polymorphism;

/**
 * 创建时间: 2020/01/15 12:28 <br>
 * 作者: qiudengjiao <br>
 * 描述: 接下来，我们先来看如何利用接口类来实现多态特性
 */
public interface Iterator {

  boolean hasNext();

  String next();

  String remove();

}
