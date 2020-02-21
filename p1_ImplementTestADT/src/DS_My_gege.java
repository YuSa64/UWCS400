/**
 * Author: Gege Zhang 
 * Email: gzhang85@wisc.edu 
 * Lecture Number: 002 Program
 * Description: This program is to practice for implement, test, and compare alternate implementations of an abstract data type 
 */

/**
 * 
 * DS_My - This class is to is to implement your own data structure. This class
 * implements DataStructureADT.
 * 
 * @author Gege Zhang(2020)
 *
 */
public class DS_My_gege implements DataStructureADT<String, String> {

	/**
	 * define a private inner class for storing key and value as a pair
	 * 
	 * @author Gege Zhang
	 *
	 * @param <K> key
	 */
	private class Paire<K> {
		// create private file for key and value
		private String key;
		private String value;

		/**
		 * constructor for inner private class pair to initial the data
		 * 
		 * @param k key
		 * @param v value
		 */
		private Paire(String key, String value) {
			// initial the key and value
			this.key = key;
			this.value = value;
		}
	}

	// field here to store data pairs
	private Paire[] pairs;
	// initialize the size
	int size = 0;

	/**
	 * constructor of DS_My class to initial the pairs number
	 */
	public DS_My_gege() {
		// initial pair number to be 1,000,000
		pairs = new Paire[1000000];

	}

	/**
	 * Add the key,value pair to the data structure and increases size.If key is
	 * null, throws IllegalArgumentException("null key"), If key is already in data
	 * structure, throws RuntimeException("duplicate key")can accept and insert null
	 * values
	 * 
	 * @param key   the key
	 * @param value the value
	 * 
	 * @throws IllegalArgumentException if key is null
	 * @throws RuntimeException         if key is already in data structure
	 */
	@Override
	public void insert(String key, String value) {

		// check if key is null
		if (key == null) {
			// if key is null, throw IllegalArgumentException with error message
			throw new IllegalArgumentException("null key");
		}
		// check if the added new key already in data structure
		if (contains(key)) {
			// if it already in the data structure, throw RuntimeException with error
			// message
			throw new RuntimeException("duplicate key");
		}
		// create a new paire object
		Paire newPair = new Paire(key, value);
		// insert new Pair at the given index of pair
		pairs[size] = newPair;
		// increase the size by one
		size++;

	}

	/**
	 * If key is found, Removes the key from the data structure and decreases size.
	 * If key is null, throws IllegalArgumentException("null key") without
	 * decreasing size. If key is not found, returns false.
	 * 
	 * @param key the key
	 * @throws IllegalArgumentException If key is null
	 * @return true if key is found and remove, otherwise false
	 */
	@Override
	public boolean remove(String key) {
		// check if key is null
		if (key == null) {
			// if key is null throw IllegalArgumentException with error message
			throw new IllegalArgumentException("null key");
		}

		// use for loop to trace the element in the pair array to found key
		for (int i = 0; i < size; i++) {
			// check if the key is found
			if (pairs[i].key.equals(key)) {
				// if the key is found
				// use for loop to remove the key and copy the following element forward
				for (int j = i; j < size; j++) {
					int k = j + 1;
					// exchange the element in the array with previous one
					pairs[j] = pairs[k];
				}
				// delete the last element in the pair array
				pairs[size - 1] = null;
				// decrease the size by one
				size--;
				return true;
			}
		}
		// if key is not found, returns false.
		return false;
	}

	/**
	 * Returns the value associated with the specified key. get - does not remove
	 * key or decrease size. return null if key is not null and is not found in data
	 * structure. If key is null, throws IllegalArgumentException("null key")
	 * 
	 * @param key the key
	 * @throws IllegalArgumentException If key is null
	 * @return returns the value associated with the specified key get and null if
	 *         key is not null
	 */
	@Override
	public String get(String key) {
		String get = null;
		// check if key is null
		if (key == null) {
			// if key is null throw IllegalArgumentException with error message
			throw new IllegalArgumentException("null key");
		}

		// use for loop to trace the element in the pair array to found key
		for (int i = 0; i < size; i++) {
			// check if the key is found
			if (pairs[i].key.equals(key)) {
				// set it value to get
				get = pairs[i].value;
			}
		}
		// return the founded element
		return get;

	}

	/**
	 * Returns true if the key is in the data structure. Returns false if key is
	 * null or not present
	 * 
	 * @param key the key
	 * @return returns true if the key is in the data structure. Returns false if
	 *         key is null or not present
	 */
	@Override
	public boolean contains(String key) {
		// check if key is null
		if (key == null) {
			// if key is null
			return false;
		}

		// use for loop to trace the element in the pair array to found key
		for (int i = 0; i < size; i++) {
			// check the key is found
			if (pairs[i].key.equals(key)) {
				// if the key was founded, return true
				return true;
			}
		}
		// if key not found, return false
		return false;
	}

	/**
	 * Returns the number of elements in the data structure
	 * 
	 * @return the size of the structure
	 */
	@Override
	public int size() {
		return size;
	}

	// TODO: add unimplemented methods
	// ProTip: Eclipse can do this for you

}
