package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// ��д��������֮�䲻����
// ��45�к�56��ʹ�ö�����д������������ʱ����2�룬��֮���ǲ��еģ�д֮���Ǵ��еģ���д��������
// ���ע��45�к�56�У�ȡ��ע��46�к�57�У����Կ�����ͨ�����������
// ��ͨ���Ļ�����Ҳ�����������������������������ʱ�䳤��20�롣
public class ReadWriteLockDemo {

	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	private int value;
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();                     // ģ�������
			System.out.println(Thread.currentThread().getId() + ":I am reading!");
			Thread.sleep(1000);              // �������ĺ�ʱԽ�࣬��д�������ƾ�Խ����
			return value;
		} finally {
			lock.unlock();
		}
	}
	
	public void handleWrite(Lock lock, int index) throws InterruptedException {
		try {
			lock.lock();                     // ģ��д����
			System.out.println(Thread.currentThread().getId() + ":I am writing!");
			Thread.sleep(1000);
			value = index;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();
		Runnable readRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					demo.handleRead(readLock);
					//demo.handleRead(lock);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable writeRunnable = new Runnable() {	
			@Override
			public void run() {
				try {
					demo.handleWrite(writeLock, new java.util.Random().nextInt());
					//demo.handleWrite(lock, new java.util.Random().nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		for (int i = 0; i < 18; i++) {
			new Thread(readRunnable).start();
		}
		
		for (int i = 18; i < 20; i++) {
			new Thread(writeRunnable).start();
		}
	}

}
