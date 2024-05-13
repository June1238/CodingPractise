import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(concatArr(new int[]{3,2,4,6},new int[]{5,7,11}));
    }

    /**分割回文串：给定字符串s 将s分割成一些子串 使每个子串都是回文串 返回s
     * 所有可能的分割方案 回文串是正读和反读都一样的字符串
     *
     * sample 1 : "google"
     * sample 2 : "aab"
     * sample 3: "a"
     *  没有按照顺序进行输出
     * */
    static boolean[][] dp = new boolean[17][17];
    public static void partitions(String s){
        int len = s.length();
        /**初始化所有数组*/
        for(int i = 0;i<len;i++)
            Arrays.fill(dp[i],false);
        for(int i = 0;i<len;i++){
            dp[i][i] = true;
            /**判断中间的字符串是否回文*/
            for(int j = 0;j<len&&i!=j;j++){
                if(s.charAt(i)==s.charAt(j)&&(Math.abs(j-i)==1||dp[Math.max(i,j)-1][Math.min(i,j)+1])) {
                        dp[i][j] = dp[j][i] = true;
                }
            }
        }
        DFS(0,new LinkedList<>(),s);
        String[][] tempR = res.stream().
                map(listS->listS.toArray(new String[0]))
                .toArray(String[][]::new);

        for(List<String> listS : res){
            for(String ss:listS){
                System.out.print(ss+" ");
            }
            System.out.println();
        }


    }
    static List<List<String>> res = new ArrayList<>();
    /**在每个可以分裂的地方进行分裂*/
    public static void DFS(int curIdx, LinkedList<String> temp, String str){
        if(curIdx>=str.length()){
            List<String> newVar = List.copyOf(temp);
            res.add(newVar);
            return ;
        }
        /**直接遍历寻找即可*/
        for(int i = curIdx;i<str.length();i++){
            if(dp[i][curIdx]){
                String ss = str.substring(curIdx,i+1);
                temp.addLast(ss);
                DFS(i+1,temp,str);
                temp.removeLast();
            }
        }
    }

    /**
     * 分割回文串 - 给定一个字符串s 将s分割成一些子串 使每个子串都是回文串
     * 返回符合要求的最小分割次数*/
    public static int minCut(String s){
        int cnt = 0;
        int len = s.length();
        /**预处理字符串 制定好分割点*/
        boolean[][] dp = new boolean[len+1][len+1];
        for(int i = 0;i<=len;i++){
            Arrays.fill(dp[i],false);
        }
        for(int i = 0;i<len;i++){
            dp[i][i] = true;
            for(int j = 0;j<len&&j!=i;j++){
                if(s.charAt(i)==s.charAt(j)&&(Math.abs(i-j)==1||dp[Math.max(i,j)-1][Math.min(i,j)+1]))
                    dp[i][j] = dp[j][i] = true;
            }
        }

        int[] res = new int[len+1];
        res[0] = 0;
        for(int i = 0;i<len;i++){
            res[i+1] = res[i]+1;
            for(int j = 0;j<i;j++){
                if(dp[i][j]==true)
                    res[i+1] = Math.min(res[i+1],res[j]+1);
            }
        }
        return res[len]-1;
    }

    public static boolean averageNum(int[] arr){
        int[] recordMap = new int[2];
        Arrays.fill(recordMap,0);
        for(int num : arr){
            if(num % 2 == 0) return true;
            recordMap[num%4-1] ++;
        }
        if(recordMap[0]>=1&&recordMap[2]>=1)
            return true;
        return false;
    }

    public int minimumRefill(int[] plants,int capacityA,int capacityB){
        int i = 0 , j = plants.length - 1;
        int ret = 0 , tempCapA = capacityA , tempCapB = capacityB;
        while(i<j){
            if(tempCapA<plants[i]){
                tempCapA  = capacityA;
                ret ++;
            }
            if(tempCapB < plants[j])
            {
                tempCapB = capacityB;
                ret++;
            }
            tempCapA -= plants[i];
            tempCapB -= plants[j];
            i++;
            j--;
        }
        if(i==j){
            if(tempCapA < tempCapB){
                if(tempCapB<plants[i])
                    ret++;
            }
            else{
                if(tempCapA<plants[i])
                    ret++;
            }
        }
        return ret;
    }

    /**讨厌鬼的数组拼接
     *
     * 定义一个所有元素互不相等的数组的权值 ： 最大值所在位置的下标 和 最小值所在位置的下标 Diff
     * 现在有一个长度n互不相等的数组a 以及一个有m个元素的集合s
     * */
    public static int concatArr(int[] originArr,int[] addArr){
        HashSet<Integer> recordSet = new HashSet<>();
        int minValue = originArr[0] , minIndex = 0;
        int maxValue = originArr[0] , maxIndex = 0;
        for(int i = 0;i<originArr.length;i++){
            recordSet.add(originArr[i]);
            if(originArr[i] < minValue){
                minValue = originArr[i];
                minIndex = i;
            }

            if(originArr[i] > maxValue){
                maxValue = originArr[i];
                maxIndex = i;
            }
        }
        int cnt = 0;
        int addMinV = Integer.MAX_VALUE , addMaxV = Integer.MIN_VALUE;
        for(int i = 0;i<addArr.length;i++){
            if(recordSet.contains(addArr[i])==false){
                cnt++;
                recordSet.add(addArr[i]);
                if(addArr[i]<addMinV) { addMinV = addArr[i]; }
                if(addArr[i]>addMaxV) { addMaxV = addArr[i]; }
            }
        }

        if(addMaxV>maxValue && addMinV<minValue) return recordSet.size() - 1;
        if(addMaxV<maxValue && addMinV>minValue) return Math.abs(maxIndex-minIndex);
        int pos = 0;
        if(maxValue>addMaxV) pos = maxIndex;
        if(minValue<addMinV) pos = minIndex;

        return cnt + Math.max(pos,originArr.length-pos-1) ;

    }
}

/**standard answer*/
class Solution{
    public int minCnt(String s){
        int n = s.length();
        boolean[][] g = new boolean[n][n];
        for(int i = 0;i<n;i++){
            Arrays.fill(g[i],true);
        }

        /** 要求是在进行计算机的时候 前半部分的值都已经转移过 即使用过了
         * 判断是否为回文串的方法-思路：常规的方法是使用双指针分别指向i和j 每次
         * 判断两个指针指向的字符是否相同 直到两个指针相遇 然而这种方法会产生
         * 重复计算
         * */
        for(int i = n-1;i>=0;i--){
            for(int j = i+1;j<n;j++){
                g[i][j] = s.charAt(i)==s.charAt(j)&&g[i+1][j-1];
            }
        }

        int[] f = new int[n];
        Arrays.fill(f,Integer.MAX_VALUE);
        for(int i = 0;i < n;i ++){
            /**截止到本节点为止的最小分割次数*/
            if(g[0][i]) f[i] = 0;
            else{
                for(int j = 0;j<i;j++){
                    if(g[j+1][i])
                        f[i] = Math.min(f[i],f[j]+1);
                }
            }
        }
        return f[n-1];
    }
}