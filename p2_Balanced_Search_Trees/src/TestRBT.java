import static org.junit.Assert.fail;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;

// TODO: Add tests to test if a Red-Black tree
// extension of BST is correct. Mostly check node color and position

// @SuppressWarnings("rawtypes")
public class TestRBT {

  protected RBT<Integer, String> rbt;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    rbt = new RBT<Integer, String>();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {}

  /**
   * CASE 123 Insert three values in sorted order and then check the root, left, and right keys to
   * see if RBT rebalancing occurred.
   * 
   */
  @Test
  void testRBT_001_insert_sorted_order_simple() {
    try {
      rbt.insert(10, "10");
      Assert.assertTrue(rbt.rootIsBlack());

      rbt.insert(20, "20");
      Assert.assertTrue(rbt.getKeyOfRightChildOf(10).equals(20));
      Assert.assertEquals(rbt.colorOf(20), RBT.RED);

      rbt.insert(30, "30"); // SHOULD CAUSE REBALANCING
      Assert.assertTrue(rbt.getKeyOfRightChildOf(20).equals(30));

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child
      Assert.assertEquals(rbt.getKeyAtRoot(), Integer.valueOf(20));
      Assert.assertEquals(rbt.getKeyOfLeftChildOf(20), Integer.valueOf(10));
      Assert.assertEquals(rbt.getKeyOfRightChildOf(20), Integer.valueOf(30));

      rbt.print();

    } catch (Exception e) {
      // e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  /**
   * CASE 321 Insert three values in reverse sorted order and then check the root, left, and right
   * keys to see if rebalancing occurred in the other direction.
   */
  @Test
  void testRBT_002_insert_reversed_sorted_order_simple() {

    // TODO: implement this test
    try {
      rbt.insert(30, "30"); // SHOULD CAUSE REBALANCING
      Assert.assertTrue(rbt.rootIsBlack());

      rbt.insert(20, "20");
      Assert.assertTrue(rbt.getKeyOfLeftChildOf(30).equals(20));
      Assert.assertEquals(rbt.colorOf(20), RBT.RED);

      rbt.insert(10, "10");
      Assert.assertTrue(rbt.getKeyOfLeftChildOf(20).equals(10));

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child
      Assert.assertEquals(rbt.getKeyAtRoot(), Integer.valueOf(20));
      Assert.assertEquals(rbt.getKeyOfRightChildOf(20), Integer.valueOf(30));
      Assert.assertEquals(rbt.getKeyOfLeftChildOf(20), Integer.valueOf(10));

      rbt.print();

    } catch (Exception e) {
      // e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  /**
   * CASE 132 Insert three values so that rebalancing requires new key to be the "new" root of the
   * rebalanced tree.
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred correctly.
   */
  @Test
  void testRBT_003_insert_smallest_largest_middle_order_simple() {

    // TODO: implement this test
    try {
      rbt.insert(10, "10");
      Assert.assertTrue(rbt.rootIsBlack());

      rbt.insert(30, "30");
      Assert.assertTrue(rbt.getKeyOfRightChildOf(10).equals(30));
      Assert.assertEquals(rbt.colorOf(30), RBT.RED);

      rbt.insert(20, "20"); // SHOULD CAUSE REBALANCING
      Assert.assertTrue(rbt.getKeyOfRightChildOf(20).equals(30));

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child
      Assert.assertEquals(rbt.getKeyAtRoot(), Integer.valueOf(20));
      Assert.assertEquals(rbt.getKeyOfLeftChildOf(20), Integer.valueOf(10));
      Assert.assertEquals(rbt.getKeyOfRightChildOf(20), Integer.valueOf(30));

      rbt.print();

    } catch (Exception e) {
      // e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  /**
   * CASE 312 Insert three values so that rebalancing requires new key to be the "new" root of the
   * rebalanced tree.
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred correctly.
   */
  @Test
  void testRBT_004_insert_largest_smallest_middle_order_simple() {

    // TODO: implement this test
    try {
      rbt.insert(30, "30");
      Assert.assertTrue(rbt.rootIsBlack());

      rbt.insert(10, "10");
      Assert.assertTrue(rbt.getKeyOfLeftChildOf(30).equals(10));
      Assert.assertEquals(rbt.colorOf(10), RBT.RED);

      rbt.insert(20, "20"); // SHOULD CAUSE REBALANCING
      Assert.assertTrue(rbt.getKeyOfLeftChildOf(20).equals(10));

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child
      Assert.assertEquals(rbt.getKeyAtRoot(), Integer.valueOf(20));
      Assert.assertEquals(rbt.getKeyOfRightChildOf(20), Integer.valueOf(30));
      Assert.assertEquals(rbt.getKeyOfLeftChildOf(20), Integer.valueOf(10));

      rbt.print();

    } catch (Exception e) {
      // e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }

  }


  // TODO: Add your own tests

  // Add tests to make sure that rebalancing occurs even if the
  // tree is larger. Does it maintain it's balance?
  @Test
  void testRBT_005_insert_inorder_get_levelorder() {
    try {
      List<Integer> levelorder = Arrays.asList(new Integer[] {20, 10, 40, 30, 60, 50, 70});

      rbt.insert(10, "10");
      rbt.insert(20, "20");
      rbt.insert(30, "30");
      rbt.insert(40, "40");
      rbt.insert(50, "50");
      rbt.insert(60, "60");
      rbt.insert(70, "70");
      Assert.assertTrue(rbt.numKeys() == 7);
      Assert.assertTrue(rbt.isRed(40));
      Assert.assertTrue(rbt.isBlack(60));
      Assert.assertEquals(levelorder, rbt.getLevelOrderTraversal());
      rbt.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  // Does the height of the tree reflect it's actual height
  // Use the traversal orders to check.
  @Test
  void testRBT_006_insert_inorder_get_height() {
    try {
      rbt.insert(10, "10");
      rbt.insert(20, "20");
      Assert.assertTrue(rbt.getHeight() == 2);
      rbt.insert(30, "30");
      rbt.insert(40, "40");
      rbt.insert(50, "50");
      Assert.assertTrue(rbt.getHeight() == 3);
      rbt.insert(60, "60");
      rbt.insert(70, "70");
      Assert.assertTrue(rbt.getHeight() == 4);
      rbt.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  // Can you insert many and still "get" them back out?
  @Test
  void testRBT_007_insert_unbalanced_get() {
    try {
      rbt.insert(10, "10");
      rbt.insert(20, "20");
      rbt.insert(30, "30");
      rbt.insert(40, "40");
      rbt.insert(50, "50");
      rbt.insert(60, "60");
      rbt.insert(70, "70");

      Assert.assertEquals(rbt.get(20), "20");
      Assert.assertEquals(rbt.get(50), "50");

      rbt.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }
  }

  @Test
  void testRBT_008_order_traversal_test() {
    try {
      List<Integer> preorder = Arrays.asList(new Integer[] {20, 10, 40, 30, 60, 50, 70});
      List<Integer> inorder = Arrays.asList(new Integer[] {10, 20, 30, 40, 50, 60, 70});
      List<Integer> postorder = Arrays.asList(new Integer[] {10, 30, 50, 70, 60, 40, 20});
      List<Integer> levelorder = Arrays.asList(new Integer[] {20, 10, 40, 30, 60, 50, 70});

      rbt.insert(10, "10");
      rbt.insert(20, "20");
      rbt.insert(30, "30");
      rbt.insert(40, "40");
      rbt.insert(50, "50");
      rbt.insert(60, "60");
      rbt.insert(70, "70");

      if (!rbt.getPreOrderTraversal().equals(preorder))
        fail("Preorder Traversal does not done as it should be");
      if (!rbt.getInOrderTraversal().equals(inorder))
        fail("Inorder Traversal does not done as it should be");
      if (!rbt.getPostOrderTraversal().equals(postorder))
        fail("Postorder Traversal does not done as it should be");
      if (!rbt.getLevelOrderTraversal().equals(levelorder))
        fail("Levelorder Traversal does not done as it should be");

      rbt.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 001: " + e.getMessage());
    }

  }

} // copyright Deb Deppeler, all rights reserved, DO NOT SHARE
