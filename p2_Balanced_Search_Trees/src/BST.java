import java.util.ArrayList;
import java.util.List;

// DO IMPLEMENT A BINARY SEARCH TREE IN THIS CLASS

/**
 * Defines the operations required of student's BST class.
 *
 * NOTE: There are many methods in this interface that are required solely to support gray-box
 * testing of the internal tree structure. They must be implemented as described to pass all grading
 * tests.
 * 
 * @author Deb Deppeler (deppeler@cs.wisc.edu)
 * @param <K> A Comparable type to be used as a key to an associated value.
 * @param <V> A value associated with the given key.
 */
public class BST<K extends Comparable<K>, V> implements STADT<K, V> {

  private class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> left;
    private Node<K, V> right;

    public Node(K k, V v) {
      this.key = k;
      this.value = v;
    }

    public boolean equal(Object o) {
      if (o instanceof BST.Node) {
        if (((BST.Node) o).key.equals(this.key) && ((BST.Node) o).value.equals(this.value))
          return true;
      }
      return false;
    }

  }


  private Node<K, V> root;
  private int size = 0;


  /**
   * Returns the key that is in the root node of this ST. If root is null, returns null.
   * 
   * @return key found at root node, or null
   */
  public K getKeyAtRoot() {
    if (root == null)
      return null;
    return root.key;
  }

  private Node<K, V> getNodeWith(Node<K, V> root, K key) {
    if(root == null)
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

  private void InOrderTraversal(List<K> list, Node<K, V> root) {
    if (root == null)
      return;
    if (root.left != null)
      InOrderTraversal(list, root.left);
    list.add(root.key);
    if (root.right != null)
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

  private void PreOrderTraversal(List<K> list, Node<K, V> root) {
    if (root == null)
      return;
    list.add(root.key);
    if (root.left != null)
      PreOrderTraversal(list, root.left);
    if (root.right != null)
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

  private void PostOrderTraversal(List<K> list, Node<K, V> root) {
    if (root == null)
      return;
    if (root.left != null)
      PostOrderTraversal(list, root.left);
    if (root.right != null)
      PostOrderTraversal(list, root.right);
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
  }

  private Node<K, V> insert(Node<K, V> node, Node<K, V> root) throws DuplicateKeyException {
    if (root == null) {
      size++;
      return node;
    }
    if (root.equal(node))
      throw new DuplicateKeyException();
    if (root.key.compareTo(node.key) > 0)
      root.left = insert(node, root.left);
    if (root.key.compareTo(node.key) < 0)
      root.right = insert(node, root.right);
    return root;
  }



  /**
   * If key is found, remove the key,value pair from the data structure and decrease num keys, and
   * return true. If key is not found, do not decrease the number of keys in the data structure,
   * return false. If key is null, throw IllegalNullKeyException
   */
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
    else if (root.key.compareTo(key) < 0)
      root.right = remove(root.right, key);

    // if node has one child
    else if (root.left == null)
      return root.right;
    else if (root.right == null)
      return root.left;
    else {
      // if node has two children
      Node<K, V> minChild = root.right;
      while (minChild.left != null)
        minChild = minChild.left;
  
      root.key = minChild.key;
      root.value = minChild.value;
      root.right = remove(root.right, minChild.key);
  
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
    if (key == null)
      throw new IllegalNullKeyException();
    Node<K, V> node = getNodeWith(root, key);
    if (node == null)
      throw new KeyNotFoundException();
    return node.value;
  }

  /**
   * Returns true if the key is in the data structure If key is null, throw IllegalNullKeyException
   * Returns false if key is not null and is not present
   */
  public boolean contains(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    return getNodeWith(root, key) != null;
  }

  /**
   * Returns the number of key,value pairs in the data structure
   */
  public int numKeys() {
    return size;
  }

  /**
   * Print the tree. 
   *
   * For our testing purposes of your print method: 
   * all keys that we insert in the tree will have 
   * a string length of exactly 2 characters.
   * example: numbers 10-99, or strings aa - zz, or AA to ZZ
   *
   * This makes it easier for you to not worry about spacing issues.
   *
   * You can display a binary search in any of a variety of ways, 
   * but we must see a tree that we can identify left and right children 
   * of each node
   *
   * For example: 
   
         30
         /\
        /  \
       20  40
       /   /\
      /   /  \
     10  35  50 

     Look from bottom to top. Inorder traversal of above tree (10,20,30,35,40,50)
     
     Or, you can display a tree of this kind.

     |       |-------50
     |-------40
     |       |-------35
     30
     |-------20
     |       |-------10
     
     Or, you can come up with your own orientation pattern, like this.

     10                 
             20
                     30
     35                
             40
     50                  

     The connecting lines are not required if we can interpret your tree.

   */

  public void print() {
    System.out.println("----------");
    print(root, 0);
    System.out.println("----------");
  }

  private void print(Node<K, V> root, int space) {
    if (root == null)
      return;

    space += 6;

    print(root.right, space);
    System.out.print("|");
    for (int i = 6; i < space; i++) {
      if (i < space - 7)
        System.out.print(" ");
      else if (i == space - 7)
        System.out.print("|");
      else if (i != space - 1)
        System.out.print("-");
      else
        System.out.print("|");
    }
    System.out.print(root.key + "\n");

    // Process left child
    print(root.left, space);
  }

} // copyrighted material, students do not have permission to post on public sites



// deppeler@cs.wisc.edu
