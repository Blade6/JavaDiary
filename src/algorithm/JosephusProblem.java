package algorithm;

// Լɪ��˹����
public class JosephusProblem {

	public static void main(String[] args) {
		// ģ�ⷨ
		//JosephusStack demo = new JosephusStack(30, 2);
		//demo.out();
		
		JosephusProblem p = new JosephusProblem();
		p.formula(7, 2);
	}
	
	// ��ѧ��ʽ������ʽ��Դ���·�ע��
	private void formula(int n, int m) {
		int ans = 0;
		for (int i = 2; i <= n; i++) 
			ans = (ans + m) % i;
		System.out.println(ans + 1);
	}
	
}

class JosephusStack {
	private int capacity;
	private int step;
	private int size;
	
	private JosephusNode head;
	private JosephusNode tail;
	private JosephusNode cur;
	
	public JosephusStack(int capacity, int step) {
		this.capacity = capacity;
		this.step = step;
		
		tail = new JosephusNode(capacity, null);
		cur = tail;
		for (int i = capacity - 1; i > 0; --i) {
			cur = new JosephusNode(i, cur);
		}
		head = cur;
		tail.after = head;
		
		size = capacity;
	}
	
	public void out() {
		int count = 0;
		JosephusNode pre = null;
		while (size > 1) {
			++count;
			if (count > step) count = 1;
			if (count == step) {
				System.out.println("\tɱ��:" + cur.getValue());
				pre.after = cur.after;
				cur = cur.after;
				--size;
			} else {
				System.out.println("����:" + cur.getValue());
				pre = cur;
				cur = cur.after;
			}
		}
		System.out.println("ʤ����:" + cur.getValue());
	}
}

class JosephusNode {
	public JosephusNode after;
	private int value;
	
	public JosephusNode(int value, JosephusNode node) {
		this.value = value;
		after = node;
	}
	
	public int getValue() {
		return value;
	}
}

/**
����֪����һ����(���һ����m%n-1) ����֮��ʣ�µ�n-1���������һ���µ�Լɪ�򻷣��Ա��Ϊk=m%n���˿�ʼ��:
   k   k+1   k+2   ... n-2, n-1, 0, 1, 2, ... k-2
���Ҵ�k��ʼ��0��

�������ǰ����ǵı����һ��ת����
k     --> 0
k+1 --> 1
k+2 --> 2
...
...
k-2 --> n-2
k-1 --> n-1

�任�������ȫȫ��Ϊ��(n-1)���˱����������⣬��������֪�����������Ľ⣺����x�����յ�ʤ���ߣ���ô�����������������x���ȥ���պþ���n��������Ľ��𣿣������ȥ�Ĺ�ʽ�ܼ򵥣����Ŵ�Ҷ������Ƴ�����x'=(x+k)%n

���֪��(n-1)���˱���������Ľ⣿�ԣ�ֻҪ֪��(n-2)���˵Ľ�����ˡ�(n-2)���˵Ľ��أ���Ȼ������(n-3)����� ---- ����Ȼ����һ���������⣡���ˣ�˼·�����ˣ�����д���ƹ�ʽ��

��f[i]��ʾi��������Ϸ��m�˳����ʤ���ߵı�ţ����Ľ����Ȼ��f[n]

���ƹ�ʽ
f[1]=0;
f[i]=(f[i-1]+m)%i;   (i>1)

���������ʽ������Ҫ���ľ��Ǵ�1-n˳�����f[i]����ֵ���������f[n]����Ϊʵ�������б�����Ǵ�1��ʼ���������f[n]+1

 */
