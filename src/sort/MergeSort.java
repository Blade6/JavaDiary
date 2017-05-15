package sort;

public class MergeSort {

	/** The method for sorting the numbers */
	public static void mergeSort(int[] list){
		if (list.length > 1) {
			int[] leftHalf = new int[list.length / 2];
			System.arraycopy(list, 0, leftHalf, 0, list.length / 2);
			mergeSort(leftHalf);
			
			int rightHalfLength = list.length - list.length / 2;
			int[] rightHalf = new int[rightHalfLength];
			System.arraycopy(list, list.length / 2, 
					rightHalf, 0, rightHalfLength);
			mergeSort(rightHalf);
			
			int[] temp = merge(leftHalf, rightHalf);
			System.arraycopy(temp, 0, list, 0, temp.length);
		}
	}
	
	/** Merge two sorted lists */
	public static int[] merge(int[] list1, int[] list2){
		int[] temp = new int[list1.length + list2.length];
		
		int current1 = 0;//Current index in list1
		int current2 = 0;//Current index in list2
		int current3 = 0;//Current index in list3
		
		while (current1 < list1.length && current2 < list2.length) {
			if (list1[current1] < list2[current2])
				temp[current3++] = list1[current1++];
			else
				temp[current3++] = list2[current2++];
		}
		
		while (current1 < list1.length)
			temp[current3++] = list1[current1++];
		
		while (current2 < list2.length)
			temp[current3++] = list2[current2++];
		
		return temp;
	}
	
	public static void main(String[] args) {
		int[] list = {2, 3, 2, 5, 6, 1, -2, 3, 14, 12};
		mergeSort(list);
		for (int i = 0; i < list.length; i++)
			System.out.print(list[i] + " ");
	}

}
