import java.util.*;

public class GeneAlgo {
	private static final double mutateRate=0.2;
	private static final double eliminateRate=0.4;
	private static int poolsize=0;
	private static final boolean elite=true;
	public static SensorTour crossover(SensorTour p1, SensorTour p2){
		int size=p1.getSize();
		SensorTour child=new SensorTour(size);
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
	private static SensorTour mutation(SensorTour child){
		for(int position=0;position<child.getSize();position++){
			double d=Math.random();
			if(d<mutateRate){
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
		return child;
	}
	private static Uav mutation(Uav child){
		for(int position=0;position<child.uavArray.length;position++){
			double d=Math.random();
			if(d<mutateRate){
				int position1=(int)(child.uavArray.length*Math.random());
				while(position1==position){
					position1=(int)(child.uavArray.length*Math.random());
				}
				int temp=child.uavArray[position];
				child.uavArray[position]=child.uavArray[position1];
				child.uavArray[position1]=temp;
			}
		}
		return child;
	}
	private static SensorTour optmutation(SensorTour child){
		int next=0,next1=0;
		for(int position=0;position<child.getSize();position++){
			double d=Math.random();
			if(d<mutateRate){
				int position1=(int)(child.getSize()*Math.random());
				//while(position1==position){
					//position1=(int)(child.getSize()*Math.random());
				//}
				if(position1==position){
					position1=(int)(child.getSize()*Math.random());
				}
				if(position==position1)
					break;
				if(position>position1){
					int temp=position;
					position=position1;
					position1=temp;
				}
				Sensor sensor1=child.getSensor(position); 
				next=position+1;
				Sensor sensor2=child.getSensor(next);
				Sensor sensor3=child.getSensor(position1);
				if(position1+1==child.getSize()){
					next1=0;
				}
				else 
					next1=position1+1;
				Sensor sensor4=child.getSensor(next1);
				if(sensor1.getDis(sensor2)+sensor3.getDis(sensor4)>sensor1.getDis(sensor3)+sensor2.getDis(sensor4)){
					child.setSensor(next, sensor3);
					child.setSensor(position1, sensor2);
					int temp1=0;
					ArrayList<Sensor> templist=new ArrayList<Sensor>();
					for(int k=position1-1;k>next;k--){
						templist.add(child.getSensor(k));
					}
					for(int i=next+1;i<position1;i++){
						child.setSensor(i, templist.get(temp1));
						temp1++;
					}
				}
			}
		}
		return child;
	}
	
	private static Chromosome selection(Population old){
		poolsize=(int)(old.cArray[0].st.getSize()*eliminateRate);
		Population mate=new Population(poolsize);
		for(int i=0;i<poolsize;i++){
			int randomid=(int)(Math.random()*old.cArray.length);
			mate.setChromosome(i, old.getChromosome(randomid));
		}
		Chromosome fittest=mate.getFittest();
		return fittest;
	}
	
	public static Population nextGene(Population old){
		Population next=new Population(old.cArray.length);
		int eliteOffset=0;
		if(elite){
			next.setChromosome(0, old.getFittest());
			eliteOffset=1;
		}
		for(int i=eliteOffset;i<next.cArray.length;i++){
			Chromosome p1=selection(old);
			Chromosome p2=selection(old);
			SensorTour child=crossover(p1.st,p2.st);
			double or=Math.random();
			Chromosome p3;
			if(or>0.5){
				p3=new Chromosome(child,p1.uav);
			}
			else
				p3=new Chromosome(child,p2.uav);
			next.setChromosome(i, p3);
		}
		for(int i=1;i<next.cArray.length;i++){
			next.cArray[i].st=mutation(next.cArray[i].st);
			next.cArray[i].uav=mutation(next.cArray[i].uav);
		}
		next.getFittest().st=optmutation(next.getFittest().st);
		return next;
	}
}
