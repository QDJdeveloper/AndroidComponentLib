package co.review.androidcommonlib.designmode.polymorphism;

/**
 * 创建时间: 2020/01/15 12:18 <br>
 * 作者: qiudengjiao <br>
 * 描述:
 */
public class Texample {


  protected static void main() {
    SortedDynamicArray sortedDynamicArray = new SortedDynamicArray();
    // 打印结果：1、3、5
    test(sortedDynamicArray);

    Iterator arrayIterator = new Array();

    print(arrayIterator);

    Iterator linkedArray = new LinkedList();

    print(linkedArray);
  }

  public static void test(DynamicArray dynamicArray) {
    dynamicArray.add(5);
    dynamicArray.add(1);
    dynamicArray.add(3);
    for (int i = 0; i < dynamicArray.size(); ++i) {
      System.out.println(dynamicArray.get(i));
    }
  }

  private static void print(Iterator iterator) {
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }


}
