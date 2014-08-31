import java.util.*;
public class Tour implements Cloneable{
	ArrayList tour=new ArrayList<Sensor>();
	private double fit=0;
	private int distance=0;
	private int size;
	public Object clone(){
		Tour tr=null;
		try{
			tr=(Tour)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return tr;
	}
	public Tour(int size){
		this.size=size;
		for(int i=0;i<size;i++){
			tour.add(null);
		}
	}
	public Tour(ArrayList tour){
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
		fit=0;
		distance=0;
	}
	public double getDis(){
		double tourDis=0;
		if(distance==0){
			for(int id=0;id<tour.size();id++){
				Sensor last=getSensor(id);
				Sensor next;
				if(id+1<tour.size())
					next=getSensor(id+1);
				else
					next=getSensor(0);
				tourDis+=last.getDis(next);
			}
		}
		return tourDis;
	}
	public double getFit(){
		if(fit==0){
			fit=1/(double)getDis();
		}
		return fit;
	}
	public boolean containsSensor(Sensor sensor){
		return tour.contains(sensor);
	}
	public String toString(){
		String so="";
		for(int i=0;i<tour.size();i++){
			so+="->"+getSensor(i);
		}
		return so;
	}
}