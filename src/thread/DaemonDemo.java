package thread;

public class DaemonDemo {

	public static class DaemonT extends Thread {
		public void run() {
			while (true) {
				System.out.println("I am alive");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new DaemonT();
		// 设置为守护进程
		// 当一个Java应用内，只有守护进程时，Java虚拟机就会自动退出
		t.setDaemon(true);
		t.start();
		
		Thread.sleep(2000);
	}

}
