package concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock implements Runnable {

	public static ReentrantLock fairlock = new ReentrantLock(true); 
	
	@Override
	public void run() {
		while (true) {
			try {
				fairlock.lock();
				System.out.println(Thread.currentThread().getName() + " »ñµÃËø");
			} finally {
				fairlock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		FairLock r = new FairLock();
		Thread t1 = new Thread(r, "Thread_t1");
		Thread t2 = new Thread(r, "Thread_t2");
		t1.start();
		t2.start();
	}

}
