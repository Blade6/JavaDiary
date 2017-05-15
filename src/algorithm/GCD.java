package algorithm;

//求最大公约数，O(logN)级
public class GCD {

	public static int gcd(int m, int n) {
		if (m % n == 0) return n;
		else return gcd(n, m % n);
	}

}
