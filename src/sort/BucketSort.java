package sort;

import java.util.ArrayList;

// Ͱ�����ֳƻ�������
public class BucketSort {
	
	private ArrayList<Integer>[] key = new ArrayList[10];
	
	// �������붼����λ������
	// ���ȶԸ�λ����Ȼ���ʮλ�������԰�λ����
	// �ؼ��ּ�Ͱ����0,...,9
	public void bucketSort(int[] nums) {
		for (int i = 1; i <= 3; i++) {
			RadixSort(nums, i);
			collect(nums);
		}
		
	}
	
	// ��������,specialȡֵ������1,2,3,�ֱ��Ӧ����λ��ʮλ����λ����
	public void RadixSort(int[] nums, int special) {
		for (int i = 0; i < nums.length; i++) {
			int tmp = (int) ((nums[i] % Math.pow(10, special)) / Math.pow(10, special - 1));
			if (key[tmp] == null) key[tmp] = new ArrayList<>();
			key[tmp].add(nums[i]);
		}
	}
	
	// �ռ�
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
