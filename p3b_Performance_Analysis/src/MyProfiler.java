
// Used as the data structure to test our hash table against Tree Map
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable;
	TreeMap<K, V> treemap;

	public MyProfiler() {
		// TODO: complete the Profile constructor
		// Instantiate your HashTable and Java's TreeMap
		hashtable = new HashTable<K, V>();
		treemap = new TreeMap<K, V>();
	}

	public void insert(K key, V value) throws IllegalNullKeyException {
		// TODO: complete insert method
		// Insert K, V into both data structures
			hashtable.insert(key, value);
			treemap.put(key, value);
	}

	public void retrieve(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO: complete the retrieve method
		// get value V for key K from data structures
			hashtable.get(key);
			treemap.get(key);
	}

	public static void main(String[] args) {
		try {
			int numElements = Integer.parseInt(args[0]);

			// TODO: complete the main method.
			// Create a profile object.
			// For example, Profile<Integer, Integer> profile = new Profile<Integer,
			// Integer>();
			// execute the insert method of profile as many times as numElements
			// execute the retrieve method of profile as many times as numElements
			// See, ProfileSample.java for example.

			MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
			for(int i = 0; i < numElements; i++)
				profile.insert(i, i);
			for(int i = 0; i < numElements; i++)
				profile.retrieve(i);
			String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
			System.out.println(msg);
		} catch (Exception e) {
			System.out.println("Usage: java MyProfiler <number_of_elements>");
			System.exit(1);
		}
	}
}
