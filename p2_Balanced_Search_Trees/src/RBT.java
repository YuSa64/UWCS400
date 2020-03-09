import java.util.ArrayList;
import java.util.List;

/**
 * Implements a generic Red-Black tree.
 *
 * DO NOT CHANGE THE METHOD SIGNATURES OF PUBLIC METHODS DO NOT ADD ANY PACKAGE LEVEL OR PUBLIC
 * ACCESS METHODS OR FIELDS.
 * 
 * You are not required to override remove. If you do not override this operation, you may still
 * have the method in your type, and have the method throw new UnsupportedOperationException. See
 * https://docs.oracle.com/javase/7/docs/api/java/lang/UnsupportedOperationException.html
 * 
 * @param <K> is the generic type of key, must be a Comparable tyle
 * @param <V> is the generic type of value
 */
public class RBT<K extends Comparable<K>, V> implements STADT<K, V> {

  /**
   * Node sub-class of the RBT
   * 
   * @author Jun Jeong
   *
   * @param <K> A Comparable type to be used as a key to an associated value.
   * @param <V> A value associated with the given key.
   */
  private class Node<K, V> {
    private K key;
    private V value;
    private int color;
    private Node<K, V> left;
    private Node<K, V> right;
    private Node<K, V> parent;

    /**
     * Constructor of the node
     * 
     * @param k A key to an associated value.
     * @param v A value associated with the given key.
     */
    public Node(K k, V v) {
      this.key = k;
      this.value = v;
      this.color = RED;
    }

    /**
     * Returns a sibling of the node if parents exist
     * 
     * @return A sibling node of the node
     */
    public Node<K, V> sibling() {
      if (parent == null)
        return null;
      else if (this == parent.left)
        return parent.right;
      else
        return parent.left;
    }

    /**
     * Returns true if key and value are equal to each other. Otherwise, returns false.
     * 
     * @param o An object to compare with the node
     * @return true if key and value are equal to each other
     */
    public boolean equal(Object o) {
      if (o instanceof RBT.Node) {
        if (((RBT.Node) o).key.equals(this.key) && ((RBT.Node) o).value.equals(this.value))
          return true;
      }
      return false;
    }

  }



  // USE AND DO NOT EDIT THESE CONSTANTS
  public static final int RED = 0;
  public static final int BLACK = 1;

  private Node<K, V> root;
  private int size;

  // TODO: define a default no-arg constructor
  public RBT() {
    root = null;
    size = 0;
  }

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

