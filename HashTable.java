import java.util.Arrays;

/**
 * HashTable class is the hash table where generic hash objects are inserted,
 * deleted or searched
 * 
 * @author Sajia Zafreen
 *
 * @param <T>
 */
public class HashTable<T> {

	private HashObject<T>[] hashTable;
	private int tableType; // 1 for linear, 2 for double
	private int tableSize;

	private enum indexType {
		NIL, DELETED, OCCUPIED
	};

	private indexType indexCondition[];
	private int totalProbs; // for full hashTable for each of the insertion
	private int totalInsert;
	private int duplicateCount;

	/**
	 * Constructor
	 * 
	 * @param tableSize is the table size of the hash table
	 * @param tableType is the hash object's key type
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int tableSize, int tableType) {
		hashTable = (HashObject<T>[]) new HashObject[tableSize];
		this.tableType = tableType;
		this.tableSize = tableSize;
		this.totalProbs = 0;
		this.totalInsert = 0;
		this.duplicateCount = 0;
		indexCondition = new indexType[tableSize]; // do we need to initialize a the hashObjects here : no
		for (int i = 0; i < tableSize; i++) {
			indexCondition[i] = indexType.NIL;
		}
	}

	/**
	 * Returns the hash value of modulus operation on hash key of hash objects
	 * 
	 * @param hashObject is the hash object
	 * @param tableSize  is the size of the hash table
	 * @return the hash value from modulus operation on hash key
	 */
	private int modOperation(HashObject<T> hashObject, int tableSize) {
		int hashValue = hashObject.getKey() % tableSize;
		if (hashValue < 0) {
			hashValue += tableSize;
		}
		return hashValue;
	}

	/**
	 * Returns value of primary hash function of the object key
	 * 
	 * @param hashObject is the object to be hashed in the hash table
	 * @return the primary hash value of the key
	 */
	private int primaryHash(HashObject<T> hashObject) {
		return modOperation(hashObject, this.tableSize);
	}

	/**
	 * Returns the secondary hash value of the object key
	 * 
	 * @param hashObject is the object to be hashed in the hash table
	 * @return the secondary hash value of the key
	 */
	private int secondaryHash(HashObject<T> hashObject) { // vary for different double hashing
		return 1 + modOperation(hashObject, this.tableSize - 2); // project
	}

	/**
	 * Returns hash table index for double hashing
	 * 
	 * @param hashObject is the object to be hashed in the hash table
	 * @param index      is the index of table from where the hash index is
	 *                   generated using hash functions
	 * @return the hash table index for double hashing
	 */
	private int doubleHashIndex(HashObject<T> hashObject, int index) { // mod operation returned for both of them is
																		// positive
		return ((primaryHash(hashObject) + index * (secondaryHash(hashObject))) % this.tableSize);
	}

	/**
	 * Returns the hash table index for linear hashing
	 * 
	 * @param hashObject is the object to be hashed in the hash table
	 * @param index      is the index of the table from where the hash index is
	 *                   generated using hash functions
	 * @return the hash table index for linear hashing
	 */
	private int linearHashIndex(HashObject<T> hashObject, int index) {
		return ((primaryHash(hashObject) + index) % this.tableSize);
	}

	/**
	 * Inserts an object to the hash table
	 * 
	 * @param object is the object to hashed in the hash table
	 */
	public void insert(T object) {
		int localCount = 0;
		HashObject<T> hashObject = new HashObject<T>(object);
		int index = 0;
		do {
			int j = 0; // will change depending on index

			if (tableType == 1) {
				j = linearHashIndex(hashObject, index);
				localCount++;// either of these will work
			}
			if (tableType == 2) {
				j = doubleHashIndex(hashObject, index);
				localCount++; // either of these will increment
			}
			if (indexCondition[j] == indexType.NIL || indexCondition[j] == indexType.DELETED) {
				hashTable[j] = hashObject;
				indexCondition[j] = indexType.OCCUPIED;
				// hashTable[j].incrementFrequency(); don want to make duplicate
				totalInsert++;
				totalProbs = totalProbs + localCount;// real insertion
				hashTable[j].setProbCount(localCount); // each insertion e ae poriman probing hoise
				// return j;
				return;
			} else { // means same hashCode // kokhnoi emon kisu thakbe na j same j but different
						// hashCode ?
				if (hashTable[j].getKey() == hashObject.getKey()) {
					if (hashTable[j].getObject().equals(object)) {
						hashTable[j].incrementFrequency();
						duplicateCount++;
						// return 0;// no more probing
						return;
					}
				} // no else here because, if key same, we also need to increase index
				index++;
			}
			// totalProbs = totalProbs+ localCount; // only increment if real insert
		} while (index < tableSize);
		// return -1;
		return;
	}

	/**
	 * Returns the hash table
	 * 
	 * @return returns the hash table
	 */
	public HashObject<T>[] getHashTable() {
		return hashTable;
	}

	/**
	 * Returns the hash table type
	 * 
	 * @return the hash table type
	 */
	public int getTableType() {
		return tableType;
	}

	/**
	 * Sets the hash table type
	 * 
	 * @param tableType is the table type to be set
	 */
	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	/**
	 * Returns the hash object of the given index
	 * 
	 * @param index is the index of the hash table whose key is to be returned
	 * @return the hash object of the given index
	 */
	public HashObject<T> getTableContent(int index) {
		return hashTable[index];
	}

	/**
	 * Returns the hash tale size
	 * 
	 * @return the hash table size
	 */
	public int getTableSize() {
		return tableSize;
	}

	/**
	 * Sets the hash table size
	 * 
	 * @param tableSize is the size to be set to
	 */
	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	/**
	 * Returns the total inserts in the hash table
	 * 
	 * @return the total number of inserts in the hash table
	 */
	public int getTotalInsert() {
		return this.totalInsert;
	}

	/**
	 * Returns number of duplicates in the hash table
	 * 
	 * @return number of duplicates in the hash table
	 */
	public int getDuplicateCount() {
		return this.duplicateCount;
	}

	/**
	 * Returns the number of total probes of inserted objects in the hash table
	 * 
	 * @return the number of total probes of the inserted objects in the hash table
	 */
	public int getTotalProbs() {
		return this.totalProbs;
	}

	/**
	 * Returns if the hash table is empty or not
	 * 
	 * @return if the hash table empty
	 */
	public boolean isEmpty() {
		return this.totalInsert == 0;
	}

	@Override
	public String toString() {
		return Arrays.toString(hashTable);
	}

}
