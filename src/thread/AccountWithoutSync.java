package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// ��ʾ�̳߳�ͻ���Լ�����ͬ������
// ���ַ�������ע�͵��ˣ����н⿪ע�����в鿴
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
//			������
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

//		����һ 
//		public synchronized void deposit(int amount) {
		public void deposit(int amount) {
			int newBalance = balance + amount;
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}
			
			balance = newBalance;
			
			// ʹ��������䲻��������⣬��������д��Ϊ�˷Ŵ�����
			// balance = balance + amount;
		}
	}

}
