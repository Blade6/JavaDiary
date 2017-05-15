package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		int times = 100;
		executor.execute(new PrintChar('a', times));
		executor.execute(new PrintChar('b', times));
		executor.execute(new PrintNum(times));
		
		executor.shutdown();
	}

}
