package sort;

import DataStructure.Heap;

public class HeapSort {

	/** Heap sort method */
	public static <E extends Comparable> void heapSort(E[] list) {
		// Create a Heap of integers
		Heap<E> heap = new Heap<>();
		
		// Add elements to the heap
		for (int i = 0; i < list.length; i++)
			heap.add(list[i]);
		
		// Remove elements from the heap
		for (int i = list.length - 1; i >= 0; i--)
			list[i] = heap.remove();
	}
	
	public static void main(String[] args) {
		Integer[] list = {2, 3, 2, 5, 6, 1, -2, 3, 14, 12};
		heapSort(list);
		for (int i = 0; i < list.length; i++)
			System.out.print(list[i] + " ");
	}

}