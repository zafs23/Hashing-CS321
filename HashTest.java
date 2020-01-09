import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * HashTest class simulates a hash table of linear-hashing and double-hashing
 * using three types of hash objects integer, Long, and String.
 * 
 * @author Sajia Zafreen
 *
 */

public class HashTest {

	private static TwinPrimeGenerator primeNumber;
	private static double loadFactor;

	public static void main(String[] args) {
		primeNumber = new TwinPrimeGenerator(2); // twin prime from two iterations
		int inputNumber = 0;
		int tableSize = primeNumber.getTwinPrime(95500, 96000);
		if (args.length == 2) {
			try {
				loadFactor = Double.parseDouble(args[1]);
				if (loadFactor < 1 && loadFactor > 0) {
					inputNumber = (int) (Math.ceil(loadFactor * tableSize));
				} else {
					System.out.println("Load factor should be a less than 1 and greater than zero ");
					printUsage();
					System.exit(1);
				}
				if (args[0].equals("1")) {
					generalPrint("Integer:java.util.Random", tableSize);
					simulationInteger(inputNumber, tableSize, 0);
				} else if (args[0].equals("2")) {
					generalPrint("Long:System.currentTimeMillis()", tableSize);
					simulationLong(inputNumber, tableSize, 0);

				} else if (args[0].equals("3")) {
					generalPrint("String:File Name -> word-list", tableSize);
					simulationString(inputNumber, tableSize, 0);
				} else {
					printUsage();
					System.exit(1);
				}
			} catch (NumberFormatException e) {
				System.out.println("Load factor should be a number");
				printUsage();
				System.exit(1);
			}
		} else if (args.length == 3) {
			try {
				loadFactor = Double.parseDouble(args[1].toString());
				if (loadFactor < 1 && loadFactor > 0) {
					inputNumber = (int) (Math.ceil(loadFactor * tableSize));
				} else {
					System.out.println("Load factor should be a less than 1 and greater than zero ");
					printUsage();
					System.exit(1);
				}
				int debug = Integer.parseInt(args[2]);
				if (args[0].equals("1")) {
					generalPrint("Integer:java.util.Random", tableSize);
					simulationInteger(inputNumber, tableSize, debug);

				} else if (args[0].equals("2")) {
					generalPrint("Long:System.currentTimeMillis()", tableSize);
					simulationLong(inputNumber, tableSize, debug);

				} else if (args[0].equals("3")) {
					generalPrint("String:File Name -> word-list", tableSize);
					simulationString(inputNumber, tableSize, debug);
				} else {
					printUsage();
					System.exit(1);
				}
			} catch (NumberFormatException e) {
				System.out.println("Load factor and debug should be numbers");
				printUsage();
				System.exit(1);
			}
		} else {
			printUsage();
			System.exit(1);
		}
	}

	/**
	 * Simulates two types of hash tables, linear and double of Integer type
	 * 
	 * @param inputNumber is the highest number of inputs in hash table of the
	 *                    corresponding load factor
	 * @param tableSize   is the size of the hash table
	 * @param debug       is the debug level of the simulation output
	 */
	private static void simulationInteger(int inputNumber, int tableSize, int debug) {
		// lowest time needed for this simulation as duplicates will be very very few or
		// no duplicates
		HashTable<Integer> intLinearTable = new HashTable<Integer>(tableSize, 1); // creating linear hash table
		HashTable<Integer> intDoubleTable = new HashTable<Integer>(tableSize, 2);

		Random random = new Random();
		int intRandom = 0;
		while (intLinearTable.getTotalInsert() < inputNumber || intDoubleTable.getTotalInsert() < inputNumber) {
			intRandom = random.nextInt();

			intLinearTable.insert(intRandom);
			intDoubleTable.insert(intRandom);
		}

		if (debug == 0) {
			zeroDebug(intLinearTable, "Linear");
			zeroDebug(intDoubleTable, "Double");
		}
		if (debug == 1) {
			oneDebug(intLinearTable, "Linear", tableSize);
			oneDebug(intDoubleTable, "Double", tableSize);
		}
	}

	/**
	 * Simulates two types of hash tables, linear and double using Long type
	 * 
	 * @param inputNumber is the highest number of inputs in hash table of the
	 *                    corresponding load factor
	 * @param tableSize   is the size of the hash table
	 * @param debug       is the debug level of the simulation output
	 */
	private static void simulationLong(int inputNumber, int tableSize, int debug) {
		// highest time needed in this simulation as so many duplicates
		HashTable<Long> lngLinearTable = new HashTable<Long>(tableSize, 1); // creating linear hash table
		HashTable<Long> lngDoubleTable = new HashTable<Long>(tableSize, 2);
		Long systemTime;
		while (lngLinearTable.getTotalInsert() < inputNumber || lngDoubleTable.getTotalInsert() < inputNumber) {
			systemTime = System.currentTimeMillis();

			lngLinearTable.insert(systemTime);
			lngDoubleTable.insert(systemTime);
		}

		if (debug == 0) {
			zeroDebug(lngLinearTable, "Linear");
			zeroDebug(lngDoubleTable, "Double");
		}
		if (debug == 1) {
			oneDebug(lngLinearTable, "Linear", tableSize);
			oneDebug(lngDoubleTable, "Double", tableSize);
		}

	}

