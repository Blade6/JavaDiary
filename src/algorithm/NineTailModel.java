package algorithm;

import java.util.ArrayList;
import java.util.List;

import graph.AbstractGraph;
import graph.UnweightedGraph;

// ��öӲ�ҷ�ת����
// �����ٷ�ת����������Ӳ�ҵĳ�������
// ÿ�η�ת��ı��Ե�ǰӲ��Ϊ���ģ�����Ӳ�ҡ���Ӳ�ҡ���Ӳ�ҡ���Ӳ�ҹ����Ӳ�ҵĳ���
// һ���Ÿ�Ӳ�ң�һ��512��״̬����ÿ��״̬���ɶ��㣬����ͳһ���µ�״̬�����յ�
// ʵ����������������������·��
// �����ȡ�ķ����ǹ�������������������ж��㣬BFS���ȼ�����ǰ����������ڽӶ��㣬Ȼ�󲻶�������ɢ��
// ������������ǿ��Գɹ��ġ�
// ����,Dijkstra��Floyd�������ͼ�����·�������ﲻ���á�
public class NineTailModel {
	
	public static void main(String[] args) {
		String s = "HHHTHTHHH";
		char[] initialNode = s.toCharArray();
		
		NineTailModel demo = new NineTailModel();
		List<Integer> path = demo.getShortestPath(NineTailModel.getIndex(initialNode));
		for (int i = 0; i < path.size(); i++)
			NineTailModel.printNode(NineTailModel.getNode(path.get(i).intValue()));
	}

	public final static int NUMBER_OF_NODES = 512;
	protected AbstractGraph<Integer>.Tree tree;
	
	public NineTailModel() {
		List<AbstractGraph.Edge> edges = getEdges();
		
		UnweightedGraph<Integer> graph = new UnweightedGraph<>(edges, NUMBER_OF_NODES);
		
		tree = graph.bfs(511);
	}
	
	// ����һ������Edge��������Ա�
	private List<AbstractGraph.Edge> getEdges() {
		List<AbstractGraph.Edge> edges = 
			new ArrayList<>();
		
		for (int u = 0; u < NUMBER_OF_NODES; u++) {
			for (int k = 0; k < 9; k++) {
				char[] node = getNode(u);
				if (node[k] == 'H') {
					int v = getFilppedNode(node, k);
					edges.add(new AbstractGraph.Edge(v, u));
				}
			}
		}
		
		return edges;
	}
	
	// ��ת����ָ��λ�ú����ڽ�λ�ã������½����±�
	public static int getFilppedNode(char[] node, int position) {
		int row = position / 3;
		int column = position % 3;
		
		flipACell(node, row, column);
		flipACell(node, row - 1, column);
		flipACell(node, row + 1, column);
		flipACell(node, row, column - 1);
		flipACell(node, row, column + 1);
		
		return getIndex(node);
	}
	
	// ״̬ͼʵ�����Ǹ�һά���飬��������ʱ���ֳ���ɶ�ά�������о�
	// ��ת����ָ���к�����������λ��
	public static void flipACell(char[] node, int row, int column) {
		if (row >= 0 && row <= 2 && column >= 0 && column <= 2)
			if (node[row * 3 + column] == 'H')
				node[row * 3 + column] = 'T';
			else
				node[row * 3 + column] = 'H';
	}
	
	// ����״̬ͼ����״̬������
	// ���ؽ����±�
	public static int getIndex(char[] node) {
		int result = 0;
		
		for (int i = 0; i < 9; i++)
			if (node[i] == 'T')
				result = result * 2 + 1;
			else
				result = result * 2 + 0;
		
		return result;
	}
	
	// ÿ��״̬��Ӧһ������index�������������Ի��״̬ͼ
	// ����ָ���±�Ľ�㣬����0���ذ����Ÿ�H�Ľ�㣬511���ذ����Ÿ�T�Ľ��
	public static char[] getNode(int index) {
		char[] result = new char[9];
		
		for (int i = 0; i < 9; i++) {
			int digit = index % 2;
			if (digit == 0)
				result[8 - i] = 'H';
			else
				result[8 - i] = 'T';
			index = index / 2;
		}
		
		return result;
	}
	
	public List<Integer> getShortestPath(int nodeIndex) {
		return tree.getPath(nodeIndex);
	}
	
	public static void printNode(char[] node) {
		for (int i = 0; i < 9; i++)
			if (i % 3 != 2)
				System.out.print(node[i]);
			else
				System.out.println(node[i]);
		System.out.println();
	}
	
}
