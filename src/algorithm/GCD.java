package algorithm;

//�����Լ����O(logN)��
public class GCD {

	public static int gcd(int m, int n) {
		if (m % n == 0) return n;
		else return gcd(n, m % n);
	}

}
