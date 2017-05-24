package thread;

public class TestInterrupt2 {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted!");
						break;
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						System.out.println("Interrupted When Sleep");
						//�ٴ������ж�״̬��Ҫ��Ȼ27�а��ж���
						Thread.currentThread().interrupt();
					}
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000);
		//t1��˯��ʱ���жϣ��׳�InterruptedException����ʱ15��sleep()���������t1���жϱ��
		t1.interrupt();
	}

}
