import java.util.*;

public class Population {
	Chromosome[] cArray;
	public Population(int size){
		cArray=new Chromosome[size];
	}
	public Population(int size, ArrayList sensorlist,int numberofUav){
		cArray=new Chromosome[size];
		for(int i=0;i<size;i++){
			Collections.shuffle(sensorlist);
			cArray[i]=new Chromosome(sensorlist,numberofUav);
		}
	}
	public Chromosome getChromosome(int id){
		return cArray[id];
	}
	public void setChromosome(int id, Chromosome ch){
		cArray[id]=(Chromosome) ch.clone();
	}
	public Chromosome getFittest(){
		Chromosome fittest=cArray[0];  
		for(int i=1;i<cArray.length;i++){
			if(cArray[i].getDis()<fittest.getDis()){
				fittest=cArray[i];
			}
		}
		return fittest;
	}
}
