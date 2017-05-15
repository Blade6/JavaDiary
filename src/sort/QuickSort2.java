package sort;

public class QuickSort2 {
	
	// �ָ�����Ϊ�������򲿷�
	// �˴�����һ������ѡ�񷽷��������������ĵ�k���Ԫ�أ�ƽ��ʱ�临�Ӷ�ΪO(N)
	// ���㷨��ֹʱ����k����СԪ����λ��k-1��(�����±꿪ʼ��0)��
	// ���㷨�ƻ�������ԭ�������������ϣ����������ô����Ҫ��һ�ݿ���
	private static <E extends Comparable<? super E>>
	void quickSelect(E[] a, int left, int right, int k) {
		if (left + CUTOFF <= right) {
			E pivot = median3(a, left, right);
			
			int i = left, j = right - 1;
			for ( ; ; ) {
				while (a[++i].compareTo(pivot) < 0) ;
				while (a[--j].compareTo(pivot) > 0) ;
				if (i < j)
					swapReferences(a, i, j);
				else
					break;
			}
			
			swapReferences(a, i, right - 1);
			
			if (k <= i)
				quickSelect(a, left, i - 1, k);
			else if (k > i + 1)
				// �������һ��������k�����Ǵ�k-i-1��ԭ������
				// ����㷨�е�k���Ǳ�ʾ�����left��right������������ĵ�k����������������ĵ�k��
				// Ҫ�������λ�ú;���λ��
				quickSelect(a, i + 1, right, k);	
		}
		else
			insertionSort(a, left, right);
	}
	
	/**-------------------�ָ���-------------------*/

	private static final int CUTOFF = 10;
	
	public static <E extends Comparable<? super E>>
	void quicksort(E[] a) {
		quicksort(a, 0, a.length - 1);
	}
	
	// ������ֵ�ָ
	// �˳���ʵ���ϼ��������3����
	private static <E extends Comparable<? super E>>
	E median3(E[] a, int left, int right) {
		int center = (left + right) / 2;
		if (a[center].compareTo(a[left]) < 0)
			swapReferences(a, left, center);
		if (a[right].compareTo(a[left]) < 0)
			swapReferences(a, left, right);
		if (a[right].compareTo(a[center]) < 0)
			swapReferences(a, center, right);
		
		swapReferences(a, center, right - 1);
		return a[right - 1];
	}
	
	private static <E extends Comparable<? super E>>
	void quicksort(E[] a, int left, int right) {
		if (left + CUTOFF <= right) {
			// ��ŦԪ
			E pivot = median3(a, left, right);
			
			// ������������ֵ�ָ�ļ�����ã��ָ�׶�ʵ����Ҫ�������left+1��right-2֮��Ĳ���
			int i = left, j = right - 1;
			for ( ; ; ) {
				while (a[++i].compareTo(pivot) < 0) ;
				while (a[--j].compareTo(pivot) > 0) ;
				if (i < j)
					swapReferences(a, i, j);
				else
					break;
			}
			
			swapReferences(a, i, right - 1);
			
			quicksort(a, left, i - 1);
			quicksort(a, i + 1, right);
		}
		else
			insertionSort(a, left, right);
	}
	
	// ��������
	private static <E extends Comparable<? super E>>
	void swapReferences(E[] a, int p, int q) {
		E tmp = a[p];
		a[p] = a[q];
		a[q] = tmp;
	}
	
	// ��������
	public static <E extends Comparable<? super E>>
	void insertionSort(E[] a, int left, int right) {
		int j;
		for (int p = left; p <= right; p++) {
			E tmp = a[p];
			for (j = p; j > left && tmp.compareTo(a[j-1]) < 0; j--)
				a[j] = a[j-1];
			a[j] = tmp;
		}
	}
	
}
