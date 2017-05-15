package graph;

// 有向图
public class Digraph extends baseGraph {

	public static void main(String[] args) {
		Digraph demo = new Digraph(7);
		demo.init("resources/Shortest Road.txt", false);
		Vertex s = demo.find("v1");
		System.out.println("广度优先搜索：");
		demo.BFS(s);
		System.out.println("\n深度优先搜索：");
		demo.DFS(s);
	}
	
	protected Digraph(int size) {
		super(size);
	}

	// 初始化入度，拓扑排序会改变入度，因此对于要用到入度的算法，应先初始化入度
	public void initIndegree() {
		for (int i = 0; i < NUM_VERTICES; i++) {
			graph[i].indegree = 0;
			graph[i].topNum = 0;
		}
		
		for (int i = 0; i < NUM_VERTICES; i++) {
			for (int j = 0; j < graph[i].adj.size(); j++) {
				graph[i].adj.get(j).next.indegree++;
			}
		}
	}	

	// 后序遍历打印到当前顶点的路径
	public void printOnePath(Vertex v) {
		if (v.path != null) {
			printOnePath(v.path);
			System.out.print(" -> ");
		}
		System.out.print(v.name);
	}
	
	// 打印所有顶点的路径
	public void printPath(Vertex s) {
		for (int i = 0; i < NUM_VERTICES; i++)
			if (graph[i] != s) {
				System.out.println("Destination:" + graph[i].name);
				printOnePath(graph[i]);
				System.out.println();
			}	
	}
	
}
