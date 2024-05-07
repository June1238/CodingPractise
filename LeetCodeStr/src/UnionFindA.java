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
