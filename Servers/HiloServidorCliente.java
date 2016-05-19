import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidorCliente implements Runnable{

	Socket socket;
	String nameC;
	ServidorWorker servidorWorker;
	
	public HiloServidorCliente(Socket socket, String name,ServidorWorker servidorWorker) {
		this.socket=socket;
		nameC=name;
		this.servidorWorker=servidorWorker;
	}
	@Override
	public void run() {
		try {
			Mandar(socket,nameC);
			String pedido=Recibir(socket);
			System.out.println("Hilo Servidor Cliente: "+pedido);
			
			String respuesta=servidorWorker.Trabajo(pedido);
			
			Mandar(socket,respuesta);
			
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
            //Log.d("HILO","socket:Closed "+socket.isClosed()+"_conected:"+socket.isConnected());
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            respuesta=dIn.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
