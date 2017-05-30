package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// ͨ����ʵ�����ǲ������Եõ��쳣������Runnableʵ���ڵ���Ϣ
// ����Ҳ֪���˳�����������������ύ��
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
	
	// wrap�����ĵ�2������Ϊһ���쳣�����汣�����ύ������̵߳Ķ�ջ��Ϣ��
	// �˷����������Runnable�������һ���װ��ʹ֮�ܴ����쳣��Ϣ
	// ���������쳣ʱ������쳣�ᱻ��ӡ
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
	
	// ��λ����ĵط�
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
