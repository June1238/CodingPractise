import java.util.*;

class waterDot{
    int xIndex;
    int yIndex;
    int edgeLen;
    public waterDot(int xIndex,int yIndex,int edgeLen){
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.edgeLen = edgeLen;
    }
    public int getxIndex(){return this.xIndex;}
    public int getyIndex(){return this.yIndex;}
    public int getEdgeLen(){
        return this.edgeLen;
    }
}
public class UnionFindA {
    static int[] f;
    public static int findX(int x){
        if(f[x]==x)
            return x;
        return findX(f[x]);
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        f = new int[n];
        /**初始化每个雨滴所在的并查集 每个雨滴都是单独的集合*/
        for(int i = 0;i<n;i++){
            f[i] = i;
        }
        int[][] edges = new int[n][2];
        for(int i = 0;i<n;i++){
            edges[i][0] = scan.nextInt();
            edges[i][1] = scan.nextInt();
        }
        List<waterDot> waterList = new ArrayList<>();
        for(int i = 0;i<n;i++){
            for(int j = i+1;j<n;j++){
                /**得出两点之间的边长度*/
                int edge = Math.abs(edges[i][0]-edges[j][0])+Math.abs(edges[i][1]-edges[j][1]);
                waterList.add(new waterDot(i,j,edge));
            }
        }
        Collections.sort(waterList, new Comparator<waterDot>() {
            @Override
            public int compare(waterDot o1, waterDot o2) {
                return o1.getEdgeLen()-o2.getEdgeLen();
            }
        });
        int maxlen = 0;
        for(waterDot waterDot:waterList){
            int x = waterDot.getxIndex();
            int y = waterDot.getyIndex();
            if(findX(x)!=findX(y)){
                f[y] = x;
                maxlen = waterDot.getEdgeLen();
            }
        }
        /**返回边的长度*/
        System.out.println("最少需要的时间为 : "+(maxlen+1)/2);
    }
}
class FindUnionPractice{

    public static void main(String[] args){
        FindUnionPractice findUnionPractice = new FindUnionPractice();
        char[][] grid = new char[][]{{'1','1','0','0','0'},{'1','1','0','0','0'},
                {'0','0','1','0','0'},{'0','0','0','1','1'}};
        System.out.println(findUnionPractice.numIslands(grid));

    }

    //连通图的存储结构
    int[] f;
    /**岛屿数量:由1和0组成的二维网格 请计算网格中岛屿的数量
     * 岛屿总是被水包围 每座岛屿只能由水平方向和竖直方向上的相邻陆地连接形成*/
    public int findP(int x){
        if(f[x]==x) return x;
        return findP(f[x]);
    }
    public int numIslands(char[][] grid){
        int m = grid.length,n = grid[0].length;
        f = new int[m*n];
        /**初始化连通图的父节点*/
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                int curIndex = i*n+j;
                f[curIndex] = curIndex;
            }
        }
        int cnt = 0;
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                if(grid[i][j]=='1'){
                    cnt += 1;
                    int curIndex = i*n+j;
                    int leftIndex = i*n+j-1;
                    int upIndex = (i-1)*n+j;
                    /**打通连通图*/
                    if(i>0&&j>0&&grid[i][j-1]=='1'&&grid[i-1][j]=='1'){
                        int upF = findP(upIndex), leftF = findP(leftIndex);
                        /**两个父节点不相同*/
                        if(upF != leftF) {
                            f[leftF] = upF;
                            cnt -= 1;
                        }
                        cnt -= 1;
                        f[curIndex] = upF;
                    }else if(j>0&&grid[i][j-1]=='1'){
                        cnt -= 1;
                        f[curIndex] = leftIndex;
                    }
                    else if(i>0&&grid[i-1][j]=='1'){
                        cnt -= 1;
                        f[curIndex] = upIndex;
                    }
                 }
            }
        }
        /**返回岛屿数量*/
        return cnt;
    }


    /**
     * 情侣牵手
     * n对情侣 连续排列的2n个座位上 想要牵到对方的手 返回最少
     * 交换座位的次数 以便每对情侣可以并肩坐到一起 每次交换可选择任意两个人
     * 让他们站起来交换座位 最少交换座位的次数
     *
     * row = [0,2,1,3]
     * row = [3,2,0,1]
     * */
    public int minSwapCouples(int[] row){
        int cnt = 0;
        return cnt;
    }
}