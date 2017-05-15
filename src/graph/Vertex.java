package graph;

import java.util.ArrayList;
import java.util.List;

// 稀疏图的顶点
public class Vertex {

	public String name; // 顶点名称
	public boolean known; // 标志位，判断当前顶点是否被处理
	public int dist; // 从源点到当前顶点的距离，如果是有权图，则为路径的权之和；如果是无权图，则为路径涉及的边数
	public Vertex path; // 在Dijkstra算法中用来记录到当前顶点的权最小的邻接顶点
	public int indegree; // 入度，顶点v的入度定义为边(u,v)的条数
	public int topNum; // 拓扑编号，在非拓扑排序时，用于普通编号以区分不同顶点
	public List<MyEdge> adj; // 邻接表，稀疏图适合采用这种方式，空间需求为O(|E|+|V|)，E为边数，V为顶点数
	
	public Vertex() {
		adj = new ArrayList<>();
	}
	
}
