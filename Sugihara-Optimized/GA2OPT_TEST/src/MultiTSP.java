import java.util.*;

public class MultiTSP {
	public static ArrayList<Sensor> sensors=new ArrayList<Sensor>();
	int sensorSize=0;
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
		return size;
	}
	public int getUavSize(){ 
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the size of the Uavs which should be less than "+(int)(sensorSize/3.0)+":");
		int size=sc.nextInt();
		return size;
	}
	public void getSensors(){
		int size=getSensorSize();
		this.sensorSize=size;
		sensors.clear();
		for(int i=size;i<size+size;i++){
			Sensor se=new Sensor(i);
			//System.out.println(se);
			sensors.add(se);
		}
	}	
	public Chromosome evolve(){
		int size1=getUavSize();
		int size=getGeneSize(); 
		Population old=new Population(size,sensors,size1);
		System.out.println("Fittest: "+old.getFittest()+". \nInitial distance: "+(int)old.getFittest().getDis());
		System.out.println("Evolving...");
		for(int i=0;i<100;i++){
			old=GeneAlgo.nextGene(old);
		}
		System.out.println("Finished");
		System.out.println("Solution:");
		System.out.println(old.getFittest());
		System.out.println("Distance:"+(int)old.getFittest().getDis());
		return old.getFittest();
	}
	/*public static void main(String[] args){
		MultiTSP bt=new MultiTSP();
		bt.getSensors();
		bt.evolve();
	}*/
}
