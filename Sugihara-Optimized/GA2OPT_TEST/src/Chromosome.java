import java.util.*;

public class Chromosome implements Cloneable{
	SensorTour st;
	Uav uav;
	public Object clone(){
		Chromosome ch=null;
		try{
			ch=(Chromosome)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return ch;
	}
	public Chromosome(SensorTour st1,Uav uav1){
		st=new SensorTour(st1.tour);
		uav=new Uav(st1.getSize(),uav1.numberofUav);
		uav.setUavArray(uav1.uavArray);
	}
	public Chromosome(ArrayList sensorlist, int numberofUav){
		st=new SensorTour(sensorlist);
		uav=new Uav(sensorlist.size(),numberofUav);
		uav.setUavArray();
	}
	public double getDis(){
		int k=0,temp=0;
		double distance=0;
		for(int i=0;i<uav.numberofUav;i++){
			ArrayList<Sensor> sublist=new ArrayList<Sensor>();
			for(;k<uav.uavArray[i]+temp;k++){
				sublist.add((Sensor)st.tour.get(k));
			}
			SensorTour subtour =new SensorTour(sublist);
			distance=subtour.getDis()+distance;
			temp=k;
		}
		return distance;
	}
	public String toString(){
		String so="";
		for(int i=0;i<st.tour.size();i++){
			so+="->"+st.tour.get(i);
		}
		for(int i=0;i<uav.uavArray.length;i++){
			so+="->"+uav.uavArray[i];
		}
		return so;
	}
}
