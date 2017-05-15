package sort;

public class QuickSort2 {
	
	// 分割线下为快速排序部分
	// 此处给出一个快速选择方法，求出给定数组的第k大的元素，平均时间复杂度为O(N)
	// 当算法终止时，第k个最小元就在位置k-1上(数组下标开始于0)。
	// 此算法破坏了数组原来的排序，如果不希望这样，那么必须要做一份拷贝
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
				// 这里最后一个参数传k而不是传k-i-1的原因在于
				// 这个算法中的k不是表示相对于left和right所划定的区间的第k个，而是整个数组的第k个
				// 要区分相对位置和绝对位置
				quickSelect(a, i + 1, right, k);	
		}
		else
			insertionSort(a, left, right);
	}
	
	/**-------------------分割线-------------------*/

	private static final int CUTOFF = 10;
	
	public static <E extends Comparable<? super E>>
	void quicksort(E[] a) {
		quicksort(a, 0, a.length - 1);
	}
	
	// 三数中值分割法
	// 此程序实际上间接排序了3个数
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
			// 枢纽元
			E pivot = median3(a, left, right);
			
			// 利用了三数中值分割法的间接作用，分割阶段实际上要处理得是left+1到right-2之间的部分
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
	
	// 交换引用
	private static <E extends Comparable<? super E>>
	void swapReferences(E[] a, int p, int q) {
		E tmp = a[p];
		a[p] = a[q];
		a[q] = tmp;
	}
	
	// 插入排序
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
