import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// TODO: comment and complete your HashTableADT implementation
//
// TODO: implement all required methods
// DO ADD REQUIRED PUBLIC METHODS TO IMPLEMENT interfaces
//
// DO NOT ADD ADDITIONAL PUBLIC MEMBERS TO YOUR CLASS 
// (no public or package methods that are not in implemented interfaces)
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// if open addressing: describe probe sequence 
// if buckets: describe data structure for each bucket
//
// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object

/** HashTable implementation that uses:
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

    /** The initial capacity that is used if none is specifed user */
    static final int DEFAULT_CAPACITY = 101;
    
    /** The load factor that is used if none is specified by user */
    static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;

    private int bucketCapacity;			// the size of the hash table array
    private double loadFactorThreshold;	// current load factor for this hash table
    private int numKeys = 0;			// the number of key,value pairs in the data structure

    /** Node inner class: 
     * 		assigned this class' instance to HashTable's specific index
     * 		contains the key, value and next node's address of linked list
     * @field k key
     * @field v value (instance of Book class)
     * @field next next Node in linked list
     */
    private class HashNode {
    	private String k;
    	private Book v;
    	HashNode next;
    	
		public HashNode(String k, Book v) {
			this.k = k;
			this.v = v;
		}
		
		String getK() { return k; }
		Book getV() { return v;	}

		void setK(String k) { this.k = k;	}
		void setV(Book v) {	this.v = v;	}
    }
    // create instance of hash table
    ArrayList<HashNode> buckets = new ArrayList<>();

    
    /**
     * REQUIRED default no-arg constructor
     * Uses default capacity and sets load factor threshold 
     * for the newly created hash table.
     */
    public BookHashTable() {
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR_THRESHOLD);
    }
    
    /**
     * Creates an empty hash table with the specified capacity 
     * and load factor.
     * @param initialCapacity number of elements table should hold at start.
     * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
     */
    public BookHashTable(int initialCapacity, double loadFactorThreshold) {
    	
        this.bucketCapacity = initialCapacity;
        this.loadFactorThreshold = loadFactorThreshold;
        
        // make buckets in size of initialCapacity and initialize buckets
        for (int i=0; i < initialCapacity; i++) {
        	this.buckets.add(null);
        }
    }

    /**
     * Add the key,value pair to the data structure and increase the number of keys.
     * @param key (String)
     * @param value (instance of Book class)
     */
	@Override
	public void insert(String key, Book value) throws IllegalNullKeyException, DuplicateKeyException {
		
		// If key is null, throw IllegalNullKeyException()
		if (key == null) {
			throw new IllegalNullKeyException();
		}
		HashNode newNode = new HashNode(key, value);
		
		int index = getBucketIndex(key);
		HashNode head = this.buckets.get(index);
		
		// if buckets[index] is empty, set the new node into buckets[index]
		if (head == null) {
			System.out.println("head is null");
			this.buckets.set(index, newNode);
			numKeys++;
			
		} 
		else {
			while(head != null) {
				// If key is already in buckets, throw DuplicateKeyException()
				if (head.getK().equals(key)) {
					throw new DuplicateKeyException();
				}
				head = head.next;
			}
			// insert the new HashNode to head of linked list in buckets[index]
			if (head == null) {
				newNode.next = this.buckets.get(index);
				this.buckets.set(index, newNode);
				numKeys++;
			}
		}

		// if (numKeys / (double) bucketCapacity) is greater than loadFactorThreshold value,
		// increase the bucketCapacity and reassign existing items to increased hash table
		if ( (numKeys / (double) bucketCapacity) > loadFactorThreshold ) {
			ArrayList<HashNode> tempBuckets = this.buckets;
			this.buckets = new ArrayList<BookHashTable.HashNode>();
			// set bucketCapacity value to new BucketCapacity
			this.bucketCapacity = (2 * this.bucketCapacity) + 1;
			// add HashNode items to new BucketCapacity size
			for (int i = 0; i < this.bucketCapacity; i++) {
				this.buckets.add(null);
			}
			
			// reassign existing HashNode's items to new buckets hash table having new bucketCapacity 
			numKeys = 0;	// initialize numKeys' value to zero
			for (HashNode node : tempBuckets) {
				while (node != null) {
					this.insert(node.getK(), node.getV());
					node = node.next;
				}
			}
		}	
	}

	
    /**
     * If key is found, remove the key,value pair from the data structure
     *                  decrease number of keys.
     * @param key (String)
     */
	@Override
	public boolean remove(String key) throws IllegalNullKeyException {
		
		// If key is null, throw IllegalNullKeyException()
		if (key == null) {
			throw new IllegalNullKeyException();
		}
		
		int index = getBucketIndex(key);
		HashNode head = this.buckets.get(index);
		
		// If key is not found, return false
		if (head == null) {
			return false;
		}
		
		if (head.getK().equals(key)) {
			head = head.next;
			this.buckets.set(index, head);
			numKeys--;
			return true;
		} 
		else {
			HashNode prev = null;
			while (head != null) {
				if (head.getK().equals(key)) {
					prev.next = head.next;
					numKeys--;
					return true;
				}
				prev = head;
				head = head.next;
			}
			return false;
		}	
	}

	
    /**
     * Returns the value associated with the specified key
     * Does not remove key or decrease number of keys
     * @param key (String)
     */
	@Override
	public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
		
		// If key is null, throw IllegalNullKeyException()
		if (key == null) {
			throw new IllegalNullKeyException();
		}
		
		int index = getBucketIndex(key);
		HashNode head = this.buckets.get(index);
		
		while (head != null) {
			if (head.getK().equals(key)) {
				return head.getV();
			}
			head = head.next;
		}
		// If key is not found, throw KeyNotFoundException()
		throw new KeyNotFoundException();
	}

    /**
    * Returns the number of key,value pairs in the data structure
    */
	@Override
	public int numKeys() {

		return this.numKeys;
	}

    /**
    * Returns the load factor for this hash table
    * that determines when to increase the capacity 
    * of this hash table
    */
	@Override
	public double getLoadFactorThreshold() {
		
		return this.loadFactorThreshold;
	}

    /**
    * Capacity is the size of the hash table array
    * This method returns the current capacity.
    */
	@Override
	public int getCapacity() {
		
		return this.bucketCapacity;
	}

    /**
    * Returns the collision resolution scheme used for this hash table.
    */
	@Override
	public int getCollisionResolutionScheme() {
		// 5 CHAINED BUCKET: array list of linked lists
		return 5;
	}

    /**
     * Get buckets's index that stores the HashNode having the key using String class' hashCode() method
     * @param key 
     */
	private int getBucketIndex(String key) {
		int hashCode = key.hashCode();

		return Math.abs(hashCode % bucketCapacity);
	}

}