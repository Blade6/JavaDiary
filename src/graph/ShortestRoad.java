package graph;

import java.util.LinkedList;
import java.util.Queue;

// 无权最短路径：给定顶点s，求出从s到所有其他顶点的最短路径
// 使用了广度优先搜索算法来实现
public class ShortestRoad extends Digraph {

	public static void main(String[] args) {
		ShortestRoad demo = new ShortestRoad(7);
		// Shortest Road.txt描述的图和Dijkstra.txt描述的一致，只是没有权
		demo.init("resources/Shortest Road.txt", false);
		System.out.println("无权最短路径算法：");
		Vertex v1 = demo.find("v1");
		demo.unweighted(v1);
		demo.printPath(v1);
	}
	
	protected ShortestRoad(int size) {
		super(size);
	}

	public void unweighted(Vertex s) {
		Queue<Vertex> q = new LinkedList<>();
		
		for (int i = 0; i < NUM_VERTICES; i++)
			graph[i].dist = INFINITY;
		
		s.dist = 0;
		q.add(s);
		
		while (!q.isEmpty()) {
			Vertex v = q.poll();
			
			for (int i = 0; i < v.adj.size(); i++)
				if (v.adj.get(i).next.dist == INFINITY) {
					v.adj.get(i).next.dist = v.dist + 1;
					v.adj.get(i).next.path = v;
					q.add(v.adj.get(i).next);
				}
		}
	}	
	
}
