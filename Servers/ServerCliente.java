import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCliente{

    static final int socketServerPORT = 8081;
    ServerSocket serverSocket;
    int count;
    ServidorWorker servidorWorker;
    
    public ServerCliente(){
    	count=0;
    	servidorWorker = new ServidorWorker();    	
    	porSiempre();
    }
	
    
    public void porSiempre() {
    	// TODO Auto-generated method stub
        try {        	
        	System.out.println("Server Cliente Listo y la espera de clientes");
        	serverSocket = new ServerSocket(socketServerPORT);
        	ExecutorService hiloejecutor = Executors.newCachedThreadPool();
        	
        	while (true) {
        		System.out.println("Espero a un cliente");
        		Socket socket = serverSocket.accept();
                count++;
                HiloServidorCliente hc = new HiloServidorCliente(socket,"Cliente "+count,servidorWorker);
                hiloejecutor.execute(hc);
                
        	}
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
	public static void main(String[] args) {
		new ServerCliente();
		
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
