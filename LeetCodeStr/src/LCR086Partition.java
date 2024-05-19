import java.util.*;

public class LCR086Partition {

    public static void main(String[] args){
        SolutionA solutionA = new SolutionA();
        System.out.println(solutionA.getWinnerI(new int[]{2,1,3,5,4,6,7},2));
        System.out.println(solutionA.getWinnerI(new int[]{3,2,1},10));
        System.out.println(solutionA.getWinner(new int[]{1,9,8,2,3,7,6,4,5},7));
        System.out.println(solutionA.getWinner(new int[]{1,11,22,33,44,55,66,77,88,99},1000000000));
    }

    /**
     * LCR 086 : 分割回文字符串
     * 给定一个字符串s 将s分割成一些子串 使每个子串都是回文串 返回s所有可能的分割方案
     * */
    public void DFSP(char[] charArr,int start,int cur,int len){

    }
    public String[][] partition(String s){
        List<String> res = new ArrayList<>();
        char[] charArr = s.toCharArray();

        return (String[][]) res.stream().map(str->str).toArray();
    }

    public int minimumRounds(int[] tasks){
        HashMap<Integer,Integer> recordMap = new HashMap<>();
        for(int task:tasks){
            recordMap.put(task, recordMap.getOrDefault(task,0)+1);
        }
        int res = 0;
        for(Map.Entry<Integer,Integer> entry:recordMap.entrySet()){
            int value = entry.getValue();
            if(value==1) return -1;
            res += value/2;
        }
        return res;
    }

    /**直接贪心排序做*/
    public static int findMinimumTimeI(int[][] tasks){
        Arrays.sort(tasks,(a,b)->(a[1]-b[1]));
        int ans = 0;
        int mx = tasks[tasks.length - 1][1];
        boolean[] run = new boolean[mx+2];
        /**直接贪心--*/
        for(int[] task:tasks){
            int startTime = task[0];
            int endTime = task[1];
            int cost = task[2];
            for(int i = startTime;i<=endTime;i++){
                if(run[i]==true) cost--;
            }
            ans += cost;
            while(cost>0){
                if(!run[endTime]) {
                    run[endTime] = true;
                    cost --;
                    endTime--;
                }
            }
        }
        return ans;
    }
}

/**新的方法解决问题*/
class SolutionA{

    /**
     * 最终要解决的任务都是尾缀区间的更新 和方法一暴力便利每个时间点相比
     * 将已占用的时间点合并成了区间 考虑到时间的单调性 可以利用二分O(logN)的找到
     * [start,end]范围内哪些区间已经被占用
     * */
    public int findMinimumTime(int[][] tasks){
        Arrays.sort(tasks,(a,b)->(a[1]-b[1]));
        // 栈中保存区间的左右端点 栈顶到栈底的区间长度的和
        List<int[]> st = new ArrayList<>();
        //哨兵节点 保证不跟任何区间相交
        st.add(new int[]{-2,-2,0});
        for(int[] task:tasks){
            int start = task[0];
            int end = task[1];
            int cost = task[2];
            int[] e = st.get(lowBound(st,start) - 1);
            cost -= st.get(st.size()-1)[2]-e[2];
            if(start<=e[1]){
                cost -= (e[1]-start+1);
            }
            if(cost<=0)
                continue;
            while(end - st.get(st.size()-1)[1] <= cost){
                e = st.remove(st.size() -1 );
                cost += e[1]-e[0]+1;
            }
            st.add(new int[]{end - cost + 1, end, st.get(st.size() - 1)[2] + cost});
        }
        /**返回最后的值*/
        return st.get(st.size()-1)[2];
    }
    /**开区间二分 返回坐标索引index*/
    private int lowBound(List<int[]> st,int target){
        // 开区间(left,right)
        int left = -1;
        int right = st.size();
        while(left+1<right){
            /**区间不为空*/
            int mid = (left+right) >>> 1;
            if(st.get(mid)[0]<target){
                left = mid;
            }else {
                right = mid;
            }
        }
        return right;
    }

    /**1535 找出游戏中的赢家
     * 给出一个由不同整数组成的整数数组arr和一个整数k 每回合游戏都在数组中的
     * 前两个元素[arr[0]和arr[1]]之间进行 比较两者大小 较大的整数将会取
     * 得这一回合的胜利并保留在位置0 较小的整数移至数组末尾 当一个整数连续赢得
     * k个连续回合时 游戏结束 该整数就是比赛的赢家
     *
     * 链表+模拟
     * */
    public int getWinner(int[] arr,int k){
        int res = 0;
        if(arr.length==1) return arr[0];
        if(arr.length<=2) return Math.max(arr[0],arr[1]);
        LinkNode head = new LinkNode(arr[0]);
        LinkNode tail = new LinkNode(),ptr = head;
        for(int i = 0;i<arr.length - 1;i++){
            ptr.next = new LinkNode(arr[i+1]);
            ptr = ptr.next;
            tail = ptr;
        }
        /**arr中的整数各不相同*/
        int cur = 0;
        while(cur<k){
            LinkNode nPtr = head.next;
            if(head.getVal()>nPtr.getVal()){
                head.next = nPtr.next;
                tail.next = nPtr;
                nPtr.next = null;
                tail = nPtr;
            }else{
                tail.next = head;
                head.next = null;
                tail = head;
                head = nPtr;
            }
            if(head.getVal()==res){
                cur++;
            }
            else {
                res = head.getVal();
                cur = 1;
            }
        }
        return res;
    }

    /**找出数组游戏的赢家 给你一个有不同的整数的数组arr和一个整数k
     * 比较arr[0]和arr[1]的位置
     * */
    public int getWinnerI(int[] arr,int k){
        int maxNum = arr[0];
        for(int num : arr){ maxNum = Math.max(maxNum,num);}
        int cur = 0 , res = arr[0];
        for(int i = 1;i<arr.length;i++){
            if(arr[i] > res)
            {
                res = arr[i];
                cur = 1;
            }
            else {
                cur++;
            }
            if(cur==k) return res;
            if(arr[i] == maxNum) return arr[i];
        }
        return maxNum;
    }

}
class LinkNode{
    int val;
    LinkNode next;
    public LinkNode(){}
    public LinkNode(int val){
        this.val = val;
        this.next = null;
    }
    public LinkNode(int val,LinkNode next){
        this.val = val;
        this.next = next;
    }

    public int getVal(){return this.val;}
    public LinkNode getNext(){ return this.next;}
}
