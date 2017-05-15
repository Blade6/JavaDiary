package algorithm;

import java.util.ArrayList;

/*
 * ���n���ڵ��������� 
 * n������ǧ�򼶱�
 * �ʴ˴�����ӡ����������ֻ������������
 */
public class PrimeNumbers {

	//O(N*N^(1/2))������㷨��Ҳ����O(N^(3/2))���㷨
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
	 * ����Ҫ����2,3,4...,sqrt(n)���������ܷ�i����
	 * ֻ��Ҫ����2��sqrt(n)�����������ܷ�i���� 
	 * ԭ����i���������������һ������p������i=pq��p<=q
	 * ��ԭ������÷�֤��֤��
	 */
	// O(N*N^(1/2)/logN)������㷨��Ҳ����O(N^(3/2)/logN)���㷨
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
	 * ����2��ʼ��n�������ı�������Ϊfalse����Ϊ�����ı�������������
	 * �����Ȼ��true�ľ�����ʣ�µ�������
	 */
	//����ΪO(N*N^(1/2)/logN)�����Ǳ���һ���㷨Ҫ��
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
