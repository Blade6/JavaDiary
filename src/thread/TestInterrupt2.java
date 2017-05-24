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
						//再次设置中断状态，要不然27行白中断了
						Thread.currentThread().interrupt();
					}
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000);
		//t1在睡眠时被中断，抛出InterruptedException，此时15行sleep()方法会清除t1的中断标记
		t1.interrupt();
	}

}
