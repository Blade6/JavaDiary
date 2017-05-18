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

// һ������˴������ͻ�������
// Ϊÿһ���ͻ��������¿�һ���߳�
// Ϊ��ʵ�ֵ��������ˣ���ͻ��ˣ������ȿ�����������������������
// Ȼ������Client����һ���ͻ���
// Ȼ����binĿ¼��java socket/Client�����ڶ����ͻ���
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
				// ע��46�еȴ��ͻ��˵�socket��������ͻ���û������
				// ��ô���������߳̽�ͣ��46�У�һֱ�ȴ�
				// accept()���������µĿͻ��˵�����ÿ���ͻ��˽�����һ��
				
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
