// TODO: add imports as needed

// org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/** TODO: add class header comments here */
public class HashTableTest {

  // TODO: add other fields that will be used by multiple tests
  HashTableADT<Integer, String> htIntegerKey;

  // TODO: add code that runs before each test method
  @Before
  public void setUp() throws Exception {
    htIntegerKey = new HashTable<Integer, String>();
  }

  // TODO: add code that runs after each test method
  @After
  public void tearDown() throws Exception {}

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  // TODO add your own tests of your implementation
  @Test
  public void test002_resize() {
    htIntegerKey = new HashTable<Integer, String>(50, 0.9);
    try {
      for(int i = 0; i < 44; i++){
        htIntegerKey.insert(i, "value");
      }
      long starttime = System.nanoTime();
      htIntegerKey.insert(44, "value");
      long t_insert = System.nanoTime() - starttime;
      starttime = System.nanoTime();
      htIntegerKey.insert(45, "value");
      long t_resize = System.nanoTime() - starttime;
      if(t_resize < t_insert)
        fail("Resize does not occured correctly");
      if(htIntegerKey.getCapacity() != 101)
        fail("Resize does not occured");
      if(htIntegerKey.numKeys() != 46)
        fail("Numkey is not correct");
    } catch(Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
  @Test
  public void test003_insert_same_index() {
    htIntegerKey = new HashTable<Integer, String>(10, 0.5);
    try {
      for(int i = 0; i < 11; i++){
        htIntegerKey.insert(i * 10, "value");
      }
    } catch(Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
  @Test
  public void test004_insert_duplicate_overwrite() {
    htIntegerKey = new HashTable<Integer, String>(6, 0.5);
    try {
      for(int i = 0; i < 50; i++){
        htIntegerKey.insert(i, "value");
      }
        htIntegerKey.insert(1, "newvalue");
        if(!htIntegerKey.get(1).equals("newvalue"))
          fail("Value is not overwrote");
    }
    catch (Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
  @Test
  public void test005_get() {
    htIntegerKey = new HashTable<Integer, String>(11, 0.9);
    try {
      htIntegerKey.insert(0, "firstvalue");
      for(int i = 1; i < 7; i++){
        htIntegerKey.insert(i*17 - 1, "value");
      }
      if(!htIntegerKey.get(0).equals("firstvalue"))
        fail("Value is not overwrote");
    }
    catch (Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
  @Test
  public void test006_get_null() {
    try{
      htIntegerKey.get(null);
      fail("No Exception Thrown");
    }
    catch(IllegalNullKeyException e){}
    catch(Exception e2) {
      fail("Unexpected exception occured" + e2.getMessage());
    }
  }
  @Test
  public void test007_get_nothing() {
    try {
      for(int i = 0; i < 50; i++)
        htIntegerKey.insert(i, "value");
      htIntegerKey.get(50);
      fail("No Exception Thrown");
    }
    catch (KeyNotFoundException e) {}
    catch (Exception e2) {
      fail("Unexpected exception occured" + e2.getMessage());
    }
  }
  @Test
  public void test008_constructor() {
    if(htIntegerKey.getLoadFactorThreshold() != 0.75)
      fail("LoadFactorThreshold is not correct");
    htIntegerKey = new HashTable<Integer, String>(10, 0.5);
    if(htIntegerKey.getLoadFactorThreshold() != 0.5)
      fail("LoadFactorThreshold is not correct");
    htIntegerKey = new HashTable<Integer, String>(60, 0.9);
    if(htIntegerKey.getLoadFactorThreshold() != 0.9)
      fail("LoadFactorThreshold is not correct");
  }
  @Test
  public void test009_remove() {
    htIntegerKey = new HashTable<Integer, String>(11, 0.9);
    try {
      htIntegerKey.insert(0, "firsttarget");
      for(int i = 1; i < 7; i++)
        htIntegerKey.insert(i* 17, "value");
      htIntegerKey.insert(12, "secondtarget");
      if(!htIntegerKey.remove(0))
        fail("Remove success, but returns false");
      if(!htIntegerKey.remove(12))
        fail("Remove success, but returns false");
    }
    catch (Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
  @Test
  public void test010_remove_one_bucket() {
    htIntegerKey = new HashTable<Integer, String>(53, 0.9);
    try {
      htIntegerKey.insert(0, "firsttarget");
      for(int i = 1; i < 40; i++)
        htIntegerKey.insert(i* 53, "value");
      htIntegerKey.insert(106, "secondtarget");
      if(!htIntegerKey.remove(0))
        fail("Remove success, but returns false");
      if(!htIntegerKey.remove(106))
        fail("Remove success, but returns false");
    }
    catch (Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
  @Test
  public void test011_remove_null() {
    try {
      htIntegerKey.remove(null);
    }
    catch (IllegalNullKeyException e) {}
    catch (Exception e2) {
      fail("Unexpected exception occured" + e2.getMessage());
    }
  }
  @Test
  public void test012_remove_none() {
    htIntegerKey = new HashTable<Integer, String>(23, 0.9);
    try {
      for(int i = 0; i < 11; i++)
        htIntegerKey.insert(i*13 - 1, "value");
      if(htIntegerKey.remove(23))
        fail("Remove fails, but returns true");
      if(htIntegerKey.remove(24))
        fail("Remove fails, but returns true");
    }
    catch (Exception e) {
      fail("Unexpected exception occured" + e.getMessage());
    }
  }
}
