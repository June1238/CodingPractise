import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LCR086Partition {
    public static void main(String[] args){
        System.out.println(minDays(6));
    }

    /**
     * 吃掉n个橘子的最少天数
     * 厨房里有n个橘子 可以决定每一天选择如下方式之一吃掉这些橘子
     *
     * 10 1+dp[5] dp[5] = 1+3 dp[4] = 1+2
     *
     *  - 吃掉一个橘子
     *  - 剩余橘子数可以被2整除 吃掉一半
     *  - 剩余橘子数可以被3整除 吃掉三分之二
     * */
    public static int minDays(int n){
        HashMap<Integer,Integer> recordMap = new HashMap<>();
        int lastNum = 0;
        for(int i = 1;i<=n;i++){
            int curNum = i;
            if(i%2 == 0) curNum = Math.min(curNum,recordMap.get(i/2)+1);
            if(i%3 == 0) curNum = Math.min(curNum,recordMap.get(i/3)+1);
            lastNum = Math.min(curNum,lastNum+1);
            if(i<=(n+1)/2) recordMap.put(i,lastNum);
        }
        /**返回吃掉n个橘子的最少天数*/
        return lastNum;
    }
    /**直接dfs自顶向下记忆化搜索 O(logN)*/
    static HashMap<Integer,Integer> recordMap = new HashMap<>();
    public static int dfs(int n){
        if(n<2){
            return n;
        }
        if(recordMap.containsKey(n)) return recordMap.get(n);

        int res = Math.min(n%2+dfs(n/2),n%3+dfs(n/3))+1;
        recordMap.put(n,res);
        return res;
    }

    /**收集垃圾的最少总时间
     * garbage:字符数组 每个字符表示单一的金属、纸和玻璃
     *
     * travel[i]是指垃圾车从房子i行驶到房子i+1需要的分钟数
     * 城市里总共有三辆垃圾车  分别收拾三种垃圾 每辆垃圾车- 按顺序到达每一栋房子 但不
     * 是必须到达所有的房子
     * */
    public static int garbageCollection(String[] garbage, int[] travel){
        int GoldenTime = 0,PaperTime = 0,GlassTime = 0,res = 0;
        for(int i = 0;i < garbage.length;i++){
            res += garbage[i].length();
            for(char ch : garbage[i].toCharArray()){
                switch (ch){
                    case 'G':
                        GoldenTime = i;
                        break;
                    case 'P':
                        PaperTime = i;
                        break;
                    case 'M':
                        GlassTime = i;
                        break;
                }
            }
        }
        int tempNum = 0;
        for(int i = 0;i < travel.length;i++){
            tempNum += travel[i];
            if(GoldenTime == i) res += tempNum;
            if(PaperTime == i) res += tempNum;
            if(GlassTime == i) res += tempNum;
            if(GoldenTime <=i && PaperTime<=i && GlassTime<=i) break;
        }
        return res;
    }

}

