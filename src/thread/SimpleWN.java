package thread;

// 演示wait()和notify()方法
public class SimpleWN {

	final static Object object = new Object();
	
	public static class T1 extends Thread {
		public void run() {
			synchronized (object) {
				// t1此时是持有object的对象锁的
				System.out.println(System.currentTimeMillis() + ":T1 start! ");
				try {
					System.out.println(System.currentTimeMillis() + ":T1 wait for object ");
					// wait()方法执行时，t1进行等待，并释放object的对象锁
					// 当收到t2的通知后，t1不断尝试获取object的对象锁，直到t2结束才拿到object的对象锁
					// 从t1尝试到获得，一共经过了2秒，所以时间戳最后两行恰好相差了2秒t2的睡眠时间
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
			// t1的wait()方法执行后，t2获得t1释放的object的对象锁
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
				// t2通知t1
				object.notify();
				System.out.println(System.currentTimeMillis() + ":T2 end!");
				try {
					// t2开始睡眠
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
			//知道t2醒后结束了synchronized块，t2才释放了object的对象锁
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new T1();
		Thread t2 = new T2();
		t1.start();
		t2.start();
	}

}
