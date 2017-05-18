package socket;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// 一个服务端处理多个客户端请求
// 为每一个客户端请求新开一个线程
// 为了实现单服务器端，多客户端，可以先开启本程序来开启服务器端
// 然后运行Client开启一个客户端
// 然后在bin目录下java socket/Client开启第二个客户端
public class MultiThreadServer extends JFrame {

	private JTextArea jta = new JTextArea();
	
	public static void main(String[] args) {
		new MultiThreadServer();
	}
	
	public MultiThreadServer() {
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		setTitle("MultiThreadServer");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("MultiThreadServer started at " + new Date() + '\n');
			
			// Number a client
			int clientNo = 1;
			
			while (true) {
				// Listen for a new connection request
				Socket socket = serverSocket.accept();
				// 注意46行等待客户端的socket请求，如果客户端没有请求
				// 那么服务器的线程将停在46行，一直等待
				// accept()方法接受新的客户端的请求，每个客户端仅接受一次
				
				// Display the client number
				jta.append("Starting thread for client " + clientNo +
					" at " + new Date() + '\n');
				
				// Find the client's host name, and IP address
				InetAddress inetAddress = socket.getInetAddress();
				jta.append("Client " + clientNo + "'s host name is "
					+ inetAddress.getHostName() + "\n");
				jta.append("Client " + clientNo + "'s IP Address is "
					+ inetAddress.getHostAddress() + "\n");
				
				// Create a new thread for the connection
				HandleAClient task = new HandleAClient(socket);
				
				// Start the new thread
				new Thread(task).start();
				
				// Increment clientNo
				clientNo++;
			}
		}
		catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	// Define the thread class for handing new connection 
	class HandleAClient implements Runnable {
		private Socket socket;
		
		/** Construct a thread */
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}
		
		/** Run a thread */
		@Override
		public void run() {
			try {
				DataInputStream inputFromClient = new DataInputStream(
					socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(
					socket.getOutputStream());
				
				while (true) {
					// Receive radius from the client
					double radius = inputFromClient.readDouble();
					
					// Compute area
					double area = radius * radius * Math.PI;
					
					// Send area back to the client
					outputToClient.writeDouble(area);
					
					jta.append("radius received from client: " +
						radius + '\n');
					jta.append("Area found: " + area + '\n');
				}
			}
			catch (IOException e) {
				System.err.println(e);
			}
		}
		
	}
}
