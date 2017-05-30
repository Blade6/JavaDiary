package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeLock implements Runnable {

	public static ReentrantLock lock = new ReentrantLock();
	
	@Override
	public void run() {
		try {
			// 线程1会尝试获取锁，获取成功后会睡眠6秒
			// 线程2会尝试获取锁，必然失败，超过5秒还没有得到锁，线程2会选择放弃
			if (lock.tryLock(5, TimeUnit.SECONDS)) {
				Thread.sleep(6000);
			} else {
				System.out.println("get lock failed");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (lock.isHeldByCurrentThread()) lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		TimeLock t = new TimeLock();
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		t1.start();
		t2.start();
	}

}
