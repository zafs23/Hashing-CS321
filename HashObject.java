/**
 * Construct a generic hash object
 * @author Sajia Zafreen
 *
 * @param <T>
 */
public class HashObject<T> {

	Object object;
	int frequency;
	int probCount;
	

	/**
	 * Constructor
	 * @param object the object to be inserted in the hash table
	 */
	public HashObject(Object object) {
		this.object = new Object();
		this.object = object;
		this.frequency =0;
		this.probCount =0;
	}
	
	/**
	 * Constructor
	 * @param frequency of same hash object in the hash table
	 * @param probCount number of probes of this hash object before entry in the hash table
	 * @param object the object to be inserted in the hash table
	 */
	public HashObject(int frequency, int probCount, Object object) {
		this.object = new Object();
		this.object = object;
		this.frequency = frequency;
		this.probCount = probCount;
	}

	/**
	 * Returns the Object itself
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * Sets the object to object
	 * @param object to be set to new object
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * Returns object frequency in the hash table
	 * @return
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Sets the object's frequency
	 * @param frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * Returns the number of probes of the object
	 * @return the number of probes
	 */
	public int getProbCount() {
		return probCount;
	}

	/**
	 * Sets the number of probes
	 * @param probCount set to be the new number of probes
	 */
	public void setProbCount(int probCount) {
		this.probCount = probCount;
	}
	
	/**
	 * Returns the key which is the object's hash code
	 * @return the hash code of the object
	 */
	public int getKey() {
		return object.hashCode();
	}
	
	/**
	 * Increments the frequency of the hash object in the table
	 * @return
	 */
	public int incrementFrequency() {
		return frequency++;
	}
	
//	public int incrementProbCount() { // do not need this as count number of probs only when only new insertions
//		return probCount ++;
//	}
	
	@Override
	public boolean equals(Object key) { 
		return this.object.equals(key);
	}

	@Override
	public String toString() {
		return this.object.toString()+" "+frequency+" "+probCount;
	}

}
