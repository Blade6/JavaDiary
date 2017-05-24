package graph;

import dataStructure.BinaryHeap;
import dataStructure.DisjSets;

// 最小生成树
public class MinimumSpanningTree extends UndirectedGraph {

	public static void main(String[] args) {
		MinimumSpanningTree demo = new MinimumSpanningTree(7);
		demo.init("resources/UDG.txt", true);
		System.out.println("Prim算法生成最小生成树：");
		demo.Prim();
		demo.print();
		System.out.println("kruskal算法生成最小生成树：");
		demo.kruskal();
	}
	
	protected MinimumSpanningTree(int size) {
		super(size);
	}
	
	// 适合稠密图，稠密图用邻接矩阵表示
	// 此处仍然用邻接表来呈现
	public void Prim() {
		for (int i = 0; i < NUM_VERTICES;i ++) {
			graph[i].known = false;
			graph[i].dist = INFINITY;
			graph[i].path = null;
		}
		
		graph[0].dist = 0;
		
		for ( ; ; ) {
			Vertex v = findSmall();
			if (v.name == null)
				break;
			v.known = true;
			
			for (int i = 0; i < v.adj.size(); i++) {
				Vertex w = v.adj.get(i).next;
				if (!w.known && v.adj.get(i).weight < w.dist) {
					w.dist = v.adj.get(i).weight;
					w.path = v;
				}
			}
			
		}
	}

	// 适合稀疏图
	public void kruskal() {
		int edgesAccepted = 0;
		DisjSets ds = new DisjSets(NUM_VERTICES);
		BinaryHeap<MyEdge> pq = new BinaryHeap<>(getEdges());
		MyEdge e;
		Vertex u, v;
		MyEdge[] result = new MyEdge[NUM_VERTICES-1];
		
		while (edgesAccepted < NUM_VERTICES - 1) {
			e = pq.deleteMin(); // Edge e = (u,v)
			u = e.cur;
			v = e.next;
			int us = ds.find(u.topNum-1);
			int vs = ds.find(v.topNum-1);
			if (us != vs) {
				result[edgesAccepted++] = e;
				ds.union(us, vs);
			}
		}
		
		for (int i = 0; i < result.length; i++)
			System.out.println(result[i].cur.name + "--" + result[i].next.name);
	}
	
	// 打印最小生成树
	public void print() {
		for (int i = 0; i < NUM_VERTICES; i++)
			if (graph[i].path != null)
				System.out.println(graph[i].name + "--" + graph[i].path.name);
	}
	
}
