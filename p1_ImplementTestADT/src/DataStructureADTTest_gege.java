import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Author: Gege Zhang 
 * Email: gzhang85@wisc.edu 
 * Lecture Number: 002 Program
 * Description: This program is to practice for implement, test, and compare alternate implementations of an abstract data type 
 */

/**
 * 
 * DataStructureADTTest - This class is to tests any problems may occurs in the
 * method
 * 
 * @author Gege Zhang(2020)
 *
 * @param <T> dataStructureADT object which is a pair
 */
abstract class DataStructureADTTest_gege<T extends DataStructureADT<String, String>> {

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

	// TODO: review tests 01 - 04

	/**
	 * insert one key value pair into the data structure and then confirm that
	 * size() is 1
	 */
	@Test
	void test01_insert_one() {
		String key = "1";
		String value = "one";
		ds.insert(key, value);
		assert (ds.size() == 1);
	}

	/**
	 * insert one key,value pair and remove it, then confirm size is 0.
	 */
	@Test
	void test02_insert_remove_one_size_0() {
		String key = "1";
		String value = "one";
		ds.insert(key, value);
		assert (ds.remove(key)); // remove the key
		if (ds.size() != 0)
			fail("data structure should be empty, with size=0, but size=" + ds.size());
	}

	/**
	 * insert a few key,value pairs such that one of them has the same key as an
	 * earlier one. Confirm that a RuntimeException is thrown.
	 */
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

	/**
	 * insert some key,value pairs, then try removing a key that was not inserted.
	 * Confirm that the return value is false.
	 */
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
	 * inserts one item and fails if unable to remove it
	 */
	@Test
	void test05_insert_remove_one() {
		// set the key and value
		String key = "1";
		String value = "one";
		// insert one pair
		ds.insert(key, value);
		// remove the key, fails if unable to remove it
		assert (ds.remove(key));
	}

	/**
	 * inserts many items and fails if size is not correct
	 */
	@Test
	void test06_insert_many_size() {
		// set the key and value
		String key = "3";
		String value = "three";
		// insert three pairs
		ds.insert("1", "one");
		ds.insert("2", "two");
		ds.insert(key, value);
		// check if size equal 3
		if (ds.size() != 3)
			// if size not correct fails
			fail("size is not correct ");
	}

	/**
	 * inserts two pairs with different keys, but same values; fails if second
	 * doesn't insert
	 */
	@Test
	void test07_duplicate_values() {
		// set the key and value
		String key = "3";
		String value = "one";
		// insert one pair
		ds.insert("1", "one");
		try {
			// insert another pair with same value as previous one
			ds.insert(key, value);
			// make sure the second one is inserted
			assert (ds.contains(key));
			// catch RuntimeException in case for unknown duplication
		} catch (RuntimeException e) {
			// fail if there is runtimeException
			fail("second doesn't insert");
		}

	}

	/**
	 * inserts one pairs with null key. Confirm that a IllegalArgumentException is
	 * thrown.
	 */
	@Test
	void test08_insert_null_key_exception_thrown() {
		try {
			// inserts one pairs with null key
			ds.insert(null, "one");
			// fail if IllegalArgumentException is not thrown
			fail("IllegalArgumentException not thrown");
			// catch the IllegalArgumentException if success thrown
		} catch (IllegalArgumentException e) {
		}
		// make sure insert is fail
		assert (ds.size() == 0);
	}

	/**
	 * get the key which is not insert in the data. Expect null be return
	 */
	@Test
	void test09_get_key_not_exist() {
		// set key and value
		String key = "1";
		String value = "one";
		// insert one pair
		ds.insert(key, value);
		// get another pair which is not in the data
		// make sure the return be null
		assert (ds.get("2") == null);
	}

	/**
	 * check if contain return false when the key dose not exist
	 */
	@Test
	void test010_contains_key_not_exist() {
		// set key and value
		String key = "1";
		String value = "one";
		// insert one pair
		ds.insert(key, value);
		// check contain method for another pair which is not in the data
		assert (!(ds.contains("2"))); // contain should return false if key do not exist
	}

	// TODO: add tests 05 - 07 as described in assignment

	// TODO: add more tests of your own design to ensure that you can detect
	// implementation that fail

	// Tip: consider different numbers of inserts and removes and how different
	// combinations of insert and removes

}
