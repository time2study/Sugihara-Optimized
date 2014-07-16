package Path_Planning;
import java.util.*;
public class PathPlanning {
	double INF=(double)Integer.MAX_VALUE;
	Map<Integer,ArrayList<Edge>> graph;
	int sensorSize=0;
	double[][] weightMatrix;
	double[] shortpath;
	String[] path;
	Tour tsp;
	ArrayList slist=new ArrayList<Sensor>();
	public PathPlanning(Map<Integer,ArrayList<Edge>> graph,Tour tsp){
		this.graph=graph;
		this.sensorSize=graph.size()+1;
		weightMatrix=new double[sensorSize][sensorSize];
		shortpath=new double[sensorSize];
		path=new String[sensorSize];
		this.tsp=(Tour) tsp.clone();
	}
	public boolean isContained(int start, int end){
		boolean flag=true;
		for(int i=start;i<=end;i++){
			if(start-end==0)
				break;
			if(!graph.get(start).get(end-start-1).label.contains(i)){
				flag=false;
				break;
			}
		}
		return flag;
	}
	public void constructMatrix(){
		for(int i=0;i<sensorSize;i++)
			for(int j=0;j<sensorSize;j++){
				if(i>=j)
					weightMatrix[i][j]=INF;
				else
					weightMatrix[i][j]=graph.get(i).get(j-i-1).getWeight();
			}
			
	}
	public void Dijkstra(double[][] weight,int start){
		int[] visited=new int[sensorSize];
		shortpath[start]=0;
		visited[start]=1;
		for(int i=0;i<sensorSize;i++)  
            path[i]=new String(start+"-->"+i);  
		for(int count=1;count<sensorSize;count++){
			int k=-1;
			double dmin=(double)Integer.MAX_VALUE;
			for(int i=0;i<sensorSize;i++){
				if(visited[i]==0&&isContained(start,i)){
					if(weight[start][i]<dmin){
						dmin=weight[start][i];
						k=i;
					}
				}
			}
			if(k==-1)
				break;
			shortpath[k]=dmin;
			visited[k]=1;
			for(int i=k+1;i<sensorSize;i++){
				if(visited[i]==0){
					if((!isContained(start,i))&&isContained(k,i)){
						weight[start][i]=weight[start][k]+weight[k][i];
						for(int p=start;p<=i;p++){
							if(!graph.get(start).get(i-1).label.contains(p))
								graph.get(start).get(i-1).label.add(p);
						}
						path[i]=path[k]+"-->"+i;
					}
				}
			}
		}
	}
	public void display(){
		for(int i=0;i<slist.size()-1;i++)
			System.out.print(slist.get(i)+"-->");
		System.out.println(slist.get(slist.size()-1));
		System.out.println("Distance:"+shortpath[sensorSize-1]);
	}
	
	public void getSensor(){
		String[] sid=path[sensorSize-1].split("-->");
		for(int i=0;i<sid.length;i++){
			//System.out.println(sid[i]);
			slist.add(i, tsp.getSensor(Integer.parseInt(sid[i])));
		}
		//System.out.println(sid.length);
	}
}
