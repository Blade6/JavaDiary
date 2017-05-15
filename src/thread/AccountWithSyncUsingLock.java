package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 演示显式使用锁解决线程冲突
public class AccountWithSyncUsingLock {

	private static Account account = new Account();
	
	public static void main(String[] args) {
											//为每个任务都创建线程，线程池大小不定
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 100; i++) {
			executor.execute(new AddPennyTask());
		}

		executor.shutdown();
		
		while (!executor.isTerminated()){
			
		}
		
		System.out.println(account.getBalance());

	}
	
	public static class AddPennyTask implements Runnable {
		public void run() {
			account.deposit(1);
		}
	}
	
	public static class Account {
		private static Lock lock = new ReentrantLock();
		private int balance = 0;
		
		public int getBalance() {
			return balance;
		}
		
		public void deposit(int amount) {
			lock.lock();
			
			try {
				int newBalance = balance + amount;
				
				Thread.sleep(5);
				
				balance = newBalance;
			} catch (InterruptedException ex) {				
			} finally {
				lock.unlock();
			}
		}
		
	}

}
