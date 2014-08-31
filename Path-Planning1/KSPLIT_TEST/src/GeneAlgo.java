import java.util.*;

public class GeneAlgo {
	private static final double mutationRate=0.2;
    private static final double eliminateRate=0.4;
	private static int poolsize=0;
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
				int position1=(int)(child.getSize()*Math.random());
				while(position1==position){
					position1=(int)(child.getSize()*Math.random());
				}
				Sensor sensor1=child.getSensor(position);
				Sensor sensor2=child.getSensor(position1);
				child.setSensor(position, sensor2);
				child.setSensor(position1, sensor1);
			}
		}
	}
	private static Tour selection(Generation old){
	    poolsize=(int)(old.tours[0].getSize()*eliminateRate);
		Generation mate=new Generation(poolsize);
		for(int i=0;i<poolsize;i++){
			int randomid=(int)(Math.random()*old.tours.length);
			mate.setTour(i, old.getTour(randomid));
		}
		Tour fittest=mate.getFittest();
		return fittest;
	}
	public static Generation nextGene(Generation old){
		Generation next=new Generation(old.tours.length);
		int eliteOffset=0;
		if(elite){
			next.setTour(0, old.getFittest());
			eliteOffset=1;
		}
		for(int i=eliteOffset;i<next.tours.length;i++){
			Tour p1=selection(old);
			Tour p2=selection(old);
			Tour child=crossover(p1,p2);
			next.setTour(i, child);
		}
		for(int i=1;i<next.tours.length;i++){
			mutation(next.getTour(i));
		}
		return next;
	}
}
