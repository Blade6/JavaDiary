package graph;

import java.util.ArrayList;
import java.util.List;

// ϡ��ͼ�Ķ���
public class Vertex {

	public String name; // ��������
	public boolean known; // ��־λ���жϵ�ǰ�����Ƿ񱻴���
	public int dist; // ��Դ�㵽��ǰ����ľ��룬�������Ȩͼ����Ϊ·����Ȩ֮�ͣ��������Ȩͼ����Ϊ·���漰�ı���
	public Vertex path; // ��Dijkstra�㷨��������¼����ǰ�����Ȩ��С���ڽӶ���
	public int indegree; // ��ȣ�����v����ȶ���Ϊ��(u,v)������
	public int topNum; // ���˱�ţ��ڷ���������ʱ��������ͨ��������ֲ�ͬ����
	public List<MyEdge> adj; // �ڽӱ�ϡ��ͼ�ʺϲ������ַ�ʽ���ռ�����ΪO(|E|+|V|)��EΪ������VΪ������
	
	public Vertex() {
		adj = new ArrayList<>();
	}
	
}
