import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

  private T ds;

  protected abstract T createInstance();

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

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

  // TODO: review tests 01 - 04

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


  // TODO: add tests 05 - 07 as described in assignment
  @Test
  void test05_insert_remove_one() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (ds.remove(key));
    if (ds.get(key) != null)
      fail("get(" + key + ") returned " + ds.get(key) + " which should have been removed");
  }

  @Test
  void test06_insert_many_size() {
    for (int i = 0; i < 100; i++)
      ds.insert("" + i, "" + i);
    if (ds.size() != 100)
      fail("size does not correct");

    for (int i = ds.size(); i < 1000; i++)
      ds.insert("" + i, "" + i);
    if (ds.size() != 1000)
      fail("size does not correct");

    for (int i = ds.size(); i < 10000; i++)
      ds.insert("" + i, "" + i);
    if (ds.size() != 10000)
      fail("size does not correct");

    /*
     * This test takes too long time. for(int i = ds.size(); i < 100000; i++) ds.insert(""+i, ""+i);
     * if(ds.size() != 100000) fail("size does not correct");
     */
  }

  @Test
  void test07_no_duplicates() {
    String key = "1";
    String value = "one";
    ds.insert("1", "one");
    for (int i = 2; i < 100; i++)
      ds.insert("" + i, "" + i);
    try {
      ds.insert(key, value);
      fail("duplicate exception not thrown");
    } catch (RuntimeException re) {
      ds.remove(key);
      try {
        ds.insert(key, value);
      } catch (RuntimeException re2) {
        fail("duplicate exception thrown");
      }
    }
  }

  // TODO: add more tests of your own design to ensure that you can detect implementation that fail
  @Test
  void test08_shadow_array_time() { //This test is unstable due to way to handle array.
    for (int i = 0; i < 1000; i++)
      ds.insert("" + i, "" + i);
    // Change to shadow array, than make a new shadow array (Expand), O(N)
    long pre_start = System.nanoTime();
    ds.insert("1000", "1000");
    long pre_end = System.nanoTime();
    //
    // Insert after expand, O(1)
    long pst_start = System.nanoTime();
    ds.insert("1001", "1001");
    long pst_end = System.nanoTime();
    if (pre_end - pre_start < pst_end - pst_start) // if expanding time does not exceed O(1)
      fail("Expansion is not working properly");
  }

  @Test
  void test09_get_null_key() {
    try {
      ds.get(null);
      fail("Exception does not occur");
    } catch (IllegalArgumentException e) {

    } catch (Exception e2) {
      fail("Unexpected exception occured");
    }

  }

  @Test
  void test10_get_no_key_null_value() {
    for (int i = 0; i < 10; i++)
      ds.insert("" + i, "" + i);
    if(ds.get("10") != null)
      fail("Get" + ds.get("10") + ", Expected null");
  }

  // Tip: consider different numbers of inserts and removes and how different combinations of insert
  // and removes

}
