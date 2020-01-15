package co.review.androidcommonlib.designmode.abstraction;

import android.graphics.Picture;
import android.media.Image;

/**
 * 创建时间: 2020/01/15 11:36 <br>
 * 作者: qiudengjiao <br>
 * 描述: 抽象接口练习
 */
public interface IPictureStorage {

  void savePicture(Picture picture);

  Image getPicture(String pictureId);

  void deletePicture(String pictureId);

  void modifyMetaInfo(String pictureId);
}
