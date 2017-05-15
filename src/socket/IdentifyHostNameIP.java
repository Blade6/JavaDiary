package socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

// InetAddress可以查看有哪些人连接在服务器上
// 此程序需要在Learn/bin目录下
// 在cmd用java socket/IdentifyHostNameIP www.gdufs.edu.cn
// 可以看到结果为Host name: www.gdufs.edu.cn IP address: 202.116.192.17
public class IdentifyHostNameIP {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			try {
				InetAddress address = InetAddress.getByName(args[i]);
				System.out.print("Host name: " + address.getHostName() + " ");
				System.out.println("IP address: " + address.getHostAddress());
			}
			catch (UnknownHostException ex) {
				System.err.println("Unknown host or IP address " + args[i]);
			}
	}

}
