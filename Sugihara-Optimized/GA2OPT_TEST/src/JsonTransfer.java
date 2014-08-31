

import java.io.*;
import java.util.*;

public class JsonTransfer {
	public static void write(String path, String s1){
		try{
			File f=new File(path);
			if(f.exists()){
				f.delete();
			}
			if(!f.createNewFile())
				System.out.println("Failure...");
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
		       output.write(s1);
		       output.close();
		}catch(Exception e){
			System.out.println("Error...");
			e.printStackTrace();
		}
	}
	public static int setRange(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the communication range:");
		int range=sc.nextInt();
		sc.close();
		return range;
	}
	public static void writeCSV(String di){
		try { 
		      File f = new File("data.csv"); 
		      if(f.exists()){
			  }
		      else if(!f.createNewFile())
				System.out.println("Failure...");
		      BufferedWriter bw = new BufferedWriter(new FileWriter(f, true)); // ¸½¼Ó 
		      bw.write(di); 
		      bw.newLine(); 
		      bw.close(); 

		    } catch (FileNotFoundException e) { 
		      e.printStackTrace(); 
		    } catch (IOException e) { 
		      e.printStackTrace(); 
		    } 
	} 
	public static void main(String[] args){
		//for(int m=10; m<=25;m=m+5){
		//for(int n=1; n<=10;n++){
		//for(int o=1;o<=10;o++){
		String dst="";
		MultiTSP bt=new MultiTSP();
		bt.getSensors();
		Chromosome ch=bt.evolve();
		int k=0,temp=0;
		double distance=0;
		int range=JsonTransfer.setRange();
		Sensor s=new Sensor(0,0,1);
		String allSensor="";
		ArrayList<Sensor> all=new ArrayList<Sensor>();
		//all.add(s);
		for(int i=0;i<ch.st.tour.size();i++){
			all.add((Sensor)ch.st.tour.get(i));
		}
		allSensor=JsonTools.createJsonString("sensors", all);
		JsonTransfer.write("sensors.json",allSensor);
		double dis=0;
		Map<String,ArrayList<Sensor>> path=new HashMap<String,ArrayList<Sensor>>();
		Map<String,ArrayList<Sensor>> opath=new HashMap<String,ArrayList<Sensor>>();
		for(int i=0;i<ch.uav.numberofUav;i++){
			ArrayList<Sensor> sublist=new ArrayList<Sensor>();
			sublist.add(s);
			for(;k<ch.uav.uavArray[i]+temp;k++){
				sublist.add((Sensor)ch.st.tour.get(k));
			}
			SensorTour subtour=new SensorTour(sublist);
			temp=k;
			GraphConstruct pp=new GraphConstruct(subtour);
			pp.shortCut(range);
			int size=pp.tsp.getSize();
			PathPlan p=new PathPlan(pp.emap,pp.tsp);
			p.constructMatrix();
			p.Dijkstra(p.weightMatrix, 0);
			p.getSensor();
			//p.display();
			SensorTour subtour1=new SensorTour(p.slist);
			subtour1.tour.remove(0);
			dis+=subtour1.getDis();
			//String path="";
			p.slist.add(s);
			path.put(i+"path", p.slist);
			sublist.add(s);
			opath.put(i+"path", sublist);
		}
		//dst+=range+","+((int)ch.getDis()-(int)dis)+","+1+",";
		//dst+=all.size()+","+(int)dis+","+1+",";
		//JsonTransfer.writeCSV(dst);
		//String uav=JsonTools.createJsonString("uav", ch.uav.uavArray);
		//JsonTransfer.write("uav.json", uav);
		String plist="";
		String olist="";
		plist=JsonTools.createJsonString("UAVs", path.values());
		JsonTransfer.write("UAVs.json", plist);
		olist=JsonTools.createJsonString("UAVs", opath.values());
		JsonTransfer.write("OUAVs.json", olist);
		System.out.println("total distance: "+(int)dis);
	}
}