  /**
   * Recursive method to find the node that has specific key. If node with the key does not exist,
   * returns null.
   * 
   * @param root A root node to search
   * @param key A key of the node to be found
   * @return node with the key, or null
   */
  private Node<K, V> getNodeWith(Node<K, V> root, K key) {
    if (root == null)
      return null;
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

  /**
   * Returns the key that is in the root node of this ST. If root is null, returns null.
   * 
   * @return key found at root node, or null
   */
  public K getKeyAtRoot() {
    // TODO Auto-generated method stub
    return root.key;
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a matching node is found, it
   * returns the returns the key that is in the left child. If the left child of the found node is
   * null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the left child of the found key
   * 
   * @throws IllegalNullKeyException if key argument is null
   * @throws KeyNotFoundException if key is not found in this BST
   */
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

  /**
   * Tries to find a node with a key that matches the specified key. If a matching node is found, it
   * returns the returns the key that is in the right child. If the right child of the found node is
   * null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the right child of the found key
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException if key is not found in this BST
   */
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

  /**
   * Returns the height of this BST. H is defined as the number of levels in the tree.
   * 
   * If root is null, return 0 If root is a leaf, return 1 Else return 1 + max( height(root.left),
   * height(root.right) )
   * 
   * Examples: A BST with no keys, has a height of zero (0). A BST with one key, has a height of one
   * (1). A BST with two keys, has a height of two (2). A BST with three keys, can be balanced with
   * a height of two(2) or it may be linear with a height of three (3) ... and so on for tree with
   * other heights
   * 
   * @return the number of levels that contain keys in this BINARY SEARCH TREE
   */
  public int getHeight() {
    return getHeight(root);
  }

  /**
   * Recursive helper method as well as overloading method of getHeight
   * 
   * @param root A root node to find the height
   * @return height of the node
   */
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

  /**
   * Returns the keys of the data structure in sorted order. In the case of binary search trees, the
   * visit order is: L V R
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in-order
   */
  public List<K> getInOrderTraversal() {
    List<K> output = new ArrayList<K>();
    InOrderTraversal(output, root);
    return output;
  }

  /**
   * Recursive helper method of getInOrderTraversal Left -> Current -> Right
   * 
   * @param list A list to store nodes in order
   * @param root A root node of the tree
   */
  private void InOrderTraversal(List<K> list, Node<K, V> root) {
    // if root is leaf
    if (root == null)
      return;
    // do left subtree
    InOrderTraversal(list, root.left);
    // add key of the root to the list
    list.add(root.key);
    // do right subtree
    InOrderTraversal(list, root.right);
  }

  /**
   * Returns the keys of the data structure in pre-order traversal order. In the case of binary
   * search trees, the order is: V L R
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in pre-order
   */
  public List<K> getPreOrderTraversal() {
    List<K> output = new ArrayList<K>();
    PreOrderTraversal(output, root);
    return output;
  }

  /**
   * Recursive helper method of getPreOrderTraversal Current -> Left -> Right
   * 
   * @param list A list to store nodes in order
   * @param root A root node of the tree
   */
  private void PreOrderTraversal(List<K> list, Node<K, V> root) {
    // if root is leaf
    if (root == null)
      return;
    // add key of the root to the list
    list.add(root.key);
    // do left subtree
    PreOrderTraversal(list, root.left);
    // do right subtree
    PreOrderTraversal(list, root.right);
  }

  /**
   * Returns the keys of the data structure in post-order traversal order. In the case of binary
   * search trees, the order is: L R V
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in post-order
   */
  public List<K> getPostOrderTraversal() {
    List<K> output = new ArrayList<K>();
    PostOrderTraversal(output, root);
    return output;
  }

  /**
   * Recursive helper method of getPostOrderTraversal Left -> Right -> Current
   * 
   * @param list A list to store nodes in order
   * @param root A root node of the tree
   */
  private void PostOrderTraversal(List<K> list, Node<K, V> root) {
    // if root is leaf
    if (root == null)
      return;
    // do left subtree
    PostOrderTraversal(list, root.left);
    // do right subtree
    PostOrderTraversal(list, root.right);
    // add key of the root to the list
    list.add(root.key);
  }

  /**
   * Returns the keys of the data structure in level-order traversal order.
   * 
   * The root is first in the list, then the keys found in the next level down, and so on.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in level-order
   */
  public List<K> getLevelOrderTraversal() {
    List<K> output = new ArrayList<K>();
    // repeat for each level
    for (int i = 1; i <= getHeight(); i++)
      LevelOrderTraversal(root, i, output);
    return output;
  }

  /**
   * Helper method of getLevelOrderTraversal Upper Level -> Lower Level
   * 
   * @param list A list to store nodes in order
   * @param root A root node of the tree
   */
  private void LevelOrderTraversal(Node<K, V> root, int level, List<K> list) {
    // if root is leaf
    if (root == null)
      return;
    // if level is 1 (designated level)
    if (level == 1)
      list.add(root.key);
    // otherwise, search through child nodes
    else if (level > 1) {
      LevelOrderTraversal(root.left, level - 1, list);
      LevelOrderTraversal(root.right, level - 1, list);
    }
  }



  // TODO: override the insert method so that it rebalances
  // according to red-black tree insert algorithm.


  /**
   * Rotates tree which the node is the root by the left side
   * 
   * @param node A parent node to be rotated
   */
  private void rotateLeft(Node<K, V> node) {
    // if the node is not a root
    if (node.parent != null) {
      // let the right child replace the node
      // if node is left child of the parent
      if (node == node.parent.left) {
        node.parent.left = node.right;
      } else { // if node is right child of the parent
        node.parent.right = node.right;
      }
      node.right.parent = node.parent;
      node.parent = node.right;

      // move rest of the child
      if (node.right.left != null) {
        node.right.left.parent = node;
      }
      node.right = node.right.left;
      node.parent.left = node;
    } else { // if the node is a root
      Node<K, V> right = root.right;
      root.right = right.left;
      if (right.left != null)
        right.left.parent = root;
      root.parent = right;
      right.left = root;
      right.parent = null;
      root = right;
    }
  }

  /**
   * Rotates tree which the node is the root by the right side
   * 
   * @param node A grand node to be rotated
   */
  private void rotateRight(Node<K, V> node) {
    // if the node is not a root
    if (node.parent != null) {
      // let the left child replace the node
      // if node is left child of the parent
      if (node == node.parent.left) {
        node.parent.left = node.left;
      } else { // if node is right child of the parent
        node.parent.right = node.left;
      }
      node.left.parent = node.parent;
      node.parent = node.left;

      // move rest of the child
      if (node.left.right != null) {
        node.left.right.parent = node;
      }
      node.left = node.left.right;
      node.parent.right = node;
    } else { // if the node is a root
      Node<K, V> left = root.left;
      root.left = left.right;
      if (left.right != null)
        left.right.parent = root;
      root.parent = left;
      left.right = root;
      left.parent = null;
      root = left;
    }
  }

  /**
   * Add the key,value pair to the data structure and increase the number of keys. If key is null,
   * throw IllegalNullKeyException; If key is already in data structure, throw
   * DuplicateKeyException(); Do not increase the num of keys in the structure, if key,value pair is
   * not added.
   */
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    Node<K, V> input = new Node<K, V>(key, value);
    root = insert(input, root);
    insertFix(root);
    root.color = BLACK;

  }

  /**
   * Recursive helper as well as overload method of insert. Returns the modified root.
   * 
   * @param node A node to insert
   * @param root A root of the tree
   * @return root with the node inserted or not
   * @throws DuplicateKeyException if the node already exist
   */
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

  /**
   * Recursive method to fix the insert violations of the RBT
   * 
   * @param node A root node whose tree needs to be fixed
   */
  private void insertFix(Node<K, V> node) {
    // uses PostOrderTraversal
    if (node.left != null)
      insertFix(node.left);
    if (node.right != null)
      insertFix(node.right);

    // if there's no grand/parent node exist
    if (node == null || node.parent == null || node.parent.parent == null)
      return;

    Node<K, V> c = node, p = c.parent, g = p.parent, s = p.sibling();

    // if parent node is black, there's no violation
    if (p.color == BLACK)
      return;

    // otherwise,
    if (s == null) { // if there's no sibling
      if (p == g.left) { // if parent is left child of the grand node -> Left
        if (c == p.right) {// if it is right child -> Left Right
          // rotate p and c
          rotateLeft(p);
          c = p;
          p = c.parent;
        }
        // Left Left
        rotateRight(g);
      } else { // parent is right child of the grand node -> Right
        if (c == p.left) { // if it is left child -> Right Left
          // rotate p and c
          rotateRight(p);
          c = p;
          p = c.parent;
        }
        // Left Left
        rotateLeft(g);
      }
      // re color nodes
      g.color = RED;
      p.color = BLACK;
    } else if (s.color == RED) { // if there's sibling and it is red
      // re color nodes
      p.color = BLACK;
      s.color = BLACK;
      if (g != this.root)
        g.color = RED;
    }
  }

  /**
   * If key is found, remove the key,value pair from the data structure and decrease num keys, and
   * return true. If key is not found, do not decrease the number of keys in the data structure,
   * return false. If key is null, throw IllegalNullKeyException
   */
  public boolean remove(K key) throws IllegalNullKeyException {
    // if key is null
    if (key == null)
      throw new IllegalNullKeyException();
    // otherwise
    int sizeBefore = size;
    root = remove(root, key);
    // if the size does not changed
    if (size == sizeBefore)
      return false;
    // otherwise
    else
      return true;
  }

  /**
   * Recursive helper as well as overload method of the remove. Returns the modified root.
   * 
   * @param node A node to insert
   * @param key A key of the node to remove
   * @return root with the node removed or not
   */
  private Node<K, V> remove(Node<K, V> root, K key) {
    // if root is leaf
    if (root == null)
      return null;

    // if key of the root is lager than the key, go left subtree
    if (root.key.compareTo(key) > 0)
      root.left = remove(root.left, key);
    // otherwise, go right subtree
    else if (root.key.compareTo(key) < 0)
      root.right = remove(root.right, key);

    // if node has one child
    // if left node is null
    else if (root.left == null)
      return root.right;
    // if right node is null
    else if (root.right == null)
      return root.left;
    else {
      // if node has two children
      Node<K, V> minChild = root.right;
      while (minChild.left != null)
        minChild = minChild.left;
      // switch node with the in-order successor
      root.key = minChild.key;
      root.value = minChild.value;
      // remove the successor
      root.right = remove(root.right, minChild.key);
      // after remover, decrease the size
      size--;
    }
    return root;
  }

  /**
   * Returns the value associated with the specified key.
   *
   * Does not remove key or decrease number of keys If key is null, throw IllegalNullKeyException If
   * key is not found, throw KeyNotFoundException().
   */
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // if key is null
    if (key == null)
      throw new IllegalNullKeyException();
    // use the getNodeWith as it is same with the get
    Node<K, V> node = getNodeWith(root, key);
    // if node returned is null
    if (node == null)
      throw new KeyNotFoundException();
    // otherwise, return value
    return node.value;
  }

