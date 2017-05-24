package thread;

// ��ʾwait()��notify()����
public class SimpleWN {

	final static Object object = new Object();
	
	public static class T1 extends Thread {
		public void run() {
			synchronized (object) {
				// t1��ʱ�ǳ���object�Ķ�������
				System.out.println(System.currentTimeMillis() + ":T1 start! ");
				try {
					System.out.println(System.currentTimeMillis() + ":T1 wait for object ");
					// wait()����ִ��ʱ��t1���еȴ������ͷ�object�Ķ�����
					// ���յ�t2��֪ͨ��t1���ϳ��Ի�ȡobject�Ķ�������ֱ��t2�������õ�object�Ķ�����
					// ��t1���Ե���ã�һ��������2�룬����ʱ����������ǡ�������2��t2��˯��ʱ��
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + ":T1 end!");
			}
		}
	}
	
	public static class T2 extends Thread {
		public void run() {
			// t1��wait()����ִ�к�t2���t1�ͷŵ�object�Ķ�����
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
				// t2֪ͨt1
				object.notify();
				System.out.println(System.currentTimeMillis() + ":T2 end!");
				try {
					// t2��ʼ˯��
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
			//֪��t2�Ѻ������synchronized�飬t2���ͷ���object�Ķ�����
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new T1();
		Thread t2 = new T2();
		t1.start();
		t2.start();
	}

}
