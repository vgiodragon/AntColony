import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorWorker {

	miWorkerLocal []w;
	/*
	 * LLEGA UN PEDIDO
	 * 
	 * ESCOJO A QUE WORKER MANDAR 
	 * MI ALGORITMO VA ACA
	 * 
	 * MANDO AL WORKER LA TAREA Y
	 * ESPERO SU RESPUESTA
	 * 
	 * MANDO LA RESPUESTA AL CLIENTE RESPECTIVO
	 * 
	 * 
	 * 
	 */
	
	public String Trabajo(String tarea){
		
		miWorkerLocal wn = AntColony();		
		String res="Error man :/";
		try {
			Socket socket = new Socket(wn.adrs, wn.port);
			Mandar(socket, wn.getID()+": "+tarea);
			res=Recibir(socket);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		wn.menosHilo();
		return res;
	}
	
	public synchronized miWorkerLocal AntColony(){
		int [][] estW=new int [3][2];
		int MHilosDis=0;
		int nWorker=0;
		
		for(int i=0;i<w.length;i++){
			estW[i]=w[i].estado();
			/*Obtengo el q tiene más hilos disponibles*/
			if(estW[i][1]-estW[i][0]>MHilosDis){
				MHilosDis =estW[i][1]-estW[i][0];
				nWorker=i;
			}
			/*Si hay empate chapo el que tiene espacio para más hilos*/
			else if((estW[i][1]-estW[i][0])==MHilosDis){
				if(w[i].getCantHilos()>w[nWorker].getCantHilos()){
					nWorker=i;
				}
			}
		}
		
		//Si estan full espero nuevamente a que haya hilos libres
		if(MHilosDis==0){
			try {
				System.out.println("Espero a que haya hilos libres");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return AntColony();
		}
		else{
			w[nWorker].masHilo();
			return w[nWorker];
		}
	}
	
	public ServidorWorker(){
		w = new miWorkerLocal [3];
		w[0]=new miWorkerLocal ("1",4,4081,"127.0.0.1");
		w[1]=new miWorkerLocal ("2",2,4082,"127.0.0.1");
		w[2]=new miWorkerLocal ("3",1,4083,"127.0.0.1");		
	}
	

	public void Mandar(Socket socket,String mnsj) throws IOException {
        DataOutputStream dOut;
        try {
            dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(mnsj);
            dOut.flush(); // Send off the data

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	public String Recibir(Socket socket){
        String respuesta="";

        try {
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            respuesta=dIn.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
	
}
