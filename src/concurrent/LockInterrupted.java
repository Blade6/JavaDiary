package concurrent;

import java.util.concurrent.locks.ReentrantLock;

// 演示锁中断解决死锁问题
public class LockInterrupted implements Runnable {

	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	int lock;
	
	/**
	 * 控制加锁顺序，方便构造死锁 
	 * @param lock
	 */
	public LockInterrupted(int lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			if (lock == 1) {
				lock1.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				lock2.lockInterruptibly();
			} else {
				lock2.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					lock1.lockInterruptibly();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (lock1.isHeldByCurrentThread())
				lock1.unlock();
			if (lock2.isHeldByCurrentThread())
				lock2.unlock();
			System.out.println(Thread.currentThread().getId() + ":线程退出");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		LockInterrupted r1 = new LockInterrupted(1);
		LockInterrupted r2 = new LockInterrupted(2);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		// main进入睡眠，t1和t2进入死锁状态
		Thread.sleep(1000);
		// main醒过来，中断t2，t2会放弃对lock1的申请，同时释放它占有的lock2，从而结束死锁状态
		t2.interrupt();
	}

}
