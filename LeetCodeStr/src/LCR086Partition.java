import java.util.*;

public class LCR086Partition {

    public static void main(String[] args){
        System.out.println(findMinimumTimeI(new int[][]{{10,18,2},{1,8,1},{10,20,8}}));
        System.out.println(findMinimumTimeI(new int[][]{{2,3,1},{4,5,1},{1,5,2}}));
        System.out.println(findMinimumTimeI(new int[][]{{1,3,2},{2,5,3},{5,6,2}}));
        System.out.println(findMinimumTimeI(new int[][]{{8,19,1},{3,20,1},{1,20,2},{6,13,3}}));
        System.out.println(findMinimumTimeI(new int[][]{{1,10,7},{4,11,1},{3,19,7},{10,15,2}}));
        System.out.println(findMinimumTimeI(new int[][]{{1,14,4},{4,20,3},{17,19,2},{1,16,3}}));
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
}

