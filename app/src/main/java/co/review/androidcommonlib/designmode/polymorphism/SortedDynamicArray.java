package co.review.androidcommonlib.designmode.polymorphism;

/**
 * 创建时间: 2020/01/15 12:16 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class SortedDynamicArray extends DynamicArray {

  @Override
  public void add(Integer e) {
    super.add(e);
    ensureCapacity();
    int i;
    for (i = size - 1; i >= 0; --i) { //保证数组中的数据有序
      if (elements[i] > e) {
        elements[i + 1] = elements[i];
      } else {
        break;
      }
    }
    elements[i + 1] = e;
    ++size;
  }
}
