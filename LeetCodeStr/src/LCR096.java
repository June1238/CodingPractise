import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

public class LCR096 {

    public static void main(String[] args){
        System.out.println(findMinimumTime(new int[][]{{2,3,1},{4,5,1},{1,5,2}}));
    }
    /**交错字符串
     * 给丁三个字符串 判断s3能不能够由s1和s2交织组成
     * */
    public static boolean isInterLeave(String s1,String s2,String s3){
        boolean res = false;
        char[] chArr1 = s1.toCharArray(),chArr2 = s2.toCharArray(),chArr3 = s3.toCharArray();
        int len1 = s1.length() , len2 = s2.length() , len3 = s3.length();
        if(len2+len1!=len3) return false;
        int[][] f = new int[len1+1][len2+1];
        for(int i = 0;i<=len1;i++)
            Arrays.fill(f[i],0);
        f[0][0] = 1;
        for(int i = 0;i<=len1;i++){
            for(int j = 0;j<=len2;j++){
                if(i>0 && chArr1[i-1]==chArr3[i+j-1])
                    f[i][j] |= f[i-1][j];
                /**表示 i = 0*/
                if(j>0 && chArr2[j-1]==chArr3[i+j-1])
                    f[i][j] |= f[i][j-1];
            }
        }
        return f[len1][len2]==1;
    }

    /**摘樱桃：从位置0,0出发 最后到达
     * 两次dp分别求取最多樱桃
     * 中间使用回溯算法恢复grid的地图路径 两个人从同一路径出发
     * */
    public static int cherryPickup(int[][] grid){
        int m = grid.length , n = grid[0].length;
        int[][] f = new int[m][n];
        for(int i = 0;i<m;i++)
            Arrays.fill(f[i],0);
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                if(grid[i][j]!=-1){
                    f[i][j] = grid[i][j] + Math.max(i>0?f[i-1][j]:0,j>0?f[i][j-1]:0);
                }
            }
        }
        int rightDown = f[m-1][n-1];
        /**恢复路径*/
        for(int i = 0;i<m;i++)
            Arrays.fill(f[i],0);
        return rightDown+f[0][0];
    }

    /**
     * 规划兼职工作
     * n份兼职工作 每份工作从startTime[i]开始到endTime[i] 报酬为profit[i]
     * 使用treeMap进行稀疏存储 而非密集存储
     *
     * 不使用treeMap来存储 使用数组+二分搜索也可以
     * */
    public static int jobScheduling(int[] startTime,int[] endTime,int[] profit){
        int res = 0;
        wrapProfit[] wrapProfits = new wrapProfit[startTime.length];
        for(int i = 0;i<startTime.length;i++){
            wrapProfits[i] = new wrapProfit(startTime[i],endTime[i],profit[i]);
        }
        Arrays.sort(wrapProfits);
        TreeMap<Integer,Integer> recordMap = new TreeMap<>();
        recordMap.put(0,0);
        for(wrapProfit wrapProfit : wrapProfits){
            int sTime = wrapProfit.getsTime(), eTime = wrapProfit.geteTime(), pfit = wrapProfit.getProfit();
            int originValue = recordMap.floorEntry(sTime).getValue();
            recordMap.put(eTime,Math.max(originValue+pfit,recordMap.floorEntry(eTime).getValue()));
            res = Math.max(res,recordMap.get(eTime));

        }
        return res;
    }

    /**
     * 完成所有任务的最少时间
     * 给你一个二维整数数组tasks 表示第i个任务需要在
     * 闭区间时间段[start_i,end_i,duration_i]个整数时间点
     * */
    public static int findMinimumTime(int[][] tasks){
        int cnt = 0;
        Arrays.sort(tasks, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1]-arr2[1];
            }
        });
        int startTime = 0;
        int endTime = 0;
        for(int[] task : tasks){
            /**
             * t[0] 开始时间
             * t[1] 结束时间
             * t[2] 任务时间
             * */
            if(task[0]>endTime) {
                cnt += task[2];
                endTime = task[1];
            }
            else{
                /**此时出现了重叠情况*/
                int intersecTime = Math.min(endTime-task[0]+1,task[2]);
                cnt = Math.max(cnt,intersecTime);
                endTime = task[1];
                cnt += Math.max(0,task[2]-intersecTime);
            }
        }
        return cnt;
    }

    /**
     * 面试：模式匹配
     * https://leetcode.cn/problems/pattern-matching-lcci/description/
     * */
    public boolean patternMatching(String pattern,String value){
        return false;
    }

}
class wrapProfit implements Comparable<wrapProfit>{
    private int sTime;
    private int eTime;
    private int profit;

    public wrapProfit(int sTime,int eTime,int profit){
        this.sTime = sTime;
        this.eTime = eTime;
        this.profit = profit;
    }

    public int getsTime(){
        return this.sTime;
    }
    public int geteTime(){
        return this.eTime;
    }

    public int getProfit(){
        return this.profit;
    }

    /**重写排序逻辑*/
    @Override
    public int compareTo(wrapProfit wrapProfit) {
        if(this.geteTime() == wrapProfit.geteTime()){
            return this.getsTime() - wrapProfit.getsTime();
        }
        return this.geteTime() - wrapProfit.geteTime();
    }
}
