
public class Replace {
	private int id;
	private Sensor rep;
	public Replace(int id, Sensor rep){
		this.id=id;
		this.rep=rep;
	}
	public int getId(){
		return id;
	}
	public Sensor getSensor(){
		return rep;
	}
	public void setId(int id){
		this.id=id;
	}
	public void setRep(Sensor rep){
		this.rep=rep;
	}
}
