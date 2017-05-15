package algorithm;

// 概率素性测试算法，使用概率来判断某个数是不是素数
// 可能出错，但是概率很小
public class IsItPrime {

	public static void main(String[] args) {
		int[] number = new int[]{5,9,36,121,97,100,626,29};
		for (int num : number)
			System.out.println(num + " " + isPrime(num));
	}
	
	/**
	 * The number of witnesses queried in randomized primality test. 
	 */
	public static final int TRIALS = 5;
	
	/**
	 * Method that implements the basic primality test.
	 * If witness does not return 1, n is definitely composite.
	 * Do this by computing a^i (mod n) and looking for
	 * nontrivial square roots of 1 along the way.
	 */
	private static int witness(int a, int i, int n) {
		if (i == 0)
			return 1;
		
		int x = witness(a, i / 2, n);
		if (x == 0)
			return 0;
		
		int y = (x * x) % n;
		if (y == 1 && x != 1 && x != n - 1)
			return 0;
		
		if (i % 2 != 0)
			y = (a * y) % n;
		
		return y;
	}
	
	/**
	 * Randomized primality test.
	 * Adjust TRIALS to increase confidence level.
	 * @param n the number to test.
	 * @return if false, n is definitely not prime.
	 * */
	public static boolean isPrime(int n) {
		java.util.Random r = new java.util.Random();
		
		for (int counter = 0; counter < TRIALS; counter++)
			// r.nextInt(n-4)+2生成(2,n-2)的随机数
			// 此算法针对大于4的数！！！
			if (witness(r.nextInt(n-4) + 2, n - 1, n) != 1)
				return false;
		
		return true;
	}
	
}
