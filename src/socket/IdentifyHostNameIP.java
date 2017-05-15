package socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

// InetAddress���Բ鿴����Щ�������ڷ�������
// �˳�����Ҫ��Learn/binĿ¼��
// ��cmd��java socket/IdentifyHostNameIP www.gdufs.edu.cn
// ���Կ������ΪHost name: www.gdufs.edu.cn IP address: 202.116.192.17
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
