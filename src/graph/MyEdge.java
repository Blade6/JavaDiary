package graph;

// ��
public class MyEdge implements Comparable<MyEdge> {

	Vertex cur; // ��ǰ����
	Vertex next; // �ڽӶ���
	int weight; // ���ӵ��ڽӽڵ�ıߵ�Ȩ
	
	// �ṩ����Ȩͼ�Ĺ��췽��
	public MyEdge(Vertex next, int weight) {
		this(next);
		this.weight = weight;
	}
	
	// �ṩ����Ȩͼ�Ĺ��췽��
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
