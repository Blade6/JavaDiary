package algorithm;

// 约瑟夫斯问题
public class JosephusProblem {

	public static void main(String[] args) {
		// 模拟法
		//JosephusStack demo = new JosephusStack(30, 2);
		//demo.out();
		
		JosephusProblem p = new JosephusProblem();
		p.formula(7, 2);
	}
	
	// 数学公式法，公式来源见下方注释
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
				System.out.println("\t杀死:" + cur.getValue());
				pre.after = cur.after;
				cur = cur.after;
				--size;
			} else {
				System.out.println("报数:" + cur.getValue());
				pre = cur;
				cur = cur.after;
			}
		}
		System.out.println("胜利者:" + cur.getValue());
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
我们知道第一个人(编号一定是m%n-1) 出列之后，剩下的n-1个人组成了一个新的约瑟夫环（以编号为k=m%n的人开始）:
   k   k+1   k+2   ... n-2, n-1, 0, 1, 2, ... k-2
并且从k开始报0。

现在我们把他们的编号做一下转换：
k     --> 0
k+1 --> 1
k+2 --> 2
...
...
k-2 --> n-2
k-1 --> n-1

变换后就完完全全成为了(n-1)个人报数的子问题，假如我们知道这个子问题的解：例如x是最终的胜利者，那么根据上面这个表把这个x变回去不刚好就是n个人情况的解吗？！！变回去的公式很简单，相信大家都可以推出来：x'=(x+k)%n

如何知道(n-1)个人报数的问题的解？对，只要知道(n-2)个人的解就行了。(n-2)个人的解呢？当然是先求(n-3)的情况 ---- 这显然就是一个倒推问题！好了，思路出来了，下面写递推公式：

令f[i]表示i个人玩游戏报m退出最后胜利者的编号，最后的结果自然是f[n]

递推公式
f[1]=0;
f[i]=(f[i-1]+m)%i;   (i>1)

有了这个公式，我们要做的就是从1-n顺序算出f[i]的数值，最后结果是f[n]。因为实际生活中编号总是从1开始，我们输出f[n]+1

 */
