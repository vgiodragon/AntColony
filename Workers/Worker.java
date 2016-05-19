import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Worker extends Thread{
	
	int socketServerPORT = 4081;
	ServerSocket serverSocket;
	int cantH;
	
	public Worker(int ssPort){
		socketServerPORT= ssPort;
		cantH=0;
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(socketServerPORT);		

			while(true){
				System.out.println("Worker a la espera");
        		
				Socket socket = serverSocket.accept();        		
        		
        		new HiloWorker(socket,"Hilo # "+cantH).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		System.out.println("Ingresa el # de worker");
		int op=sc.nextInt();
		if(op==1)
			new Worker(4081).start();
		
		else if(op==2)
			new Worker(4082).start();
		
		else 
			new Worker(4083).start();
		
		sc.close();
	}
	
	
}
