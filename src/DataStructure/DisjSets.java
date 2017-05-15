package DataStructure;

// 不相交集类，Disjoint Sets
public class DisjSets {

	private int[] s;
	
	public DisjSets(int numElements) {
		s = new int[numElements];
		for (int i = 0; i < s.length; i++)
			s[i] = -1;
	}
	
	// 按秩(估计高度)求并
	public void union(int root1, int root2) {
		if (s[root2] < s[root1])
			s[root1] = root2;
		else {
			if (s[root1] == s[root2])
				s[root1]--;
			s[root2] = root1;
		}
	}
	
	// 《数据结构与算法分析》P226 图8-14
	// 返回包含x的树的根
	public int find(int x) {
		if (s[x] < 0)
			return x;
		else
			// 普通查找
			//return find(s[x]);
			// 路径压缩
			return s[x] = find(s[x]);
	}
	
}
