package sort;

public class ShellSort {

	public void shellsort(int[] nums) {
		int j;
		
		// ��������Ϊh(1),h(2),...h(t)
		// h(t) = N/2, NΪԪ�ظ���,h(k) = h(k+1)/2,k=1...t-1
		// ������Ϊshell���˽���ʹ�ã���Ч������̫��
		for (int gap = nums.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < nums.length; i++) {
				int tmp = nums[i];
				for (j = i; j >= gap && tmp < nums[j - gap]; j-=gap)
					nums[j] = nums[j-gap];
				nums[j] = tmp;
			}
		}
	}
	
}
