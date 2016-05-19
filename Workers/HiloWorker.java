import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloWorker extends Thread{

	String name;
	Socket socket;
	
	public HiloWorker(Socket socket,String name){		
		this.socket=socket;
		this.name=name;
	}
	
	public String Trabajar(String trabajo){
        System.out.println("Trabajo segun: "+trabajo);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Esta es la respuesta del HiloWorker :"+funcion(100);
	}
	 double funcion(int fin){
	        double sum = 0;
	        for(int j = 0; j<=fin;j++ ){
	            sum = sum + Math.sin(j*Math.random());
	        }
	        return sum;
	    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub		
		try {
			String trabajo = Recibir(socket);
			String respuesta=Trabajar(trabajo);			
			Mandar(socket, respuesta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
