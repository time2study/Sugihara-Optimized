package Path_Planning;
import java.util.*;

public class Generation {
	Tour[] tours;
    /**
     * @param size: the number of the tours
     * @param sensorlist: the list of the sensors
     */
	public Generation(int size){
		tours=new Tour[size];
	}
	public Generation(int size,ArrayList sensorlist){
		tours=new Tour[size];
		for(int i=0;i<size;i++){
			Collections.shuffle(sensorlist);
			tours[i]=new Tour(sensorlist);
			//System.out.println("#"+tours[i]);
		}
	}
	public Tour getTour(int id){
		return tours[id];
	}
	public void setTour(int id,Tour tour){
		tours[id]=(Tour)tour.clone();
	}
	public Tour getFittest(){
		Tour fittest=tours[0];
		for(int i=1;i<tours.length;i++){
			if(fittest.getFit()<=getTour(i).getFit()){
				fittest=getTour(i);
			}
		}
		return fittest;
	}
}
