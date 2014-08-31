import java.util.*;

public class SensorTour implements Cloneable{
	ArrayList tour=new ArrayList<Sensor>();
	private double fitness=0;
	private int distance=0;
	private int size;
	public Object clone(){
		SensorTour tr=null;
		try{
			tr=(SensorTour)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return tr;
	}
	public SensorTour(int size){
		this.size=size;
		for(int i=0;i<size;i++){
			tour.add(null);
		}
	}
	public SensorTour(ArrayList tour){
		for(int i=0;i<tour.size();i++)
			this.tour.add(i,tour.get(i));
	}
	public int getSize(){
		return tour.size();
	}
	public void setSize(int size){
		this.size=size;
	}
	public Sensor getSensor(int id){
		return (Sensor)tour.get(id);
	}
	public void setSensor(int id, Sensor sensor){
		tour.set(id, sensor);
		fitness=0;
		distance=0;
	}
	public double getDis(){
		double tourDis=0;
		if(distance==0){
			Sensor SEnd=new Sensor(0,0,1);
			Sensor last=SEnd;
			Sensor next;
			for(int id=0;id<tour.size();id++){
				next=getSensor(id);
				tourDis+=last.getDis(next);
				last=next;
			}
			next=SEnd;
			tourDis+=last.getDis(next);
		}
		//System.out.println(tourDis);
		return tourDis;
	}
	public double getFit(){
		if(fitness==0){
			fitness=1/(double)getDis();
		}
		return fitness;
	}
	public boolean containsSensor(Sensor sensor){
		return tour.contains(sensor);
	}
	/*public String toString(){
		String so="";
		for(int i=0;i<tour.size();i++){
			so+="->"+getSensor(i);
		}
		return so;
	}*/
}