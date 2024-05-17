import java.util.*;

/**
 * 练习线程池中future的用法
 * */
public class ApplyExample {
    // example
    public static void main(String[] args){
        System.out.println(maxProfitAssignment(new int[]{2,4,6,8,10},new int[]{10,20,30,40,50},new int[]{4,5,6,7}));
    }

    /** 可以工作的最大周数
     * 按照规则参与项目中的工作
     * 1- 每周 将会完成某个项目中的恰好一个阶段的任务 而且必须每周都工作
     * 2- 连续的两周中 不能参与并完成同一个项目中的两个阶段任务
     *
     * 一旦违反上述规则 将停止工作 在不违反上面的规则的情况下 可以最多工作
     * 多少周
     *
     * 类比 记忆
     * */
    public static long numberOfWeeks(int[] milestones){
        long res = 0,len = milestones.length;
        Arrays.sort(milestones);
        for(int i = 0;i<len;i++){
            if(i==len-1){
                if(res<milestones[i]) res = res*2+1;
                else res += milestones[i];
                break;
            }
            res += milestones[i];
        }
        return res;
    }

    /** 安排工作以达到最大收益
     * 1-每个工人最多只能安排一个工作 但是一个工作可以完成多次
     * 2-返回的是把工人分派到工作岗位后 ，我们可以获得的最大利润
     * */
    public static int maxProfitAssignment(int[] difficulty,int[] profit,int[] worker) {
        workUnit[] workUnits = new workUnit[difficulty.length];
        for(int i = 0;i<difficulty.length;i++){
            workUnits[i] = new workUnit(difficulty[i],profit[i]);
        }
        Arrays.sort(workUnits);
        TreeMap<Integer,Integer> recordMap = new TreeMap<>();
        recordMap.put(0,0);
        int maxProfit = 0;
        for(workUnit workUnit_ : workUnits){
            maxProfit = Math.max(maxProfit,workUnit_.getProfit());
            recordMap.put(workUnit_.getDifficulty(),maxProfit);
        }
        int res = 0;
        for(int worker_ : worker){
            res += recordMap.floorEntry(worker_).getValue();
        }
        return res;
    }
}
class workUnit implements Comparable<workUnit>{
    private int difficulty;
    private int profit;
    public workUnit(int difficulty,int profit){
        this.difficulty = difficulty;
        this.profit = profit;
    }

    public int getProfit(){
        return this.profit;
    }
    public int getDifficulty(){
        return this.difficulty;
    }
    public String toString(){
        return "difficulty : "+ this.difficulty+", profit : "+this.profit;
    }

    @Override
    public int compareTo(workUnit workUnit) {
        return this.difficulty - workUnit.getDifficulty();
    }
}


