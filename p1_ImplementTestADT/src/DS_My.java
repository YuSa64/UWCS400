// TODO: Add file header here
/**
 * p1 Testing, Debugging and Performance file: DS_My.java, DataStructureADTTest.java, CompareDS.java
 * 
 * @author Hyeon Jun Jeong
 *
 */



// TODO: Add class header here
/**
 * Data Structure with ArrayList like structure.
 * 
 * @author Jun
 *
 */
public class DS_My implements DataStructureADT<String, String> {

  // TODO may wish to define an inner class
  // for storing key and value as a pair
  // such a class and its members should be "private"

  /**
   * Private Data class which has two Strings as a key and a value
   * 
   * @author Jun
   * 
   */
  private class KVData {
    private String key;
    private String value;

    /**
     * Constructor of data
     * 
     * @param key key String of the data
     * @param value value String of the data
     */
    public KVData(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }

  // Private Fields of the class
  // TODO create field(s) here to store data pairs
  private int size;
  private final int arraysize = 1000;
  private KVData[] array;
  private KVData[] sarray;

  // TODO: add unimplemented methods
  // ProTip: Eclipse can do this for you
  /**
   * Constructor of the class
   */
  public DS_My() {
    // TODO Auto-generated method stub
    size = 0;
    array = new KVData[arraysize];
    sarray = new KVData[array.length * arraysize];
  }

  @Override
  // Add the key,value pair to the data structure and increases size.
  // If key is null, throws IllegalArgumentException("null key");
  // If key is already in data structure, throws RuntimeException("duplicate key");
  // can accept and insert null values
  public void insert(String key, String value) {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalArgumentException();

    if (size == array.length) {
      array = sarray;
      sarray = new KVData[array.length * arraysize];
      for (int i = 0; i < size; i++)
        sarray[i] = array[i];
    }

    if (!contains(key)) {
      array[size] = new KVData(key, value);
      sarray[size] = array[size++];
    } else
      throw new RuntimeException();
  }

  @Override
  // If key is found, Removes the key from the data structure and decreases size
  // If key is null, throws IllegalArgumentException("null key") without decreasing size
  // If key is not found, returns false.
  public boolean remove(String key) {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalArgumentException();

    for (int i = 0; i < size; i++) {
      if (array[i].key.equals(key)) {
        for (int j = i; j < size; j++) {
          array[j] = array[j + 1];
          sarray[j] = sarray[j + 1];
        }
        size--;
        return true;
      }
    }
    return false;
  }

  @Override
  // Returns the value associated with the specified key
  // get - does not remove key or decrease size
  // return null if key is not null and is not found in data structure
  // If key is null, throws IllegalArgumentException("null key")
  public String get(String key) {
    // TODO Auto-generated method stub
    if (key == null)
      throw new IllegalArgumentException();
    for (int i = 0; i < size; i++)
      if (array[i].key.equals(key))
        return array[i].value;
    return null;
  }

  @Override
  // Returns true if the key is in the data structure
  // Returns false if key is null or not present
  public boolean contains(String key) {
    // TODO Auto-generated method stub
    if (key == null)
      return false;
    if (size() == 0)
      return false;
    for (int i = 0; i < size; i++)
      if (array[i].key.equals(key))
        return true;
    return false;
  }

  @Override
  // Returns the number of elements in the data structure
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }



}

