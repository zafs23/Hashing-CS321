import java.util.Random;

/**
 * TwinPrimeGenerator class creates twin primes with given iterations.
 * 
 * @author Sajia Zafreen
 *
 */
public class TwinPrimeGenerator {

	private int iteration;

	/**
	 * Constructor
	 * 
	 * @param iteration is how many times the method checks before if a number is
	 *                  prime
	 */
	public TwinPrimeGenerator(int iteration) {
		this.iteration = iteration;
	}

	/**
	 * Returns the larger of the twin primes within the ranges
	 * 
	 * @param from first number in the range
	 * @param to   last number in the range
	 * @return larger of the twin primes
	 */
	public int getTwinPrime(int from, int to) {
		if (from % 2 == 0) {
			from++;
		}
		// pass only the odd numbers
		while (from <= to) {
			if (isPrime(from)) {// first prime
				from += 2;
				if (isPrime(from)) {
					return from; // next prime
				}
			} else {
				from += 2;
			}
		}
		return 0;
	}

	/**
	 * Returns if a number is prime or not using Fermat's theorem to test primality
	 * 
	 * @param number to be checked to be prime
	 * @return boolean value if the given number is prime or not
	 */
	private boolean isPrime(long number) {
		if (number == 0 || number == 1)
			return false;
		if (number == 2)
			return true;
		if (number % 2 == 0)
			return false;

		// long array[] = new long [iteration];
		boolean returnValue = true;

		// because 1st binary value will be ignored it is 2^0
		for (int i = 0; i < iteration; i++) {
			long power = number - 1;// one less than the number
			String binaryString = Long.toBinaryString(power);// need binary of the power

			Random randomNumber = new Random();
			long base = Math.max(2,randomNumber.nextInt((int) number));// base is between 1<a<number
			

			long modNumber = 0;
			// looping through the binary string from left to right
			for (int strIndex = 1; strIndex < binaryString.length(); strIndex++) {
				// always have to do these operations square and then mod
				if (strIndex == 1) {
					modNumber = (long) Math.pow(base, 2);
				} else {
					modNumber = (long) Math.pow(modNumber, 2);
				}
				modNumber = Math.floorMod(modNumber, number);
				if (binaryString.charAt(strIndex) == '1') {
					// but if it is a '1' then we need to multiply the "base" and again mod
					modNumber = modNumber * base;
					modNumber = Math.floorMod(modNumber, number);
				}
			}
			if (modNumber != 1) {
				returnValue = false;
			}
		}
		return returnValue;
	}

	/**
	 * Returns the iterations done to check if a number is prime or not
	 * 
	 * @return iteration
	 */
	public int getIteration() {
		return iteration;
	}

	/**
	 * Sets the iteration to the given iteration
	 * 
	 * @param iteration set to be new iteration
	 */
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

}