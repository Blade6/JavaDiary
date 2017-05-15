package graph;

import java.util.LinkedList;
import java.util.Queue;

// ��Ȩ���·������������s�������s������������������·��
// ʹ���˹�����������㷨��ʵ��
public class ShortestRoad extends Digraph {

	public static void main(String[] args) {
		ShortestRoad demo = new ShortestRoad(7);
		// Shortest Road.txt������ͼ��Dijkstra.txt������һ�£�ֻ��û��Ȩ
		demo.init("resources/Shortest Road.txt", false);
		System.out.println("��Ȩ���·���㷨��");
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
