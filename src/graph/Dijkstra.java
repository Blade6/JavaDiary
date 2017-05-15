package graph;

//Dijkstra算法解决有向有权图问题
public class Dijkstra extends Digraph {

	public static void main(String[] args) {
		Dijkstra demo = new Dijkstra(7);
		demo.init("resources/Dijkstra.txt", true);
		System.out.println("Dijkstra算法：");
		Vertex v1 = demo.find("v1");
		demo.dijkstra(v1);
		demo.printPath(v1);
	}
	
	protected Dijkstra(int size) {
		super(size);
	}

	// Dijkstra算法实际上解决的问题是给定一个源点和其他所有顶点，求出源点到其他所有顶点的最短路径
	public void dijkstra(Vertex s) {
		for (int i = 0; i < NUM_VERTICES; i++) {
			graph[i].dist = INFINITY;
			graph[i].known = false;
		}
			
		s.dist = 0;
			
		for ( ; ; ) {
			Vertex v = findSmall();
			if (v.name == null)
				break;
			v.known = true;
				
			for (int i = 0; i < v.adj.size(); i++) {
				Vertex w = v.adj.get(i).next;
				if (!w.known) {
					if (v.dist + v.adj.get(i).weight < w.dist) {
						w.dist = v.dist + v.adj.get(i).weight;
						w.path = v;
					}
				}
			}
		}
	}		
	
}
