package thread;

public class TestInterrupt {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (true) {
//					if (Thread.currentThread().isInterrupted()) {
//						System.out.println("Interrupted!");
//						break;
//					}
					//10-13��ע�͵Ĵ����Ǵ����жϵ��߼��������д����ô22���ж�t1������Ч����t1��Ȼ��������
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000); //ʹִ����һ�������߳�˯�ߣ�Ҳ����main���������߳�˯��
		// main()���������߳��Ѻ��ж�t1
		t1.interrupt();
	}

}
