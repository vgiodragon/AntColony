
public class miWorkerLocal {

	final int cantHilos;
	int cantHilosUsados;
	int port;
	String adrs;
	String id;
	
	public miWorkerLocal(String id,int cantHilos,int port,String adrs) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.cantHilos=cantHilos;
		this.port=port;
		this.adrs=adrs;
		cantHilosUsados=0;
	}
	
	public String getID(){
		return id;
	}
	
	public int getCantHilos(){
		return cantHilos;
	}
	
	public synchronized int [] estado(){
		return new int[]{cantHilosUsados,cantHilos};
	}
	
	public synchronized void menosHilo(){
		cantHilosUsados-=1;
	}
	public synchronized void masHilo(){
		cantHilosUsados+=1;
	}
}
