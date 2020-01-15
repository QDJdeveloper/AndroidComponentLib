package co.review.androidcommonlib.designmode.abstraction;

import android.graphics.Picture;
import android.media.Image;

/**
 * 创建时间: 2020/01/15 11:38 <br>
 * 作者: qiudengjiao <br>
 * 描述: 抽象接口练习
 * 抽象讲的是如何隐藏方法的具体实现，让调用者只需要关心方法提供了哪些功能，并不需要知道这些功能是如何实现的
 *
 * 在面向对象编程中，我们常借助编程语言提供的接口类（比如 Java 中的 interface 关键字语法）
 * 或者抽象类（比如 Java 中的 abstract 关键字语法）这两种语法机制，来实现抽象这一特性
 */
public class PictureStorage implements IPictureStorage {

  @Override
  public void savePicture(Picture picture) {

  }

  @Override
  public Image getPicture(String pictureId) {
    return null;
  }

  @Override
  public void deletePicture(String pictureId) {

  }

  @Override
  public void modifyMetaInfo(String pictureId) {

  }
}
