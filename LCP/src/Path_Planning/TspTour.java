package Path_Planning;
import java.util.*;

public class TspTour {
	public static ArrayList<Sensor> sensors=new ArrayList<Sensor>();
	public int getSensorSize(){ 
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the size of the sensors:");
		int size=sc.nextInt();
		return size;
	}
	public int getGeneSize(){ 
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the size of the tours in a generation:");
		int size=sc.nextInt();
		//sc.close();
		return size;
	}
	public void getSensors(){
		int size=getSensorSize();
		for(int i=0;i<size;i++){
			Sensor se=new Sensor();
			System.out.println(se);
			sensors.add(i, se);
		}
	}	
	public Tour evolve(){
		int size=getGeneSize(); 
		Generation old=new Generation(size,sensors);
		System.out.println("Fittest:"+old.getFittest()+". \nInitial distance: "+old.getFittest().getDis());
		System.out.println("Evolving...");
		//stop after 100 generations evolution
		for(int i=0;i<150;i++){
			old=GeneAlgo.nextGene(old);
			//System.out.println("Fittest:"+old.getFittest()+". Initial distance: "+old.getFittest().getDis());
			//System.out.println("=========================================================================================");
		}
		System.out.println("Finished");
		System.out.println("Solution:");
		System.out.println(old.getFittest());
		System.out.println("Distance:"+old.getFittest().getDis());
		return old.getFittest();
	}
	/*public static void main(String[] args){
		TspTour bt=new TspTour();
		bt.getSensors();
		bt.evolve();
	}*/
}
