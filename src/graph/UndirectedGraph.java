package graph;

import java.util.HashSet;

// 无向图
public class UndirectedGraph extends baseGraph {

	public static void main(String[] args) {
		UndirectedGraph demo = new UndirectedGraph(7);
		demo.init("resources/UDG.txt", true);
		demo.printEdges();
	}
	
	protected UndirectedGraph(int size) {
		super(size);
	}
	
	// 获取所有边
	public MyEdge[] getEdges() {
		int realEdgesNum = getEdgesNum();
		MyEdge[] edges = new MyEdge[realEdgesNum];
		int curEdgeNum = 0;
		// 快速判断某条边是否已经出现过
		HashSet<twoVertex> hashSet = new HashSet<>();
		for (int i = 0; i < NUM_VERTICES; i++) {
			for (int j = 0; j < graph[i].adj.size(); j++) {
				twoVertex tv = new twoVertex(graph[i].adj.get(j).next, graph[i]);
				if (hashSet.contains(tv)) continue;
				
				MyEdge newEdge = graph[i].adj.get(j);
				newEdge.cur = graph[i];
				edges[curEdgeNum] = newEdge;
				curEdgeNum++;
				hashSet.add(tv);
				if (curEdgeNum == realEdgesNum) break;
			}
			if (curEdgeNum == realEdgesNum) break;
		}
		return edges;
	}
	
	// 无向边的边数为总边数的一半
	public int getEdgesNum() {
		return NUM_EDGES / 2;
	}
	
	// 两个顶点
	class twoVertex {
		Vertex u;
		Vertex v;
		
		public twoVertex(Vertex u, Vertex v) {
			this.u = u;
			this.v = v;
		}
		
		@Override
		public int hashCode() {
			return u.hashCode() + v.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			twoVertex tmp = (twoVertex) obj;
			if (((u == tmp.u && v == tmp.v)) || (u == tmp.v && v == tmp.u))
				return true;
			return false;
		}
	}
	
	// 判断两条边是否相同
	public boolean compareEdge(MyEdge a, MyEdge b) {
		if (a == b) return true;
		else if (a.cur == b.cur && a.next == b.next) return true;
		else if (a.cur == b.next && a.next == b.cur) return true;
		return false;
	}
	
	// 打印所有边
	public void printEdges() {
		MyEdge[] edges = getEdges();
		for (int i = 0; i < edges.length; i++)
			if (edges[i] == null)
				System.out.println("bingo" + i);
			else {
				System.out.println(edges[i].cur.name + "--" + edges[i].next.name);
			}
	}

}
