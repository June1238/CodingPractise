import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        partitions("aabaa");
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
}