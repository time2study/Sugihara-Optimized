
import java.util.*;

public class Edge {
	private int start;
	private int end;
	private double weight;
	ArrayList label=new ArrayList<Sensor>();
	public Edge(int start,int end,double weight,ArrayList label){
		this.start=start;
		this.end=end;
		this.weight=weight;
		this.label=label;
	}
	public int getStart(){
		return this.start;
	}
	public int getEnd(){
		return this.end;
	}
	public void setStart(int start){
		this.start=start;
	}
	public void setEnd(int end){
		this.end=end;
	}
	public double getWeight(){
		return this.weight;
	}
	public void setWeight(double weight){
		this.weight=weight;
	}
}
