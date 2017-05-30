package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemapDemo implements Runnable {

	final Semaphore semp = new Semaphore(5);
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(20);
		final SemapDemo demo = new SemapDemo();
		for (int i = 0; i < 20; i++)
			exec.submit(demo);
	}

	@Override
	public void run() {
		try {
			// 获取信号量
			semp.acquire();
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId() + ":done!");
			// 释放信号量
			semp.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
