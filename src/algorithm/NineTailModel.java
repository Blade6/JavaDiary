package algorithm;

import java.util.ArrayList;
import java.util.List;

import graph.AbstractGraph;
import graph.UnweightedGraph;

// 九枚硬币翻转问题
// 用最少翻转次数把所有硬币的朝向向下
// 每次翻转会改变以当前硬币为中心，其上硬币、下硬币、左硬币、右硬币共五个硬币的朝向。
// 一共九个硬币，一共512个状态，把每个状态当成顶点，朝向统一向下的状态当成终点
// 实际上求的是两个顶点间的最短路径
// 这里采取的方法是广度优先搜索遍历了所有顶点，BFS会先检索当前顶点的所有邻接顶点，然后不断向外扩散。
// 所以这个方法是可以成功的。
// 另外,Dijkstra和Floyd解决有向图的最短路径，这里不适用。
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
	
	// 返回一个包含Edge对象的线性表
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
	
	// 翻转结点的指定位置和其邻接位置，返回新结点的下标
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
	
	// 状态图实际上是个一维数组，但我们有时候又抽象成二维数组来研究
	// 翻转结点的指定行和列所锁定的位置
	public static void flipACell(char[] node, int row, int column) {
		if (row >= 0 && row <= 2 && column >= 0 && column <= 2)
			if (node[row * 3 + column] == 'H')
				node[row * 3 + column] = 'T';
			else
				node[row * 3 + column] = 'H';
	}
	
	// 根据状态图返回状态的索引
	// 返回结点的下标
	public static int getIndex(char[] node) {
		int result = 0;
		
		for (int i = 0; i < 9; i++)
			if (node[i] == 'T')
				result = result * 2 + 1;
			else
				result = result * 2 + 0;
		
		return result;
	}
	
	// 每个状态对应一个索引index，根据索引可以获得状态图
	// 返回指定下表的结点，例如0返回包含九个H的结点，511返回包含九个T的结点
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
