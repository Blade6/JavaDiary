package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 通过此实例我们不仅可以得到异常发生的Runnable实现内的信息
// 我们也知道了出错的任务是在哪里提交的
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
	
	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
		long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	public void execute(Runnable task) {
		super.execute(wrap(task, clientTrace(), Thread.currentThread().getName()));
	}
	
	@Override
	public Future<?> submit(Runnable task) {
		return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
	}
	
	// wrap方法的第2个参数为一个异常，里面保存着提交任务的线程的堆栈信息。
	// 此方法将传入的Runnable任务进行一层包装，使之能处理异常信息
	// 当任务发生异常时，这个异常会被打印
	private Runnable wrap(final Runnable task, final Exception clientStack,
			String clientThreadName) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch (Exception e) {
					clientStack.printStackTrace();
					throw e;
				}
			}
		};
	}
	
	// 定位出错的地方
	private Exception clientTrace() {
		return new Exception("Client stack trace");
	}

	public static void main(String[] args) {
		ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 
			0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		
		for (int i = 0; i < 5; i++)
			pools.execute(new DivTask(100,i));
	}

}

class DivTask implements Runnable {
	int a,b;
	public DivTask(int a, int b) {
		this.a = a;
		this.b = b;
	}
	@Override
	public void run() {
		double re = a / b;
		System.out.println(re);
	}
	
}
