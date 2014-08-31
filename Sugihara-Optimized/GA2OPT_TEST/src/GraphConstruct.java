import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GraphConstruct {
	SensorTour tsp;
	Map<Integer,ArrayList<Edge>> emap=new HashMap<Integer,ArrayList<Edge>>();
	ArrayList lb=new ArrayList<Sensor>();
	ArrayList elist=new ArrayList<Edge>();
	public GraphConstruct(SensorTour tsp){
		this.tsp=(SensorTour)tsp.clone();
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
	
	public void shortCut(int range1){
		int size=tsp.getSize();
		int range=range1;
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
	
	public SensorTour getSensors(){
		return this.tsp;
	}
}
