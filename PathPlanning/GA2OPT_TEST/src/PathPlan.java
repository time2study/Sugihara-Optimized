import java.util.ArrayList;
import java.util.Map;

public class PathPlan {
	double INF=(double)Integer.MAX_VALUE;
	Map<Integer,ArrayList<Edge>> graph;
	int sensorSize=0;
	double[][] weightMatrix;
	double[] shortpath;
	String[] path;
	SensorTour tsp;
	ArrayList slist=new ArrayList<Sensor>();
	ArrayList vi=new ArrayList();
	//ArrayList olist=new ArrayList<Sensor>();
	public PathPlan(Map<Integer,ArrayList<Edge>> graph,SensorTour tsp){
		this.graph=graph;
		this.sensorSize=graph.size()+1;
		weightMatrix=new double[sensorSize][sensorSize];
		shortpath=new double[sensorSize];
		path=new String[sensorSize];
		this.tsp=(SensorTour) tsp.clone();
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
				if(visited[i]==0&&isContained(k,i)){
					if(!isContained(start,i)){
						weight[start][i]=weight[start][k]+weight[k][i];
						for(int p=start;p<=i;p++){
							if(!graph.get(start).get(i-1).label.contains(p))
								graph.get(start).get(i-1).label.add(p);
						}
						path[i]=path[k]+"-->"+i;
					}
					else{
						if(weight[start][i]>weight[start][k]+weight[k][i]){
							weight[start][i]=weight[start][k]+weight[k][i];
							path[i]=path[k]+"-->"+i;
						}
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
	
	public double getDistance(){
		return shortpath[sensorSize-1];
	}
	
	public void getSensor(){
		String[] sid=path[sensorSize-1].split("-->");
		for(int i=0;i<sid.length-1;i++){
			slist.add(tsp.getSensor(Integer.parseInt(sid[i])));
			vi.add(Integer.parseInt(sid[i]));
			
			int si=Integer.parseInt(sid[i+1]);
			int sii=Integer.parseInt(sid[i]);
			//System.out.println(sii);
			
			for(int j=0;j<graph.get(sii).size();j++){
				int end=graph.get(sii).get(j).getEnd();
				if(si==end){
					//System.out.println("start"+sii+"end"+end+"label"+graph.get(sii).get(j).label.toString());
					if(graph.get(sii).get(j).label.size()>=3){
						for(int k=1;k<graph.get(sii).get(j).label.size()-1;k++){
							if(!vi.contains(graph.get(sii).get(j).label.get(k))){
								vi.add(graph.get(sii).get(j).label.get(k));
								Sensor temp=tsp.getSensor(Integer.parseInt(graph.get(sii).get(j).label.get(k).toString()));
								temp=getPoint(tsp.getSensor(Integer.parseInt(sid[i])),tsp.getSensor(Integer.parseInt(sid[i+1])),temp);
								//System.out.println(temp);
								slist.add(temp);
							}
						}
					}
				}
			}
		}
		slist.add(tsp.getSensor(Integer.parseInt(sid[sid.length-1])));
	}
	public Sensor getPoint(Sensor s1, Sensor s2, Sensor s3){
		Sensor s4;
		double denom=((s2.getX()-s1.getX())*(s2.getX()-s1.getX())+(s2.getY()-s1.getY())*(s2.getY()-s1.getY())+(s2.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()));
		double t=((s3.getX()-s1.getX())*(s2.getX()-s1.getX())+(s3.getY()-s1.getY())*(s2.getY()-s1.getY())+(s3.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()))/denom;
		int x=(int)(t*(s2.getX()-s1.getX())+s1.getX());
		int y=(int)(t*(s2.getY()-s1.getY())+s1.getY());
		int z=(int)(t*(s2.getZ()-s1.getZ())+s1.getZ());
		s4=new Sensor(x,y,z);
		return s4;
	}
}
