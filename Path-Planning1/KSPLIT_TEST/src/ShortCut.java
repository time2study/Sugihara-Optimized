import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ShortCut {
	Map<Integer,ArrayList> visited=new HashMap<Integer,ArrayList>();
	Map<Integer,ArrayList<Replace>> omited=new HashMap<Integer,ArrayList<Replace>>();
	Map<Integer, ArrayList<Sensor>> smap=new HashMap<Integer, ArrayList<Sensor>>();
	Map<Integer, ArrayList<Sensor>> smap1=new HashMap<Integer, ArrayList<Sensor>>();
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
	public ShortCut(Map<Integer, ArrayList<Sensor>> smap){
		this.smap=smap;
		this.smap1=smap;
		Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=smap.entrySet();
		for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour){
			ArrayList vlist=new ArrayList();
			vlist.add(1);
			vlist.add(1);
			visited.put(p.getKey(), vlist);
			ArrayList<Replace> plist=new ArrayList<Replace>();
			omited.put(p.getKey(), plist);
		}
	}
	public double pointToSeg(Sensor ss1, Sensor ss2,Sensor ss3){
		double line1=ss1.getDis(ss2);
		double line2=ss1.getDis(ss3);
		double line3=ss2.getDis(ss3);
		double disSeg=0;
		if(line1+line2==line3)
			disSeg=0;
		else if(line1*line1>=line2*line2+line3*line3)
			disSeg=line2;
		else if(line2*line2>=line1*line1+line3*line3)
			disSeg=line1;
		else{
			double p=(line1+line2+line3)/2;
			double area=Math.sqrt(p*(p-line3)*(p-line2)*(p-line1));
			disSeg=2*area/line3;
		}
		return disSeg;
	}
	public int setRange(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the communication range:");
		int range=sc.nextInt();
		sc.close();
		return range;
	}
	public void shortcut(int range){
		boolean flag=true;
		while(flag){
			int count=0;
			Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=smap.entrySet();
			for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour){
				ArrayList<Sensor> temp=p.getValue();
				ArrayList vis=visited.get(p.getKey());
				ArrayList<Replace> ol=omited.get(p.getKey());
				int len=vis.size();
				if(vis.get(0).equals(1)){
					if(len<temp.size()){
						Sensor last=temp.get(len-2);
						Sensor vi=temp.get(len-1);
						Sensor next=temp.get(len);
						double disSeg=pointToSeg(vi,last,next);
						if(disSeg<range){
							Sensor tmp=getCPoint(last,next,vi);
							temp.remove(len-1);
							p.setValue(temp);
							Replace re=new Replace(len-1,tmp);
							ol.add(re);
							omited.put(p.getKey(), ol);
							break;
						}
						vis.add(1);
					}
					else if(len==temp.size()){
						Sensor last=temp.get(len-2);
						Sensor vi=temp.get(len-1);
						Sensor next=temp.get(0);
						double disSeg=pointToSeg(vi,last,next);
						if(disSeg<range){
							Sensor tmp=getCPoint(last,next,vi);
							temp.remove(len-1);
							p.setValue(temp);
							Replace re=new Replace(len-1,tmp);
							ol.add(re);
							omited.put(p.getKey(), ol);
							vis.remove(len-1);
						}
						vis.set(0,2);	
					}
					visited.put(p.getKey(), vis);
				}
				else
					count++;
			}
			if(count==smap.size())
				flag=false;
			else
				count=0;
		}
	}
	public void display(){
		Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=smap.entrySet();
		for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour)
			System.out.println("id:"+p.getKey()+", tour:"+p.getValue());
	}
	public Sensor getCPoint(Sensor s1, Sensor s2, Sensor s3){
		Sensor s4;
		double denom=((s2.getX()-s1.getX())*(s2.getX()-s1.getX())+(s2.getY()-s1.getY())*(s2.getY()-s1.getY())+(s2.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()));
		double t=((s3.getX()-s1.getX())*(s2.getX()-s1.getX())+(s3.getY()-s1.getY())*(s2.getY()-s1.getY())+(s3.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()))/denom;
		int x=(int)(t*(s2.getX()-s1.getX())+s1.getX());
		int y=(int)(t*(s2.getY()-s1.getY())+s1.getY());
		int z=(int)(t*(s2.getZ()-s1.getZ())+s1.getZ());
		s4=new Sensor(x,y,z);
		return s4;
	}
	public void finalize(){
		Sensor s=new Sensor(0,0,1);
		Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=smap.entrySet();
		for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour){
			ArrayList<Replace> rl=omited.get(p.getKey());
			ArrayList<Sensor> as=p.getValue();
			for(int i=rl.size()-1;i>-1;i--){
				as.add(rl.get(i).getId(), rl.get(i).getSensor());
			}
			as.add(s);
			p.setValue(as);
		}
	}
	public double getDist(){
		double sum=0;
		Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=smap.entrySet();
		for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour){
			Tour t1=new Tour(p.getValue());
			//System.out.println(t1.getDis());
			sum+=t1.getDis();
		}
		return sum;
	}
	
	public static void main(String[] args){
		//for(int m=10; m<=25;m=m+5){
		//for(int n=1; n<=10;n++){
		//for(int o=1;o<11;o++){
		String dst="";
		TspTour bt=new TspTour();
		bt.getSensors();
		int uav=bt.getUavSize();
		String allSensor="";
		ArrayList<Sensor> all=new ArrayList<Sensor>();
		//all.add(s);
		for(int i=0;i<bt.sensors.size();i++){
			all.add(bt.sensors.get(i));
		}
		allSensor=JsonTools.createJsonString("sensors", all);
		ShortCut.write("sensors.json",allSensor);
		Tour fit=bt.evolve();
		Ksplitour ks=new Ksplitour();
		ks.ksplit(fit, uav);
		ks.getKTour(fit);
		//ks.display();
		int d=(int)ks.getDis();
		//ks.sets();
		ShortCut sc=new ShortCut(ks.sp);
		int range=sc.setRange();
		sc.shortcut(range);
		//sc.display();
		//System.out.println("total distance: "+(int)sc.getDist());
		sc.finalize();
		//dst=range+","+Math.abs(d-(int)sc.getDist())+","+0+",";
		//dst=m+","+(int)sc.getDist()+","+0+",";
		//sc.display();
		//System.out.println("total distance: "+(int)sc.getDist());
		//ShortCut.writeCSV(dst);
		String plist="";
		String olist="";
		plist=JsonTools.createJsonString("UAVs", sc.smap.values());
		//allSensor=JsonTools.createJsonString("Sensors", all);
		ShortCut.write("UAVs.json", plist);
		olist=JsonTools.createJsonString("UAVs", ks.sp.values());
		ShortCut.write("OUAVs.json", olist);
	}
}
