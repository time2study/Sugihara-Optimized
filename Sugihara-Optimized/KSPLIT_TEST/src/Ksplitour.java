import java.util.*;
public class Ksplitour{
	Map<Integer,ArrayList<Sensor>> sp=new HashMap<Integer,ArrayList<Sensor>>();
        ArrayList<Integer> ilist=new ArrayList();
        
        Map<Integer,ArrayList<Sensor>> sp1=new HashMap<Integer,ArrayList<Sensor>>();
    	public double getCmax(ArrayList<Sensor> all){
		Sensor s=all.get(0);
		double cmax=0;
		double temp=0;
		for(int i=1;i<all.size();i++){
			temp=s.getDis(all.get(i));
			if(cmax<temp){
				cmax=temp;
			}
		}
		return cmax;
	}
	public void ksplit(Tour fit,int sizeofNum){
		double total=fit.getDis();
		ArrayList<Sensor> ts=fit.tour;
		Sensor s=new Sensor(0,0,1);
		ts.add(0, s);
		double cmax=getCmax(ts);
		double sum=0;
		double thre=0;
		int j=1;
		for(int i=1;i<sizeofNum;i++){
			thre=((i+1.0-1.0)/sizeofNum)*(total-2*cmax)+cmax;
			sum+=ts.get(0).getDis(ts.get(j));
			for(;j<ts.size()-1;j++){
				sum+=ts.get(j).getDis(ts.get(j+1));
				if(sum>=thre){
					j=j+2;
					ilist.add(j+1);
					break;
				}
			}
		}
	}
	public void getKTour(Tour fit){
		int j=0;
		int i=0;
		for(;i<ilist.size();i++){
			ArrayList<Sensor> sub=new ArrayList<Sensor>();
			for(;j<=ilist.get(i);j++){
				sub.add((Sensor)fit.tour.get(j));
			}
			if(i!=0){
			        sub.add(0,(Sensor)fit.tour.get(0));
			}
			j=ilist.get(i)+1;
			sp.put(i, sub);
		}

		ArrayList<Sensor> sub=new ArrayList<Sensor>();
		sub.add((Sensor)fit.tour.get(0));
		for(;j<fit.tour.size();j++){
			sub.add((Sensor)fit.tour.get(j));
		}
		sp.put(i, sub);
	}
	public double getDis(){
		double sum=0;
		Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=sp.entrySet();
		for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour){
			Tour tr=new Tour(p.getValue());
			sum+=tr.getDis();
		}
		return sum;
	}
	public void sets(){
		Sensor s=new Sensor(0,0,1);
		Set<Map.Entry<Integer, ArrayList<Sensor>>> subtour=sp.entrySet();
		for(Map.Entry<Integer, ArrayList<Sensor>> p:subtour){
			ArrayList<Sensor> al=p.getValue();
			al.add(s);
			sp1.put(p.getKey(), al);
		}
	}
}