import java.util.*;
public class Uav {
	int[] uavArray;
	int sensorSize;
	int numberofUav;
	int stops=0;
	double distance=0;
	public Uav(int sensorSize,int numberofUav){
		this.sensorSize=sensorSize;
		this.numberofUav=numberofUav;
		uavArray=new int[numberofUav];
	}
	public void setUavArray(int[] uavArray1){
		for(int i=0;i<numberofUav;i++){
			uavArray[i]=uavArray1[i];
		}
	}
	public void setUavArray(){
		for(int i=0;i<uavArray.length;i++){
			uavArray[i]=3;
		}
		sensorSize=sensorSize-numberofUav*3;
		for(int i=0;i<numberofUav-1;i++){
			stops=(int)(Math.random()*(sensorSize+1));
			uavArray[i]=stops+uavArray[i];
			sensorSize=sensorSize-stops;
		}
		if(sensorSize>0)
			uavArray[numberofUav-1]=sensorSize+uavArray[numberofUav-1];
	}
	/*public static void main(String[] args){
		Uav uav=new Uav(50,4);
		uav.setUavArray();
		for(int i=0;i<uav.numberofUav;i++)
			System.out.println(uav.uavArray[i]);
	}*/
}
