package graph;

// 边
public class MyEdge implements Comparable<MyEdge> {

	Vertex cur; // 当前顶点
	Vertex next; // 邻接顶点
	int weight; // 连接到邻接节点的边的权
	
	// 提供给有权图的构造方法
	public MyEdge(Vertex next, int weight) {
		this(next);
		this.weight = weight;
	}
	
	// 提供给无权图的构造方法
	public MyEdge(Vertex next) {
		this.next = next;
	}

	@Override
	public int compareTo(MyEdge o) {
		if (this.weight < o.weight) return -1;
		else if (this.weight > o.weight) return 1;
		return 0;
	}

}
