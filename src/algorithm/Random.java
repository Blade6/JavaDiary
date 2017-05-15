package algorithm;

// 随机数生成器
public class Random {

	private int state;
	private static final int A = 48271;
	private static final int M = 2147483647;
	private static final int Q = M / A;
	private static final int R = M % A;
	
	public Random() {
		state = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
	}
	
	/**
	 * Return a pseudorandom int, and change the internal state.
	 * @return the pseudorandom int.
	 */
	public int randomInt() {
		int tmpState = A * (state % Q) - R * (state / Q);
		
		if (tmpState >= 0)
			state = tmpState;
		else
			state = tmpState + M;
		
		return state;
	}
	
	/**
	 * Return a pseudorandom double in the open range 0~1
	 * and change the internal state.
	 * @return the pseudorandom double.
	 */
	public double random0_1() {
		return (double) randomInt() / M;
	}
	
}
