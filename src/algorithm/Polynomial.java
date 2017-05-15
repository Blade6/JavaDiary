package algorithm;

public class Polynomial {
	//求解f(X)=a0*x^0+a1*x^1+...aN*x^N
	// Horner原则
	public static int horner(int[] a, int n, int x) {
		int poly = 0;
		for (int i = n; i >= 0; i--) {
			poly = x * poly + a[i];
		}
		return poly;
	}
	
}
