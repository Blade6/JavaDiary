package thread;

import java.util.concurrent.Semaphore;

// 只有一个许可的信号量可以用来模拟一个相互排斥的锁
public class AccountDemo {
	private static Semaphore semaphore = new Semaphore(1);
	private int balance = 0;
	
	public int getBalance() {
		return balance;
	}
	
	public void deposit(int amount) {
		try {
			semaphore.acquire();
			int newBalance = balance + amount;
			
			Thread.sleep(5);
			
			balance = newBalance;
		} catch (InterruptedException ex) {}
		finally {
			semaphore.release();
		}
	}
}