	/**
	 * Simulates two types of hash tables, linear and double using String type
	 * 
	 * @param inputNumber is the highest number of inputs in hash table of the
	 *                    corresponding load factor
	 * @param tableSize   is the size of the hash table
	 * @param debug       is the debug level of the simulation output
	 */
	private static void simulationString(int inputNumber, int tableSize, int debug) {
		// medium amount of time needed among the three simulations, as it is a word
		// list and may have lot of duplicates
		HashTable<String> strLinearTable = new HashTable<String>(tableSize, 1); // creating linear hash table
		HashTable<String> strDoubleTable = new HashTable<String>(tableSize, 2);

		try {
			String input = new String(Files.readAllBytes(Paths.get("word-list")));
			String[] parseInput = input.split("\\r?\\n");
			String fileString;
			for (int strLen = 0; strLen < parseInput.length; strLen++) {// load Until
				fileString = parseInput[strLen];

				if (strLinearTable.getTotalInsert() < inputNumber || strDoubleTable.getTotalInsert() < inputNumber) {
					strLinearTable.insert(fileString);
					strDoubleTable.insert(fileString);
				} else {
					break;
				}
			}

			if (debug == 0) {
				zeroDebug(strLinearTable, "Linear");
				zeroDebug(strDoubleTable, "Double");
			}
			if (debug == 1) {
				oneDebug(strLinearTable, "Linear", tableSize);
				oneDebug(strDoubleTable, "Double", tableSize);
			}

		} catch (IOException e) {
			System.out.println("File not Found");
			printUsage();
			System.exit(1);
		}
	}

	/**
	 * Prints the table size found in the given range and the data source of the
	 * current simulation
	 * 
	 * @param dataSource is the data type of the simulation
	 * @param tableSize  is the table size of the hash table
	 */
	private static void generalPrint(String dataSource, int tableSize) {
		System.out.println("\nA good table size is found: " + tableSize);
		System.out.println("Data Source Type: " + dataSource);
	}

	/**
	 * Prints a summary of the current simulation
	 * 
	 * @param hashTable is the hash table
	 * @param tableType is the type of hashing
	 */
	private static <T> void zeroDebug(HashTable<T> hashTable, String tableType) {
		double probes = ((double) hashTable.getTotalProbs()) / (double) (hashTable.getTotalInsert());
		int totalInput = hashTable.getTotalInsert() + hashTable.getDuplicateCount();
		System.out.println("\n\n");
		System.out.println("Using " + tableType + " Hashing...");
		System.out.print("Input " + totalInput + " elements");
		System.out.println(" , of which " + hashTable.getDuplicateCount() + " are duplicates");
		System.out.println("Load Factor = " + loadFactor + ", Avg. no. of probes " + probes + "\n\n");
	}

	/**
	 * Prints a summary of the current simulation in the console and prints a dump
	 * file of the current simulation of the current hashing
	 * 
	 * @param hashTable is hash table
	 * @param tableType is the type of hashing
	 * @param tableSize is the size of the hash table
	 */
	private static <T> void oneDebug(HashTable<T> hashTable, String tableType, int tableSize) {
		zeroDebug(hashTable, tableType);
		String fileName = tableType + "-dump";

		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(fileName)); // use FileWriter instead System.setout (PrintStream)
			// then only one file for output stream and the console output is not printed

			for (int i = 0; i < tableSize; i++) {
				if (hashTable.getTableContent(i) != null) {
					out.println("table[" + i + "]: " + hashTable.getTableContent(i).toString());
				}
			}
			out.close(); // must must must use it

		} catch (IOException e) {
			System.out.println("Cannot Open file");
			printUsage();
			System.exit(1);
		}
	}

	/**
	 * Prinsts usage message on the console.
	 */
	private static void printUsage() {
		System.out.println("Usage: $ java HashTest <input type> <load factor> <debug level>");
		System.out.println(
				"<input type> 1, 2, or 3 \n 1: java.util.Random \n 2: System.currentTimeMillis() \n 3: word-list.");
		System.out.println("<load factor> any numbers of 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.98, 0.99");
		System.out.println("optional <debug level> \n 0: print summary of experiment on the console "
				+ "\n 1: print summary of experiment on the console \n\tand print the hash tables with number of duplicates "
				+ "and number of probes into two files linear-dump and double-dump.");
	}

}
