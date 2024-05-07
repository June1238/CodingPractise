import java.util.Arrays;

public class LCR096 {

    public static void main(String[] args){
        boolean var = isInterLeave("","","");
        System.out.println(var);
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
}

/**
 * 题解：同一个格子上的樱桃只能统计一次 需要考虑同一个格子重复经过的情况
 * 子问题定义为两个人都走了相同的步数 也就是p = q的情况 才能直接计算出重复经过的情况 -
 * 也就是两个人走到同一个格子上 可以得到的樱桃个数的最大值
 *
 *
 * */
class SolutionT{
    /**
     * 子问题是两个人都走了同样的步数 即p = q 才能直接
     * 计算出重复经过的清空 也就是两个人走到同一个格子上
     * */
    public int cherryPickup(int[][] grid){
        int n = grid.length;
        /**总步数 最重要到达的终点即x1 x2为(n-1,n-1)*/
        int[][][] memo = new int[n*2-1][n][n];
        for(int[][] m: memo){
            for(int[] r: m){
                /**-1表示该位置没有被计算过*/
                Arrays.fill(r,-1);
            }
        }

        return Math.max(dfs(2*n-2,n-1,n-1,grid,memo),0);
    }
    /**入参条件：t是总步数 memo为记忆化数组 grid为路径地图*/
    private int dfs(int t,int j,int k,int[][] grid,int[][][] memo){
        /**越界情况 直接返回-1即可*/
        if(k<0||j<0||t<j||t<k||grid[t-j][j]<0||grid[t-k][k]<0)
            return Integer.MIN_VALUE;
        if(t==0)
            return grid[0][0];
        if(memo[t][j][k]!=-1)
            return memo[t][j][k];
        int res = Math.max(Math.max(dfs(t-1,j-1,k,grid,memo),dfs(t-1,j,k-1,grid,memo)),
                Math.max(dfs(t-1,j,k,grid,memo),dfs(t-1,j-1,k-1,grid,memo)))+grid[t-j][j]+j==k?0:grid[t-k][k];
        memo[t][j][k] = res;
        return res;
    }

    public int cherryPickupN(int[][] grid){
        int n = grid.length;
        int[][][] f = new int[n*2-1][n+1][n+1];
        for(int[][] m : f){
            for(int[] r : m){
                Arrays.fill(f,Integer.MIN_VALUE);
            }
        }
        f[0][1][1] = grid[0][0];
        for(int t = 1;t<n*2-1;t++){
            for(int j = Math.max(t-n+1,0);j<=Math.min(t,n-1);j++){
                if(grid[t-j][j]<0) continue;
                for(int k = j;k<=Math.min(t,n-1);k++){
                    if(grid[t-k][k]<0) continue;
                    f[t][j + 1][k + 1] = Math.max(Math.max(f[t - 1][j + 1][k + 1], f[t - 1][j + 1][k]), Math.max(f[t - 1][j][k + 1], f[t - 1][j][k])) +
                            grid[t - j][j] + (k != j ? grid[t - k][k] : 0);
                }
            }
        }

        return Math.max(0,f[n*2-2][n][n]);
    }
}