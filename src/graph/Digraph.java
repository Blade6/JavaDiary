package graph;

// ����ͼ
public class Digraph extends baseGraph {

	public static void main(String[] args) {
		Digraph demo = new Digraph(7);
		demo.init("resources/Shortest Road.txt", false);
		Vertex s = demo.find("v1");
		System.out.println("�������������");
		demo.BFS(s);
		System.out.println("\n�������������");
		demo.DFS(s);
	}
	
	protected Digraph(int size) {
		super(size);
	}

	// ��ʼ����ȣ����������ı���ȣ���˶���Ҫ�õ���ȵ��㷨��Ӧ�ȳ�ʼ�����
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

	// ���������ӡ����ǰ�����·��
	public void printOnePath(Vertex v) {
		if (v.path != null) {
			printOnePath(v.path);
			System.out.print(" -> ");
		}
		System.out.print(v.name);
	}
	
	// ��ӡ���ж����·��
	public void printPath(Vertex s) {
		for (int i = 0; i < NUM_VERTICES; i++)
			if (graph[i] != s) {
				System.out.println("Destination:" + graph[i].name);
				printOnePath(graph[i]);
				System.out.println();
			}	
	}
	
}
