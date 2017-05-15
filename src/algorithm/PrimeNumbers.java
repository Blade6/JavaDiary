package algorithm;

import java.util.ArrayList;

/*
 * 求出n以内的所有素数 
 * n可能是千万级别
 * 故此处不打印所有素数，只返回素数个数
 */
public class PrimeNumbers {

	//O(N*N^(1/2))级别的算法，也就是O(N^(3/2))的算法
	public long getPrime(long n) {
		long count = 0;
		long number = 2;
		
		while (number <= n) {
			boolean isPrime = true;
			
			for (long divisor = 2; divisor <= (int)(Math.sqrt(number)); divisor++) {
				if (number % divisor == 0) {
					isPrime = false;
					break;
				}
			}
			
			if (isPrime) ++count;
			++number;
		}
		
		return count;
	}
	
	/*
	 * 不需要检测从2,3,4...,sqrt(n)的所有数能否被i整除
	 * 只需要检测从2到sqrt(n)的所有素数能否被i整除 
	 * 原理：若i不是素数，则存在一个素数p，满足i=pq且p<=q
	 * 此原理可以用反证法证明
	 */
	// O(N*N^(1/2)/logN)级别的算法，也就是O(N^(3/2)/logN)的算法
	public long getPrimeEfficient(long n) {
		java.util.ArrayList<Long> list = new ArrayList<>();
		
		long count = 0;
		long number = 2;
		long squareRoot = 1;
		
		while (number <= n) {
			boolean isPrime = true;
			
			if (squareRoot * squareRoot < number) squareRoot++;
			
			for (int k = 0; k < list.size() && list.get(k) <= squareRoot; k++) {
				if (number % list.get(k) == 0) {
					isPrime = false;
					break;
				}
			}
			
			if (isPrime) {
				count++;
				list.add(number);
			}
			
			number++;
		}
		
		return count;
	}
	
	/*
	 * 将从2开始到n的素数的倍数都置为false，因为素数的倍数不会是素数
	 * 最后仍然是true的就是淘剩下的素数。
	 */
	//上限为O(N*N^(1/2)/logN)，但是比上一个算法要快
	public int sieveOfEratoshenes(int n) {
		boolean[] primes = new boolean[n+1];
		
		for (int i = 0; i < primes.length; i++) {
			primes[i] = true;
		}
		
		for (int k = 2; k <= n; k++) {
			if (primes[k]) {
				for (int i = k; i <= n / k; i++) {
					primes[k * i] = false;
				}
			}
		}
		
		int count = 0;
		for (int i = 2; i < primes.length; i++) {
			if (primes[i]) ++count;
		}
		
		return count;
	}

}
