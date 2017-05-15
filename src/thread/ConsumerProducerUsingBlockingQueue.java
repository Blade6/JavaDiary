package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 阻塞队列在试图向一个满队列添加元素或者从空队列中删除元素时会导致线程阻塞。
// 此处展示的是ArrayBlockingQueue，使用数组实现阻塞队列，它是受限的。
// 还可以创建LinkedBlockingQueue或PriorityBlockingQueue
// 创建这两者的时候可以选择受限或不受限
// 如果选择了不受限，那么put方法将永远不会阻塞
public class ConsumerProducerUsingBlockingQueue {

	private static ArrayBlockingQueue<Integer> buffer = 
		new ArrayBlockingQueue<>(2);
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ProducerTask());
		executor.execute(new ConsumerTask());
		executor.shutdown();
	}
	
	private static class ProducerTask implements Runnable {
		@Override
		public void run() {
			try {
				int i = 1;
				while (true) {
					System.out.println("Producer writes " + i);
					buffer.put(i++);
					Thread.sleep((int)(Math.random() * 1000));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
		}
	}
	
	private static class ConsumerTask implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					System.out.println("\t\t\tConsumer reads " + buffer.take());
					Thread.sleep((int)(Math.random() * 1000));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
		}	
	}

}
