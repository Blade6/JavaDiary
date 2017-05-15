package sort;

public class ShellSort {

	public void shellsort(int[] nums) {
		int j;
		
		// 增量序列为h(1),h(2),...h(t)
		// h(t) = N/2, N为元素个数,h(k) = h(k+1)/2,k=1...t-1
		// 此增量为shell本人建议使用，但效果并不太好
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
