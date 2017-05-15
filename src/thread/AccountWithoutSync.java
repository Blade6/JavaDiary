package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 演示线程冲突，以及两种同步方法
// 两种方法都被注释掉了，自行解开注释运行查看
public class AccountWithoutSync {

	private static Account account = new Account();
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 100; i++) {
			executor.execute(new AddPennyTask());
		}

		executor.shutdown();
		
		while (!executor.isTerminated()){
			
		}
		
		System.out.println(account.getBalance());
	}
	
	private static class AddPennyTask implements Runnable {
		public void run() {
//			方法二
//			synchronized (account) {
//				account.deposit(1);
//			}
			account.deposit(1);
		}
	}
	
	private static class Account {
		private int balance = 0;
		
		public int getBalance() {
			return balance;
		}

//		方法一 
//		public synchronized void deposit(int amount) {
		public void deposit(int amount) {
			int newBalance = balance + amount;
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}
			
			balance = newBalance;
			
			// 使用以下语句不会出现问题，上面这样写是为了放大问题
			// balance = balance + amount;
		}
	}

}
