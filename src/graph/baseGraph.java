package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

// ͼ�Ļ���
public abstract class baseGraph {

	protected int NUM_VERTICES; // ������
	protected int NUM_EDGES = 0; // ����
	protected Vertex[] graph;
	protected static final int INFINITY = Integer.MAX_VALUE; // �����

	// ��������Ҫ�Ķ���
	protected baseGraph(int size) {
		this.NUM_VERTICES = size;
		graph = new Vertex[size];
		for (int i = 0; i < size; i++) {
			graph[i] = new Vertex();
			graph[i].name = "v" + (i+1);
			graph[i].topNum = i + 1;
		}
	}	
	
	// ������������֮���ϵ���ļ���ʼ��ͼ��isWeight��ʾ�Ƿ���Ȩͼ
	public void init(String filename, boolean isWeight) {
		try {
			File file = new File(filename);
			Scanner input = new Scanner(file);
			String line;
			while (input.hasNext()) {
				line = input.nextLine();
				String[] words = line.split("\\s+");
				Vertex vetex = find(words[0]);
				if (vetex != null) {
					if (isWeight) {
						for (int i = 1; i < words.length; i+=2) {
							vetex.adj.add(new MyEdge(find(words[i]), 
								Integer.parseInt(words[i+1])));
							++NUM_EDGES;
						}
					} else {
						for (int i = 1; i < words.length; i++) {
							vetex.adj.add(new MyEdge(find(words[i])));
							++NUM_EDGES;
						}
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// ���ݽ�����ֲ��ҽ��
	public Vertex find(String name) {
		for (int i = 0; i < NUM_VERTICES; i++)
			if (graph[i].name.equals(name))
				return graph[i];
		return null;
	}
	
	// Ѱ��δ֪���Ҿ�����Сdist�Ķ���
	public Vertex findSmall() {
		Vertex vertex = new Vertex();
		vertex.dist = INFINITY;
		for (int i = 0; i < NUM_VERTICES; i++) {
			if (graph[i].dist < vertex.dist && !graph[i].known)
				vertex = graph[i];
		}
		return vertex;
	}
	
	// BFS:breadth-first search �����������
	// �࿪ʼ���������Щ�������ȱ���ֵ������Զ����Щ���������ֵ
	// ������ͨͼ������Ƿ���ͨͼ��Ҫ�Դ������޸�
	public void BFS(Vertex s) {
		LinkedList<Vertex> q = new LinkedList<>();
		for (int i = 0; i < NUM_VERTICES; i++)
			graph[i].known = false;
		
		System.out.print(s.name);
		s.known = true;
		q.add(s);
		
		while (!q.isEmpty()) {
			Vertex v = q.poll();
			for (int i = 0; i < v.adj.size(); i++) {
				if (!v.adj.get(i).next.known) {
					System.out.print(" -> " + v.adj.get(i).next.name);
					v.adj.get(i).next.known = true;
					q.add(v.adj.get(i).next);
				}
			}
		}
	}
	
	// DFS:depth-first search
	public void DFS(Vertex s) {
		for (int i = 0; i < NUM_VERTICES; i++)
			graph[i].known = false;
		
		DFS_Main(s);
	}
	
	private void DFS_Main(Vertex v) {
		System.out.print(v.name + " ");
		v.known = true;
		for (int i = 0; i < v.adj.size(); i++)
			if (!v.adj.get(i).next.known)
				DFS_Main(v.adj.get(i).next);
	}
	
}
