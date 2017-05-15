package sort;

// ��������
public class InsertSort {

	// ֱ�Ӳ�������
	public void insertSort(int[] nums) {
		int j, tmp;
		for (int i = 1; i < nums.length; i++) {
			tmp = nums[i];
			// �Ӻ���ǰѰ�Ҳ���λ��
			for (j = i; j > 0 && tmp < nums[j-1] ; j--)
				nums[j] = nums[j-1];
			nums[j] = tmp;
		}
	}
	
	// �۰��������
	public void BinaryInsertSort(int[] nums) {
		int tmp, low, high, mid;
		for (int i = 1; i < nums.length; i++) {
			tmp = nums[i];
			low = 1;
			high = i - 1;
			while (low <= high) {
				mid = (low + high) / 2;
				if (tmp < nums[mid]) high = mid - 1;
				else low = mid + 1;
			}
			for (int j = i - 1; j >= high + 1; j--)
				nums[j+1] = nums[j];
			nums[high+1] = tmp;
		}
	}
	
}
