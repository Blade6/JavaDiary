package sort;

// �鲢����������Java���ø�ֵ������
public class MergeSort2 {

	public static <E extends Comparable<? super E>>
	void mergeSort(E[] a) {
		E[] tmpArray = (E[]) new Comparable[a.length];
		
		mergeSort(a, tmpArray, 0, a.length-1);
	}
	
	private static <E extends Comparable<? super E>>
	void mergeSort(E[] a, E[] tmpArray, int left, int right) {
		if (left < right) {
			int center = (left + right) / 2;
			mergeSort(a, tmpArray, left, center);
			mergeSort(a, tmpArray, center+1, right);
			merge(a, tmpArray, left, center+1, right);
		}
	}
	
	private static <E extends Comparable<? super E>>
	void merge(E[] a, E[] tmpArray, int leftPos, int rightPos, int rightEnd) {
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;
		
		while (leftPos <= leftEnd && rightPos <= rightEnd) {
			if (a[leftPos].compareTo(a[rightPos]) < 0)
				tmpArray[tmpPos++] = a[leftPos++];
			else
				tmpArray[tmpPos++] = a[rightPos++];
		}
		
		while (leftPos <= leftEnd)
			tmpArray[tmpPos++] = a[leftPos++];
		
		while (rightPos <= rightEnd)
			tmpArray[tmpPos++] = a[rightPos++];
		
		// Copy tmpArray back ��������ΪJava���ø�ֵ�����ԣ�ֻ�Ǹ�ֵ���ڴ��ַ����ǳ���ơ�
		for (int i = 0; i < numElements; i++, rightEnd--)
			a[rightEnd] = tmpArray[rightEnd];
	}
	
}
