import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// @SuppressWarnings("rawtypes")
public class TestBST {

  protected STADT<Integer, String> bst;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    bst = new BST<Integer, String>();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {}

  /**
   * CASE 123 Insert three values in sorted order and then check the root, left, and right keys to
   * see if insert worked correctly.
   */
  @Test
  void testBST_001_insert_sorted_order_simple() {
    try {
      bst.insert(10, "10");
      if (!bst.getKeyAtRoot().equals(10))
        fail("insert at root does not work");

      bst.insert(20, "20");
      if (!bst.getKeyOfRightChildOf(10).equals(20))
        fail("insert to right child of root does not work");

      bst.insert(30, "30");
      if (!bst.getKeyAtRoot().equals(10))
        fail("inserting 30 changed root");

      if (!bst.getKeyOfRightChildOf(20).equals(30))
        fail("inserting 30 as right child of 20");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(10));
      Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(20));
      Assert.assertEquals(bst.getKeyOfRightChildOf(20), Integer.valueOf(30));

      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  /**
   * CASE 321 Insert three values in reverse sorted order and then check the root, left, and right
   * keys to see if insert worked in the other direction.
   */
  @Test
  void testBST_002_insert_reversed_sorted_order_simple() {
    try {
      bst.insert(30, "30");
      if (!bst.getKeyAtRoot().equals(30))
        fail("insert at root does not work");

      bst.insert(20, "20");
      if (!bst.getKeyOfLeftChildOf(30).equals(20))
        fail("insert to left child of root does not work");

      bst.insert(10, "10");
      if (!bst.getKeyAtRoot().equals(30))
        fail("inserting 10 changed root");

      if (!bst.getKeyOfLeftChildOf(20).equals(10))
        fail("inserting 10 as left child of 20");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(30));
      Assert.assertEquals(bst.getKeyOfLeftChildOf(30), Integer.valueOf(20));
      Assert.assertEquals(bst.getKeyOfLeftChildOf(20), Integer.valueOf(10));

      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  /**
   * CASE 132 Insert three values so that rebalancing requires new key to be the "new" root of the
   * rebalanced tree.
   * 
   * Then check the root, left, and right keys to see if insert occurred correctly.
   */
  @Test
  void testBST_003_insert_smallest_largest_middle_order_simple() {
    try {
      bst.insert(10, "10");
      if (!bst.getKeyAtRoot().equals(10))
        fail("insert at root does not work");

      bst.insert(30, "30");
      if (!bst.getKeyOfRightChildOf(10).equals(30))
        fail("insert to right child of root does not work");
      Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(30));

      bst.insert(20, "20");
      if (!bst.getKeyAtRoot().equals(10))
        fail("inserting 20 changed root");

      if (!bst.getKeyOfLeftChildOf(30).equals(20))
        fail("inserting 20 as left child of 30");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(10));
      Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(30));
      Assert.assertEquals(bst.getKeyOfLeftChildOf(30), Integer.valueOf(20));

      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  /**
   * CASE 312 Insert three values so that rebalancing requires new key to be the "new" root of the
   * rebalanced tree.
   * 
   * Then check the root, left, and right keys to see if insert occurred correctly.
   */
  @Test
  void testBST_004_insert_largest_smallest_middle_order_simple() {
    try {
      bst.insert(30, "30");
      if (!bst.getKeyAtRoot().equals(30))
        fail("insert at root does not work");

      bst.insert(10, "10");
      if (!bst.getKeyOfLeftChildOf(30).equals(10))
        fail("insert to left child of root does not work");

      bst.insert(20, "20");
      if (!bst.getKeyAtRoot().equals(30))
        fail("inserting 10 changed root");

      if (!bst.getKeyOfRightChildOf(10).equals(20))
        fail("inserting 20 as right child of 10");

      // the tree should have 30 at the root
      // and 10 as its left child and 20 as 10's right child

      Assert.assertEquals(bst.getKeyAtRoot(), Integer.valueOf(30));
      Assert.assertEquals(bst.getKeyOfLeftChildOf(30), Integer.valueOf(10));
      Assert.assertEquals(bst.getKeyOfRightChildOf(10), Integer.valueOf(20));

      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }


  // TODO: Add your own tests

  // Add tests to make sure that bst grows as expected.
  // Does it maintain it's balance?
  @Test
  void testBST_005_bst_insert_inorder_equal() {
    try {
      List<Integer> levelorder = new ArrayList<>();
      levelorder.add(40);
      levelorder.add(20);
      levelorder.add(60);
      levelorder.add(10);
      levelorder.add(30);
      levelorder.add(50);
      levelorder.add(70);
      bst.insert(40, "40");
      bst.insert(20, "20");
      bst.insert(60, "60");
      bst.insert(10, "10");
      bst.insert(30, "30");
      bst.insert(50, "50");
      bst.insert(70, "70");
      Assert.assertEquals(levelorder, bst.getLevelOrderTraversal());
      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  // Does the height of the tree reflect it's actual height
  // Use the traversal orders to check.
  @Test
  void testBST_006_bst_insert_height() {
    try {
      bst.insert(10, "10");
      Assert.assertEquals(1, bst.getHeight());
      bst.insert(20, "20");
      Assert.assertEquals(2, bst.getHeight());
      bst.insert(30, "30");
      Assert.assertEquals(3, bst.getHeight());
      bst.insert(40, "40");
      Assert.assertEquals(4, bst.getHeight());
      bst.insert(50, "50");
      Assert.assertEquals(5, bst.getHeight());
      bst.insert(60, "60");
      Assert.assertEquals(6, bst.getHeight());
      bst.insert(70, "70");
      Assert.assertEquals(7, bst.getHeight());
      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }

  }

  // Can you insert many and still "get" them back out?
  @Test
  void testBST_007_insert_balanced_get() {
    try {
      bst.insert(40, "40");
      bst.insert(20, "20");
      bst.insert(60, "60");
      bst.insert(10, "10");
      bst.insert(30, "30");
      bst.insert(50, "50");
      bst.insert(70, "70");
      
      Assert.assertEquals(bst.get(20), "20");
      Assert.assertEquals(bst.get(50), "50");
      
      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  @Test
  void testBST_08_insert_inorder_get() {
    try {
      bst.insert(10, "10");
      bst.insert(20, "20");
      bst.insert(30, "30");
      bst.insert(40, "40");
      bst.insert(50, "50");
      bst.insert(60, "60");
      bst.insert(70, "70");
      
      Assert.assertEquals(bst.get(40), "40");
      Assert.assertEquals(bst.get(60), "60");

      bst.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  // Does delete work?
  // If delete is not implemented, does calling it throw an UnsupportedOperationException
  @Test
  void testBST_009_insert_balanced_remove_root() {
    try {      
      bst.insert(40, "40");
      bst.insert(20, "20");
      bst.insert(60, "60");
      bst.insert(10, "10");
      bst.insert(30, "30");
      bst.insert(50, "50");
      bst.insert(70, "70");
      
      Assert.assertTrue(bst.remove(40));
      Assert.assertTrue(bst.getKeyAtRoot() == 50);
      bst.get(40);

    } catch (KeyNotFoundException e) {
      List<Integer> levelorder = new ArrayList<>();
      levelorder.add(50);
      levelorder.add(20);
      levelorder.add(60);
      levelorder.add(10);
      levelorder.add(30);
      levelorder.add(70);
      Assert.assertEquals(bst.getLevelOrderTraversal(), levelorder);
      
      bst.print();

      
    }catch (Exception e1) {
      e1.printStackTrace();
      fail("Unexpected exception 001: " + e1.getMessage());
    }
  }

}