  /**
   * Returns true if the key is in the data structure If key is null, throw IllegalNullKeyException
   * Returns false if key is not null and is not present
   */
  public boolean contains(K key) throws IllegalNullKeyException {
    // if key is null
    if (key == null)
      throw new IllegalNullKeyException();
    // use getNodeWith as a finder
    return getNodeWith(root, key) != null;
  }

  /**
   * Returns the number of key,value pairs in the data structure
   */
  public int numKeys() {
    return size;
  }

  // TODO [OPTIONAL] you may override print() to include
  // color R-red or B-black.
  public void print() {
    System.out.println("----------");
    print(root, 0);
    System.out.println("----------");
  }

  private void print(Node<K, V> root, int space) {
    if (root == null)
      return;

    // increase the space so that level can be distinguished
    space += 6;

    // use right to left in-order traversal
    print(root.right, space);
    // first line
    System.out.print("|");
    // repeat for from second level
    for (int i = 6; i < space; i++) {
      // if there's parent's branch
      if (i < space - 7)
        System.out.print(" ");
      // as parent's branch ended, print start line
      else if (i == space - 7)
        System.out.print("|");
      // print branch till right before the end
      else if (i != space - 1)
        System.out.print("-");
      // print end line at the end
      else
        System.out.print("|");
    }
    // print the key
    // print Color
    if (root.color == RED)
      System.out.print("R");
    else
      System.out.print("B");
    System.out.print(root.key + "\n");

    print(root.left, space);
  }

}
