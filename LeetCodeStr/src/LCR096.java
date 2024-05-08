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
}
