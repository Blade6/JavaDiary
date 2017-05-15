package sort;

public class BubbleSort {

	//ԭʼ��ð������,O(N^2)
	public static void originalBubbleSort(int[] list) {
		int temp;
		for (int k = 1; k < list.length; k++) {
			for (int i = 0; i < list.length - k; i++) {
				if (list[i] > list[i+1]) {
					temp = list[i+1];
					list[i+1] = list[i];
					list[i] = temp;
				}
			}
		}
	}
	
	//�Ľ����ð�������������£�ֻ��Ҫһ�α�������ȷ���������ź���
	//������O(N)��������O(N^2)
	public static void bubbleSort(int[] list) {
		boolean needNextPass = true;
		int temp;
		
		for (int k = 1; k < list.length && needNextPass; k++) {
			needNextPass = false;
			for (int i = 0; i < list.length - k; i++) {
				if (list[i] > list[i+1]) {
					temp = list[i+1];
					list[i+1] = list[i];
					list[i] = temp;
					needNextPass = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] list = {2, 3, 2, 5, 6, -2, 3, 14, 12};
		bubbleSort(list);
		for (int i : list)
			System.out.print(i + " ");
	}

}
