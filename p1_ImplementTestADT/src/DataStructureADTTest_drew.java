
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class DataStructureADTTest_drew<T extends DataStructureADT<String, String>> {

  private T ds;

  protected abstract T createInstance();

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  @BeforeEach
  void setUp() throws Exception {
    ds = createInstance();
  }

  @AfterEach
  void tearDown() throws Exception {
    ds = null;
  }

  @Test
  void test00_empty_ds_size() {
    if (ds.size() != 0)
      fail("data structure should be empty, with size=0, but size=" + ds.size());
  }

  @Test
  void test01_insert_one() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (ds.size() == 1);
  }

  @Test
  void test02_insert_remove_one_size_0() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (ds.remove(key)); // remove the key
    if (ds.size() != 0)
      fail("data structure should be empty, with size=0, but size=" + ds.size());
  }

  @Test
  void test03_duplicate_exception_thrown() {
    String key = "1";
    String value = "one";
    ds.insert("1", "one");
    ds.insert("2", "two");
    try {
      ds.insert(key, value);
      fail("duplicate exception not thrown");
    } catch (RuntimeException re) {
    }
    assert (ds.size() == 2);
  }

  @Test
  void test04_remove_returns_false_when_key_not_present() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (!ds.remove("2")); // remove non-existent key is false
    assert (ds.remove(key)); // remove existing key is true
    if (ds.get(key) != null)
      fail("get(" + key + ") returned " + ds.get(key) + " which should have been removed");
  }

  /**
   * test05_insert_remove_one - Inserts one item and fails if unable to remove it and size is not 0.
   */
  @Test
  void test05_insert_remove_one() {
    ds.insert("1", "one");
    assertTrue(ds.remove("1"), "remove method returned false, should return true");
    assertTrue(ds.size() == 0, "Size should be 0, instead equals: " + ds.size());
  }

  /**
   * test06_insert_many_size - inserts many items and fails if size is not correct.
   */
  @Test
  void test06_insert_many_size() {
    ds.insert("1", "one");
    ds.insert("2", "two");
    ds.insert("3", "three");
    ds.insert("4", "four");
    ds.insert("5", "five");
    assertTrue(ds.size() == 5, "Size =" + ds.size() + ", should be 5.");
  }

  /**
   * test07_duplicate_values - inserts two pairs with different keys, but same values; fails if
   * second doesn't insert
   */
  @Test
  void test07_duplicate_values() {
    ds.insert("1", "one");
    try {
      ds.insert("2", "one");
    } catch (Exception e) {
      fail("Tried inserting the same value twice with different keys, no exception should have been"
              + " thrown.");
    }
    assertTrue(ds.size() == 2, "Size =" + ds.size() + ", should be 2.");
  }

  /**
   * test08_remove_empty_ds Tries to remove from an empty ds. Fails if the remove method returns
   * true or the size is not 0
   */
  @Test
  void test08_remove_empty_ds() {
    assertTrue(!ds.remove("hi"), "Remove returned true when called on empty ds.");
    assertTrue(ds.size() == 0, "Size should be 0, instead is: " + ds.size());
  }

  /**
   * test09_remove_null Tries to remove from a non-empty data structure with a null key. Fails if
   * IllegalArgumentException is not thrown and size is not still 1 after trying to remove.
   */
  @Test
  void test09_remove_null() {
    ds.insert("key", "val");
    try {
      ds.remove(null);
      fail("Exception was not thrown for removing a null key.");
    } catch (IllegalArgumentException e) {
    } catch (Exception e) {
      fail("The wrong type of exception was thrown, should be IllegalArgumentException, instead "
              + "was: " + e.getClass());
    }
    assertTrue(ds.size() == 1, "Size should be 1, instead it was: " + ds.size());
  }

  /**
   * test10_get Basic test that sees if get does not change the size and the right value is
   * returned. Also then calls get a second time to see if it still works as intended.
   */
  @Test
  void test10_get_twice() {
    ds.insert("1", "one");
    ds.insert("2", "two");
    ds.insert("3", "three");

    if (ds.get("1") != null) {
      assertTrue(ds.get("1").equals("one"),
              "Calling get with key of \"1\" returned: " + ds.get("1") + ", Expected \"one\"");
      assertTrue(ds.size() == 3, "Size=" + ds.size() + ", should be 3.");
      assertTrue(ds.get("1").equals("one"),
              "Calling get(\"1\") a second time did not work, the key,value pair may have been "
                      + "removed from the data structure from the first get call.");
      assertTrue(ds.size() == 3, "Size=" + ds.size() + ", should be 3.");
    } else {
      fail("get(\"1\") returned null, when it should have been found.");
    }
  }

  /**
   * test11_get_null Tries to remove from a ds with a null key. Fails if IllegalArgumentException is
   * not thrown.
   */
  @Test
  void test11_get_null() {
    ds.insert("1", "one");
    try {
      ds.get(null);
      fail("No IllegalArgumentException was thrown.");
    } catch (IllegalArgumentException e) {
    } catch (Exception e) {
      fail(e.getClass() + " was the class of the exception thrown, should have been an "
              + "IllegalArgumentException");
    }
  }

  /**
   * test12_contains Tests basic contains() method functionality by inserting a pair and seeing if
   * contains on that key returns true. Then removes the pair, and makes sure that contains now
   * returns false.
   */
  @Test
  void test12_contains() {
    ds.insert("1", "one");
    assertTrue(ds.contains("1"),
            "contains on a key of 1 which was already inserted returned false.");
    ds.remove("1");
    assertFalse(ds.contains("1"), "contains on key of 1 returned true after it was removed.");
  }

  /**
   * test13_contains_null Makes sure that passing null to contains returns false, fails otherwise.
   */
  @Test
  void test13_contains_null() {
    assertFalse(ds.contains(null),
            "Calling contains with a null key returned true, should be false.");
  }

  /**
   * test14_insert_remove_insert Inserts with a key of 1, then removes it. Finally, tries to insert
   * the original key to make sure that a duplicate key exception is not thrown.
   */
  @Test
  void test14_insert_remove_insert() {
    ds.insert("1", "one");
    ds.remove("1");
    try {
      ds.insert("1", "one");
    } catch (Exception e) {
      fail("Caught exception: "+e.getClass()+". No exception should be thrown in this case.");
    }
  }
  
  @Test
  void test15_insert1000_remove1000() {
    String val = "";
    String key = "";
    
    for(int i=0; i<=1000; i++) {
      val = ""+i;
      key = "key"+i;
      ds.insert(key, val);
    }
    
    for(int i=0; i<=1000; i++) {
      val = ""+i;
      key = "key"+i;
      ds.remove(key);
    }
  }
}
