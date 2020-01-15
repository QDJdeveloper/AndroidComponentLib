package co.review.androidcommonlib.designmode.polymorphism;

/**
 * 创建时间: 2020/01/15 12:04 <br>
 * 作者: qiudengjiao <br>
 * 描述: 多态练习
 *
 * 第一个语法机制是编程语言要支持父类对象可以引用子类对象，也就是可以将 SortedDynamicArray 传递给 DynamicArray。
 * 第二个语法机制是编程语言要支持继承，也就是 SortedDynamicArray 继承了 DynamicArray，才能将 SortedDyamicArray 传递给 DynamicArray。
 * 第三个语法机制是编程语言要支持子类可以重写（override）父类中的方法，也就是 SortedDyamicArray 重写了 DynamicArray 中的 add() 方法。
 */
public class DynamicArray {

  private static final int DEFAULT_CAPACITY = 10;
  protected int size = 0;
  protected int capacity = DEFAULT_CAPACITY;
  protected Integer[] elements = new Integer[DEFAULT_CAPACITY];

  public int size() {
    return this.size;
  }

  public Integer get(int index) {
    return elements[index];
  }
  //...省略n多方法...

  public void add(Integer e) {
    ensureCapacity();
    elements[size++] = e;
  }

  protected void ensureCapacity() {
    //...如果数组满了就扩容...代码省略...
  }
}
