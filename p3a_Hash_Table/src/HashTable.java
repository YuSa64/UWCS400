import java.util.ArrayList;

// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// TODO: explain your hashing algorithm here
// NOTE: you are not required to design your own algorithm for hashing,
// since you do not know the type for K,
// you must use the hashCode provided by the <K key> object
// and one of the techniques presented in lecture
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  // TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation
  public class Node<K, V> {
    private K key;
    private V value;
    Node<K, V> next;

    public Node(K k, V v) {
      this.key = k;
      this.value = v;
    }
  }

  static final int DEFAULT_CAPACITY = 53;
  static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;

  ArrayList<Node<K, V>> buckets = new ArrayList<>();
  private int capacity;
  private double loadfactorthreshold;
  private int size = 0;

  // TODO: comment and complete a default no-arg constructor
  public HashTable() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR_THRESHOLD);
  }

  // TODO: comment and complete a constructor that accepts
  // initial capacity and load factor threshold
  // threshold is the load factor that causes a resize and rehash
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    capacity = initialCapacity;
    loadfactorthreshold = loadFactorThreshold;

    for (int i = 0; i < capacity; i++)
      buckets.add(null);
  }

  // TODO: implement all unimplemented methods so that the class can compile
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalNullKeyException();

    Node<K, V> input = new Node<K, V>(key, value);
    int index = Math.abs(key.hashCode() % capacity);
    Node<K, V> head = buckets.get(index);

    if (head == null) {
      buckets.set(index, input);
    } else {
      while (head != null) {
        if (head.key.equals(key))
          break;
        head = head.next;
      }

      if (head != null) {
        head.value = value;
      } else {
        input.next = buckets.get(index);
        buckets.set(index, input);
      }
    }
    size++;

    if (getLoadFactor() > loadfactorthreshold) {
      ArrayList<Node<K, V>> oldbuckets = buckets;
      capacity = capacity * 2 + 1;
      buckets = new ArrayList<>();
      for (int i = 0; i < capacity; i++)
        buckets.add(null);
      for (int i = 0; i < oldbuckets.size(); i++) {
        Node<K, V> node = oldbuckets.get(i);
        while (node != null) {
          insert(node.key, node.value);
          node = node.next;
        }
      }
    }
  }

  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalNullKeyException();

    int index = Math.abs(key.hashCode() % capacity);
    Node<K, V> head = buckets.get(index);

    if (head == null)
      return false;

    if (head.key.equals(key)) {
      head = head.next;
      buckets.set(index, head);
      size--;
      return true;
    } else {
      Node<K, V> pre = head;
      head = head.next;
      while (head != null) {
        if (head.key.equals(key)) {
          pre.next = head.next;
          size--;
          return true;
        }
        pre = head;
        head = head.next;
      }
      return false;
    }
  }

  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalNullKeyException();

    int index = Math.abs(key.hashCode() % capacity);
    Node<K, V> head = buckets.get(index);

    while (head != null) {
      if (head.key.equals(key))
        return head.value;
      head = head.next;
    }
    throw new KeyNotFoundException();
  }

  @Override
  public int numKeys() {
    // TODO Auto-generated method stub
    return size;
  }

  @Override
  public double getLoadFactorThreshold() {
    // TODO Auto-generated method stub
    return loadfactorthreshold;
  }

  @Override
  public double getLoadFactor() {
    // TODO Auto-generated method stub
    return (double) size / capacity;
  }

  @Override
  public int getCapacity() {
    // TODO Auto-generated method stub
    return capacity;
  }

  @Override
  public int getCollisionResolution() {
    // TODO Auto-generated method stub
    return 5;
  }



}
