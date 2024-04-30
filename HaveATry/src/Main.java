import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    /**类内方法不加public修饰符*/
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        System.out.println("Hello World!");
        for(int i = 0;i<5;i++){
            new Thread(()->{
                System.out.println("Thread Name is : "+Thread.currentThread().getName()+" ; ");
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
            System.out.println("子线程结束啦....");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("这是finally代码块...");
        }
    }
}
class Funct{
    public static String name = "hallo";
    public static void changeName(String newName){
        name = newName;
    }
    public static void CallF(){
        System.out.println(name+"真尼玛傻逼");
    }

    public CountDownLatch countDownLatch = new CountDownLatch(5);
}

/**leetcode 202*/
class Solution {
    /** 博弈: n个石子 不同权重  alice先手*/
    public int stoneGameVI(int[] aliceValues,int[] bobValues){
        int total = 0;
        return total;
    }

    /**返回布尔值 或者是true 或者是false alice先手 发挥出最佳水平*/
    public boolean stoneGameI(int[] alicevalues,int[] bobValues){
        return true;
    }
}