package thread;

// 多线程演示
public class TaskThreadDemo {

	public static void main(String[] args){
		int times = 10;
		Runnable printA = new PrintChar('a', times);
		Runnable printB = new PrintChar('b', times);

		Thread thread1 = new Thread(printA);
		Thread thread2 = new Thread(printB);
			
		thread1.start();
		thread2.start();			
	
	}

}

class PrintChar implements Runnable {
	private String charToPrint;
	private int times;
	
	public PrintChar(char c, int t){
		charToPrint = c + "";
		times = t;
	}
	
	public void run() {
		for (int i = 0; i < times; i++) {
			System.out.println(i + " " + charToPrint + " is running!");
		}
	}
}

class PrintNum implements Runnable {
	private int lastNum;
	
	public PrintNum(int n) {
		lastNum = n;
	}
	
	public void run() {
		for (int i = 1; i <= lastNum; i++) {
			System.out.print(i);
		}
	}
}
