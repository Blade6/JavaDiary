package graph;

import java.util.List;

public interface Graph<V> {

	public int getSize();
	
	public java.util.List<V> getVertices();
	
	public V getVertex(int index);
	
	public int getIndex(V v);
	
	public java.util.List<Integer> getNeighbors(int index);
	
	public int getDegree(int v);
	
	public int[][] getAdjacencyMatrix();
	
	public void printAdjacencyMatrix();
	
	public void printEdges();
	
	public void dfs(int v, int[] parent, List<Integer> searchOrders, boolean[] isVisited);
	
	public AbstractGraph<V>.Tree bfs(int v);
	
}
