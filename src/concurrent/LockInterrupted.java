package concurrent;

import java.util.concurrent.locks.ReentrantLock;

// ��ʾ���жϽ����������
public class LockInterrupted implements Runnable {

	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	int lock;
	
	/**
	 * ���Ƽ���˳�򣬷��㹹������ 
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
			System.out.println(Thread.currentThread().getId() + ":�߳��˳�");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		LockInterrupted r1 = new LockInterrupted(1);
		LockInterrupted r2 = new LockInterrupted(2);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		// main����˯�ߣ�t1��t2��������״̬
		Thread.sleep(1000);
		// main�ѹ������ж�t2��t2�������lock1�����룬ͬʱ�ͷ���ռ�е�lock2���Ӷ���������״̬
		t2.interrupt();
	}

}
