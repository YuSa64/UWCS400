// TODO: Add file header here
/**
 * 
 * @author Hyeon Jun Jeong
 *
 */



// TODO: Add class header here
public class DS_Srivatsan implements DataStructureADT<String, String> {

  // TODO may wish to define an inner class
  // for storing key and value as a pair
  // such a class and its members should be "private"
  private class KVData {
    private String key;
    private String value;

    public KVData(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }

  // Private Fields of the class
  // TODO create field(s) here to store data pairs
  private int size;
  private final int arraysize = 100;
  private KVData[] array;
  private KVData[] sarray;

  public DS_Srivatsan() {
    // TODO Auto-generated method stub
    size = 0;
    array = new KVData[arraysize];
    sarray = new KVData[array.length * arraysize];
  }

  @Override
  public void insert(String key, String value) {
    // TODO Auto-generated method stub
    if(key == null) throw new IllegalArgumentException();
    
    if (size == array.length) {
      array = sarray;
      sarray = new KVData[array.length + arraysize];
      for(int i = 0; i < size; i++)
        sarray[i] = array[i];
    }
    
    if(!contains(key)) {
      array[size] = new KVData(key, value);
      sarray[size] = array[size++];      
    }
    else throw new RuntimeException();
  }

  @Override
  public boolean remove(String key) {
    // TODO Auto-generated method stub
    if(key == null) throw new IllegalArgumentException();
    
    for(int i = 0; i < size; i++) {
      if(array[i].key.equals(key)) {
        for(int j = i; j < size; j++) {
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
  public String get(String key) {
    // TODO Auto-generated method stub
    if(key == null) throw new IllegalArgumentException();
    for (int i = 0; i < size; i++)
      if (array[i].key.equals(key))
        return array[i].value;
    return null;
  }

  @Override
  public boolean contains(String key) {
    // TODO Auto-generated method stub
    if(key == null) return false;
    if(size() == 0)
      return false;
    for(int i = 0; i < size; i++)
      if (array[i].key.equals(key))
        return true;
    return false;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  // TODO: add unimplemented methods
  // ProTip: Eclipse can do this for you



}


