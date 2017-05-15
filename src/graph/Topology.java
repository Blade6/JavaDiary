package graph;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

// �������򣬴�������ͼ�����������ͼ����Ȧͼ����������ʵӦ�ü��϶�ͼ�Ƿ���Ȧ���жϣ�����ǿ��׳��
// ����(topology)���򣬶�������Ȧ��Ȩͼ��������
// �������һ����vi��vj��·������ô��������vj�ͳ�����vi�ĺ���
public class Topology extends Digraph {

	public static void main(String[] args) {
		Topology demo = new Topology(7);
		demo.init("resources/topology.txt", false);
		System.out.println("��������");
		try {
			demo.topoSort();
			demo.printTopo();
		} catch (CycleFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected Topology(int size) {
		super(size);
	}
	
	public void topoSort() throws CycleFoundException {
		Queue<Vertex> q = new LinkedList<>();
		int counter = 0;
				
		for (int i = 0; i < NUM_VERTICES; i++)
			if (graph[i].indegree == 0)
				q.add(graph[i]);
				
		while (!q.isEmpty()) {
			Vertex v = q.poll();
			v.topNum = ++counter;
					
			for (int i =0; i < v.adj.size(); i++)
				if (--v.adj.get(i).next.indegree == 0)
					q.add(v.adj.get(i).next);
		}
				
		if (counter != NUM_VERTICES)
			throw new CycleFoundException();
	}	
	
	
	// ��ӡ����������
	public void printTopo() {
		LinkedList<Vertex> result = new LinkedList<>();
		result.add(graph[0]);
		for (int i = 1; i < NUM_VERTICES; i++) {
			if (graph[i].topNum > result.peekLast().topNum)
				result.add(graph[i]);
			else if (graph[i].topNum < result.peekFirst().topNum)
				result.addFirst(graph[i]);
			else {
				for (int j = 1; j < result.size(); j++) {
					if (result.get(j).topNum > graph[i].topNum) {
						result.add(j, graph[i]);
						break;
					}
				}
			}
		}
		ListIterator<Vertex> iterator = result.listIterator();
		while (iterator.hasNext()) {
			Vertex v = iterator.next();
			System.out.println(v.name + " " + v.topNum);
		}
	}	
	
}
