package sort;

import java.util.ArrayList;

// 桶排序，又称基数排序
public class BucketSort {
	
	private ArrayList<Integer>[] key = new ArrayList[10];
	
	// 假设输入都是三位数以内
	// 首先对个位排序，然后对十位排序，最后对百位排序
	// 关键字即桶，是0,...,9
	public void bucketSort(int[] nums) {
		for (int i = 1; i <= 3; i++) {
			RadixSort(nums, i);
			collect(nums);
		}
		
	}
	
	// 单趟排序,special取值可以是1,2,3,分别对应按个位、十位、百位排序。
	public void RadixSort(int[] nums, int special) {
		for (int i = 0; i < nums.length; i++) {
			int tmp = (int) ((nums[i] % Math.pow(10, special)) / Math.pow(10, special - 1));
			if (key[tmp] == null) key[tmp] = new ArrayList<>();
			key[tmp].add(nums[i]);
		}
	}
	
	// 收集
	public void collect(int[] nums) {
		int index = -1;
		for (int i = 0; i < key.length; i++)
			if (key[i] != null)
				for (int j = 0; j < key[i].size(); j++) 
					nums[++index] = key[i].get(j).intValue();
		clear();
	}
	
	public void clear() {
		for (int i = 0; i < key.length; i++)
			key[i] = null;
	}
	
	public static void main(String[] args) {
		int[] nums = new int[]{278,109,63,930,589,184,505,269,8,83};
		BucketSort demo = new BucketSort();
		demo.bucketSort(nums);
		for (int i = 0; i < nums.length; i++)
			System.out.print(nums[i] + " ");
	}
	
}
