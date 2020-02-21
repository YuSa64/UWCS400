

/**
 * DS_My - This class implements the DataStructureADT interface and represents a data structure that
 * allows for key value pairs to be inserted, removed, and searched for. This is done with an 
 * underlying array that expands to double the size when it runs out of room.
 * 
 * @author halverson (2020)
 */
public class DS_My_drew implements DataStructureADT<String, String> {

  /**
   * keyValuePair - An inner class with the sole purpose of storing a String key and a String value
   * as a key value pair for use in the enclosing data structure.
   * 
   * @author halverson (2020)
   */
  private class KeyValuePair {
    private String key;
    private String value;

    /**
     * Constructor for this class that initializes the key and value Strings
     * 
     * @param key   parameter that will be stored in key
     * @param value parameter that will be stored in value
     */
    private KeyValuePair(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /**
     * getKey accessor for key instance field
     * 
     * @return returns the key String
     */
    private String getKey() {
      return this.key;
    }

    /**
     * getValue accessor for value instance field
     * 
     * @return returns the value String
     */
    private String getValue() {
      return this.value;
    }

    /**
     * equals checks if this object equals an other KeyValuePair based on whether or not their key
     * Strings are equal.
     * 
     * @param other the KeyValuePair object that is being compared to this object.
     * @return returns true if they are equal, false otherwise.
     */
    private boolean equals(KeyValuePair other) {
      return this.getKey().equals(other.getKey());
    }

    /**
     * equals Overloaded method that accepts a String key instead of a KeyValuePair object as a
     * parameter.
     * 
     * @param key the key String that is being compared
     * @return returns true if equal, false otherwise.
     */
    private boolean equals(String key) {
      return equals(new KeyValuePair(key, null));
    }
  }

  //instance fields for DS_My class
  private KeyValuePair[] pairs;
  private int size;

  /**
   * Constructor that initializes the pairs array with length 10.
   */
  public DS_My_drew() {
    this.pairs = new KeyValuePair[10];
    this.size = 0;
  }

  /**
   * Adds a new keyValuePair object created from the String parameters to the end of the array. If
   * the array only has one index that is not full yet, the array is first expanded to account for
   * double the size it previously did.
   * 
   * Add the key,value pair to the data structure and increases size. If key is null, throws
   * IllegalArgumentException("null key"); If key is already in data structure, throws
   * RuntimeException("duplicate key");can accept and insert null values
   */
  @Override
  public void insert(String key, String value) {
    if (key == null) {
      throw new IllegalArgumentException("null key");
    }
    if (this.contains(key)) {
      throw new RuntimeException("duplicate key");
    }
    if (size() == this.pairs.length) {
      expandPairs();
    }
    this.pairs[size] = new KeyValuePair(key, value);
    size++;
  }

  /**
   * expandPairs Private helper method that expands the array to twice its size when more space is
   * needed.
   */
  private void expandPairs() {
    KeyValuePair[] newPairs = new KeyValuePair[this.pairs.length * 2];
    for (int i = 0; i < this.pairs.length; i++) {
      newPairs[i] = this.pairs[i];
    }
    this.pairs = newPairs;
  }

  /**
   * If key is found, Removes the key from the data structure and decreases size. If key is null,
   * throws IllegalArgumentException("null key") without decreasing size If key is not found,
   * returns false.
   */
  @Override
  public boolean remove(String key) {
    if (key == null) {
      throw new IllegalArgumentException("null key");
    }
    for (int i = 0; i < this.size(); i++) {
      if (this.pairs[i].equals(key)) {
        shiftForward(i);
        size--;
        return true;
      }
    }
    return false;
  }

  /**
   * shiftForward private helper method that shifts all of the elements of the pairs array after the
   * index passed forward so that the index given is lost.
   * 
   * @param index location of where the shifting will begin.
   */
  private void shiftForward(int index) {
    KeyValuePair[] newPairs = new KeyValuePair[this.pairs.length];
    for (int i = 0; i < index; i++) {
      newPairs[i] = this.pairs[i];
    }
    for (int i = index + 1, j = index; i < size(); i++, j++) {
      newPairs[j] = this.pairs[i];
    }
    this.pairs = newPairs;
  }

  /**
   * Returns the value associated with the specified key get - does not remove key or decrease size
   * return null if key is not null and is not found in data structure If key is null, throws
   * IllegalArgumentException("null key")
   * 
   * @param key the key of the KeyValuePair that is being searched for
   * @return Returns the String of the value from the pair that was found, unless it was not found
   *         and in that case returns null.
   */
  @Override
  public String get(String key) {
    if (key == null) {
      throw new IllegalArgumentException("null key");
    }

    for (int i = 0; i < size(); i++) {
      if (this.pairs[i].equals(key)) {
        return this.pairs[i].getValue();
      }
    }

    return null;
  }

  /**
   * Returns true if the key is in the data structure, returns false if key is null or not present
   * 
   * @param key
   * @return returns true if the key was found, false otherwise
   */
  @Override
  public boolean contains(String key) {
    if(key==null) {
      return false;
    }
    
    for(int i=0; i<size(); i++) {
      if(this.pairs[i].equals(key)) {
        return true;
      }
    }
    
    return false;
  }

  /**
   * Accessor method for the size of this data structure.
   * 
   * @return returns the size of this ds
   */
  @Override
  public int size() {
    return this.size;
  }
}
