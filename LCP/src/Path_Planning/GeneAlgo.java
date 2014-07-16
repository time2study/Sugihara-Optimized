package Path_Planning;
import java.util.*;

public class GeneAlgo {
	private static final double mutationRate=0.015;
	private static final int poolsize=4;
	private static final boolean elite=true;
	public static Tour crossover(Tour p1, Tour p2){
		int size=p1.getSize();
		Tour child=new Tour(size);
		int begin=(int)(Math.random()*size);
		int end=(int)(Math.random()*size);
		while(begin==end){
			begin=(int)(Math.random()*size);
			end=(int)(Math.random()*size);
		}
		//System.out.println("sz:"+size+"begin"+begin+"end"+end);
		for(int i=0;i<size;i++){
			if(begin<end){
				if((i>=begin) && (i<=end)){
					child.setSensor(i, p1.getSensor(i));
				}
			}
			else if(begin>end){
				if((i>=begin)||(end>=i)){
					child.setSensor(i, p1.getSensor(i));
				}
			}
		}
		for(int i=0;i<p2.getSize();i++){
			if(!child.containsSensor(p2.getSensor(i))){
				for(int j=0;j<child.getSize();j++){
					if(child.getSensor(j)==null){
						child.setSensor(j, p2.getSensor(i));
						break;
					}
				}
			}
		}
		return child;
	}
	private static void mutation(Tour child){
		for(int position=0;position<child.getSize();position++){
			double d=Math.random();
			if(d<mutationRate){
				//System.out.println("yes"+d);
				int position1=(int)(child.getSize()*Math.random());
				while(position1==position){
					position1=(int)(child.getSize()*Math.random());
				}
				Sensor sensor1=child.getSensor(position);
				Sensor sensor2=child.getSensor(position1);
				child.setSensor(position, sensor2);
				child.setSensor(position1, sensor1);
				//System.out.println("mu"+child);
			}
		}
	}
	private static Tour selection(Generation old){
		Generation mate=new Generation(poolsize);
		for(int i=0;i<poolsize;i++){
			int randomid=(int)(Math.random()*old.tours.length);
			mate.setTour(i, old.getTour(randomid));
			//System.out.println(mate.getTour(i));
		}
		Tour fittest=mate.getFittest();
		return fittest;
	}
	public static Generation nextGene(Generation old){
		Generation next=new Generation(old.tours.length);
		int eliteOffset=0;
		if(elite){
			next.setTour(0, old.getFittest());
			//System.out.println(next.getTour(0));
			eliteOffset=1;
		}
		for(int i=eliteOffset;i<next.tours.length;i++){
			//System.out.println(old.getTour(i));
			Tour p1=selection(old);
			//System.out.println("parent1:"+p1);
			Tour p2=selection(old);
			//System.out.println("parent2:"+p2);
			Tour child=crossover(p1,p2);
			//System.out.println("child:"+child);
			next.setTour(i, child);
		}
		for(int i=0;i<next.tours.length;i++){
			//System.out.println("next for m"+next.getTour(i));
			mutation(next.getTour(i));
		}
		return next;
	}
}
