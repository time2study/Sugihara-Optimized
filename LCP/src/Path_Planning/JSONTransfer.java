package Path_Planning;
import java.io.*;
public class JSONTransfer {
	
	public static void write(String path, String s1,String s2){
		try{
			File f=new File(path);
			if(f.exists()){
				f.delete();
			}
			if(!f.createNewFile())
				System.out.println("Failure...");
			String s=s1+"\n"+s2;
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
		       output.write(s);
		       output.close();
		}catch(Exception e){
			System.out.println("Error...");
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		TspTour bt=new TspTour();
		bt.getSensors();
		GraphConstruct pp=new GraphConstruct(bt.evolve());
		pp.shortCut();
		int size=pp.tsp.getSize();
		PathPlanning p=new PathPlanning(pp.emap,pp.tsp);
		p.constructMatrix();
		p.Dijkstra(p.weightMatrix, 0);
		p.getSensor();
		p.display();
		String allSensor="";
		allSensor=JsonTools.createJsonString("sensors",(pp.tsp).tour);
		String path="";
		path=JsonTools.createJsonString("path", p.slist);
		JSONTransfer.write("path.json", allSensor, path);
	}
}
