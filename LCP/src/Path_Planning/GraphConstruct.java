package Path_Planning;
import java.util.*;

public class GraphConstruct {
	Tour tsp;
	//double INF=(double)Integer.MAX_VALUE;
	//double[][] matrix;
	Map<Integer,ArrayList<Edge>> emap=new HashMap<Integer,ArrayList<Edge>>();
	ArrayList lb=new ArrayList<Sensor>();
	ArrayList elist=new ArrayList<Edge>();
	public GraphConstruct(Tour tsp){
		this.tsp=(Tour)tsp.clone();
		//int size=tsp.getSize();
		/*
		 * matrix=new double[size][size];
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				matrix[i][j]=INF;
		*/
	}
	public int setRange(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the communication range:");
		int range=sc.nextInt();
		sc.close();
		return range;
	}
	public double pointToSeg(Sensor ss1, Sensor ss2,Sensor ss3){
		double line1=ss1.getDis(ss2);
		double line2=ss1.getDis(ss3);
		double line3=ss2.getDis(ss3);
		double disSeg=0;
		if(line1+line2==line3)
			disSeg=0;
		else if(line1*line1>=line2*line2+line3*line3)
			disSeg=line2;
		else if(line2*line2>=line1*line1+line3*line3)
			disSeg=line1;
		else{
			double p=(line1+line2+line3)/2;
			double area=Math.sqrt(p*(p-line3)*(p-line2)*(p-line1));
			disSeg=2*area/line3;
		}
		return disSeg;
	}
	
	public void shortCut(){
		int size=tsp.getSize();
		int range=setRange();
		for(int i=0;i<size-1;i++){
			for(int j=i+1;j<size;j++){
				double lengthSeg=tsp.getSensor(i).getDis(tsp.getSensor(j));
				for(int k=i;k<=j;k++){
					double disSeg=pointToSeg(tsp.getSensor(k),tsp.getSensor(i),tsp.getSensor(j));
					if(disSeg<range){
						lb.add(k);
					}
				}
				Edge edge=new Edge(i,j,lengthSeg,(ArrayList<Edge>)lb.clone());
				lb.clear();
				elist.add(edge);
			}
			emap.put(i, (ArrayList<Edge>) elist.clone());
			elist.clear();
		}
	}
	
	public Map<Integer,ArrayList<Edge>> getGraph(){
		return this.emap;
	}
	
	public Tour getSensors(){
		return this.tsp;
	}
	/*public static void main(String[] args){
		TspTour bt=new TspTour();
		bt.getSensors();
		GraphConstruct pp=new GraphConstruct(bt.evolve());
		pp.shortCut();
		int size=pp.tsp.getSize();
		Set<Map.Entry<Integer, ArrayList<Edge>>> path=pp.emap.entrySet();
		for(Map.Entry<Integer, ArrayList<Edge>> p:path){
			for(int i=0;i<size-p.getKey()-1;i++)
				System.out.println("start:"+p.getKey()+", label:"+p.getValue().get(i).label+", end:"+p.getValue().get(i).getEnd()+", weight:"+p.getValue().get(i).getWeight());
		}
	}*/
}
