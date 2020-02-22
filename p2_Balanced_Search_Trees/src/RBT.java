import java.util.ArrayList;
import java.util.List;

/**
 * Implements a generic Red-Black tree.
 *
 * DO NOT CHANGE THE METHOD SIGNATURES OF PUBLIC METHODS
 * DO NOT ADD ANY PACKAGE LEVEL OR PUBLIC ACCESS METHODS OR FIELDS.
 * 
 * You are not required to override remove. If you do not override this operation, you may still
 * have the method in your type, and have the method throw new UnsupportedOperationException. See
 * https://docs.oracle.com/javase/7/docs/api/java/lang/UnsupportedOperationException.html
 * 
 * @param <K> is the generic type of key, must be a Comparable tyle
 * @param <V> is the generic type of value
 */
public class RBT<K extends Comparable<K>, V> implements STADT<K, V> {

  private class Node<K, V> {
    private K key;
    private V value;
    private int color;
    private Node<K, V> left;
    private Node<K, V> right;
    private Node<K, V> parent;

    public Node(K k, V v, int c) {
      this.key = k;
      this.value = v;
      this.color = c;
    }

    public Node<K, V> sibiling() {
      if (parent == null)
        return null;
      else if (this == parent.left)
        return parent.right;
      else
        return parent.left;
    }

    public boolean equal(Object o) {
      if (o instanceof RBT.Node) {
        if (((RBT.Node) o).key.equals(this.key) && ((RBT.Node) o).value.equals(this.value))
          return true;
      }
      return false;
    }

  }

  private int size = 0;


  // USE AND DO NOT EDIT THESE CONSTANTS
  public static final int RED = 0;
  public static final int BLACK = 1;

  private Node<K, V> root;

  // TODO: define a default no-arg constructor
  public RBT() {}

  /**
   * Returns the color of the node that contains the specified key. Returns RBT.RED if the node is
   * red, and RBT.BLACK if the node is black. Returns -1 if the node is not found.
   * 
   * @param
   * @return
   */
  public int colorOf(K key) {
    Node<K, V> found = getNodeWith(root, key); // TODO Auto-generated method stub
    return found == null ? -1 : found.color;
  }

  private Node<K, V> getNodeWith(Node<K, V> root, K key) {
    if (root.key.equals(key))
      return root;
    if (root.key.compareTo(key) > 0)
      return getNodeWith(root.left, key);
    if (root.key.compareTo(key) < 0)
      return getNodeWith(root.right, key);
    return null;
  }

  /**
   * Returns true if the color of the root is black. If root is null, returns BLACK.
   * 
   * @return true if root is black, else returns false (for red)
   */
  public boolean rootIsBlack() {
    // TODO implement this method for your RBT
    return root.color == BLACK;
  }

  /**
   * Returns true if the node that contains this key is RED. If key is null, throws
   * IllegalNullKeyException. If key is not found, throws KeyNotFoundException.
   * 
   * @return true if the key is found and the node's color is RED, else return false if key is found
   *         and the node's color is BLACK.
   */
  public boolean isRed(K key) {
    return getNodeWith(root, key).color == RED;
  }

  /**
   * Returns true if the node that contains this key is BLACK. If key is null, throws
   * IllegalNullKeyException. If key is not found, throws KeyNotFoundException.
   * 
   * @return true if the key is found and the node's color is BLACK, else return false if key is
   *         found and the node's color is RED.
   */
  public boolean isBlack(K key) {
    return getNodeWith(root, key).color == BLACK;

  }

  @Override
  public K getKeyAtRoot() {
    // TODO Auto-generated method stub
    return root.key;
  }

