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
					//10-13行注释的代码是处理中断的逻辑，如果不写，那么22行中断t1不会有效果，t1仍然继续运行
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000); //使执行这一句代码的线程睡眠，也就是main方法所在线程睡眠
		// main()方法所在线程醒后中断t1
		t1.interrupt();
	}

}
