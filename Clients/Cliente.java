import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente extends Thread{
    final int dstPort = 8081;
    String dstAddress = "127.0.0.1";
    Socket socket = null;
	
	public Cliente() {

	}   
	
	@Override
	public void run() {
        try {
            socket = new Socket(dstAddress, dstPort);
            System.out.println("Cliente ready");
            String name = Recibir(socket);
            Mandar(socket,name+"%100");
            String respuesta = Recibir(socket);
            System.out.println(name+" recibio esto:"+respuesta);
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }		
	}
	public static void main(String[] args) {
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
		new Cliente().start();
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