  @Override
  public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    Node<K, V> keyNode = getNodeWith(root, key);
    if (keyNode == null)
      throw new KeyNotFoundException();
    if (keyNode.left == null)
      return null;
    return keyNode.left.key;
  }

  @Override
  public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    Node<K, V> keyNode = getNodeWith(root, key);
    if (keyNode == null)
      throw new KeyNotFoundException();
    if (keyNode.right == null)
      return null;
    return keyNode.right.key;
  }

  @Override
  public int getHeight() {
    return getHeight(root);
  }

  private int getHeight(Node<K, V> root) {
    if (root == null)
      return 0;
    else {
      int lheight = getHeight(root.left);
      int rheight = getHeight(root.right);
      if (lheight > rheight)
        return (lheight + 1);
      else
        return (rheight + 1);
    }
  }

  @Override
  public List<K> getInOrderTraversal() {
    List<K> output = new ArrayList<K>();
    InOrderTraversal(output, root);
    return output;
  }

  private void InOrderTraversal(List<K> list, Node<K, V> root) {
    if (root == null)
      return;
    if (root.left != null)
      InOrderTraversal(list, root.left);
    list.add(root.key);
    if (root.right != null)
      InOrderTraversal(list, root.right);
  }

  @Override
  public List<K> getPreOrderTraversal() {
    List<K> output = new ArrayList<K>();
    PreOrderTraversal(output, root);
    return output;
  }

  private void PreOrderTraversal(List<K> list, Node<K, V> root) {
    if (root == null)
      return;
    list.add(root.key);
    if (root.left != null)
      PreOrderTraversal(list, root.left);
    if (root.right != null)
      PreOrderTraversal(list, root.right);
  }

  @Override
  public List<K> getPostOrderTraversal() {
    List<K> output = new ArrayList<K>();
    PostOrderTraversal(output, root);
    return output;
  }

  private void PostOrderTraversal(List<K> list, Node<K, V> root) {
    if (root == null)
      return;
    if (root.left != null)
      PostOrderTraversal(list, root.left);
    if (root.right != null)
      PostOrderTraversal(list, root.right);
    list.add(root.key);
  }

  @Override
  public List<K> getLevelOrderTraversal() {
    List<K> output = new ArrayList<K>();
    for (int i = 1; i <= getHeight(); i++)
      LevelOrderTraversal(root, i, output);
    return output;
  }

  private void LevelOrderTraversal(Node<K, V> root, int level, List<K> list) {
    if (root == null)
      return;
    if (level == 1)
      list.add(root.key);
    else if (level > 1) {
      LevelOrderTraversal(root.left, level - 1, list);
      LevelOrderTraversal(root.right, level - 1, list);
    }
  }

  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    Node<K, V> input = new Node<K, V>(key, value, RED);
    root = insert(input, root);
    if (root == input) input.color = BLACK;
    insertFix(root);
  }
  private Node<K, V> insert(Node<K, V> node, Node<K, V> root) throws DuplicateKeyException {
    if (root == null) {
      size++;
      return node;
    }
    if (root.equal(node))
      throw new DuplicateKeyException();
    if (root.key.compareTo(node.key) > 0) {
      root.left = insert(node, root.left);
      root.left.parent = root;
    }
    if (root.key.compareTo(node.key) < 0) {
      root.right = insert(node, root.right);
      root.right.parent = root;
    }
    return root;
  }
  private void insertFix(Node<K, V> node) {
    if(node.left != null) insertFix(node.left);
    if(node.right != null) insertFix(node.right);
    
    if(node == null || node.parent == null || node.parent.parent == null) return;
    
    Node<K, V> c = node, p = c.parent, g = p.parent, s = p.sibiling();
    if(p.color == BLACK) return;
    
    if(s == null) {
      if(p == g.left) {
        rotateRight(g);
      } else {
        rotateLeft(g);
      }
    } else if (s.color == RED) {
      p.color = BLACK;
      s.color = BLACK;
      if(g != this.root) g.color = RED;
    }
  }

  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    int sizeBefore = size;
    root = remove(root, key);
    if (size == sizeBefore)
      return false;
    else
      return true;
  }

  private Node<K, V> remove(Node<K, V> root, K key) {
    if (root == null)
      return null;

    if (root.key.compareTo(key) > 0)
      root.left = remove(root.left, key);
    if (root.key.compareTo(key) < 0)
      root.right = remove(root.right, key);

    // if node has one child
    if (root.left == null)
      return root.right;
    if (root.right == null)
      return root.left;

    // if node has two children
    Node<K, V> minChild = root.right;
    while (minChild.left != null)
      minChild = root.left;

    root.key = minChild.key;
    root.value = minChild.value;
    root.right = remove(root.right, minChild.key);

    size--;
    return root;
  }

  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    Node<K, V> node = getNodeWith(root, key);
    if (node == null)
      throw new KeyNotFoundException();
    return node.value;
  }

  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    return getNodeWith(root, key) != null;
  }

  @Override
  public int numKeys() {
    // TODO Auto-generated method stub
    return size;
  }

  @Override
  public void print() {
    System.out.println("----------");
    print(root, 0);
    System.out.println("----------");
  }

  private void print(Node<K, V> root, int space) {
    if (root == null)
      return;

    space += 5;

    print(root.right, space);

    System.out.print("\n");
    for (int i = 5; i < space; i++)
      System.out.print(" ");
    if (root.color == RED)
      System.out.print("R");
    else
      System.out.print("B");
    System.out.print(root.key + "\n");

    // Process left child
    print(root.left, space);
  }


  // TODO: override the insert method so that it rebalances
  // according to red-black tree insert algorithm.


  private void rotateLeft(Node<K, V> root) {
    if (root.parent != null) {
      if (root == root.parent.left) {
        root.parent.left = root.right;
      } else {
        root.parent.right = root.right;
      }
      root.right.parent = root.parent;
      root.parent = root.right;
      if (root.right.left != null) {
        root.right.left.parent = root;
      }
      root.right = root.right.left;
      root.parent.left = root;
    } else {
      Node<K, V> right = root.right;
      root.right = right.left;
      if(right.left != null) right.left.parent = root;
      root.parent = right;
      right.left = root;
      right.parent = null;
      root = right;
    }
  }

  private void rotateRight(Node<K, V> root) {
    if (root.parent != null) {
      if (root == root.parent.left) {
        root.parent.left = root.left;
      } else {
        root.parent.right = root.left;
      }

      root.left.parent = root.parent;
      root.parent = root.left;
      if (root.left.right != null) {
        root.left.right.parent = root;
      }
      root.left = root.left.right;
      root.parent.right = root;
    } else {
      Node<K, V> left = root.left;
      root.left = left.right;
      if(left.right != null) left.right.parent = root;
      root.parent = left;
      left.right = root;
      left.parent = null;
      root = left;
    }
  }


  // TODO [OPTIONAL] you may override print() to include
  // color R-red or B-black.

}
