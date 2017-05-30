package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 读写锁，读读之间不阻塞
// 第45行和56行使用读锁和写锁，整个运行时间大概2秒，读之间是并行的，写之间是串行的，且写会阻塞读
// 如果注释45行和56行，取消注释46行和57行，可以看到普通锁的运行情况
// 普通锁的话，读也会阻塞读，结果导致整个程序运行时间长达20秒。
public class ReadWriteLockDemo {

	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	private int value;
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();                     // 模拟读操作
			System.out.println(Thread.currentThread().getId() + ":I am reading!");
			Thread.sleep(1000);              // 读操作的耗时越多，读写锁的优势就越明显
			return value;
		} finally {
			lock.unlock();
		}
	}
	
	public void handleWrite(Lock lock, int index) throws InterruptedException {
		try {
			lock.lock();                     // 模拟写操作
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
