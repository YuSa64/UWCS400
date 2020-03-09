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

  /**
   * Node sub-class of the BST
   * 
   * @author Jun Jeong
   *
   * @param <K> A Comparable type to be used as a key to an associated value.
   * @param <V> A value associated with the given key.
   */

  private class Node<K, V> {
    private K key; // Key of the node
    private V value; // Value of the node
    private Node<K, V> left; // Left subtree/node of the node
    private Node<K, V> right; // Right subtree/node of the node

    /**
     * Constructor of the node
     * 
     * @param k A key to an associated value.
     * @param v A value associated with the given key.
     */
    public Node(K k, V v) {
      this.key = k;
      this.value = v;
    }

    /**
     * Returns true if key and value are equal to each other. Otherwise, returns false.
     * 
     * @param o An object to compare with the node
     * @return true if key and value are equal to each other
     */
    public boolean equal(Object o) {
      if (o instanceof BST.Node) {
        if (((BST.Node) o).key.equals(this.key) && ((BST.Node) o).value.equals(this.value))
          return true;
      }
      return false;
    }

  }


  private Node<K, V> root; // root node of the BST
  private int size = 0; // size a.k.a. number of nodes in the BST


  /**
   * Returns the key that is in the root node of this ST. If root is null, returns null.
   * 
   * @return key found at root node, or null
   */
  public K getKeyAtRoot() {
    // if root is null
    if (root == null)
      return null;
    // otherwise
    return root.key;
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
    // if root is null
    if (root == null)
      return null;
    // else, if root has the key
    if (root.key.equals(key))
      return root;
    // else, if key of the root is larger than the key
    if (root.key.compareTo(key) > 0)
      return getNodeWith(root.left, key);
    // otherwise
    if (root.key.compareTo(key) < 0)
      return getNodeWith(root.right, key);
    // if none of above matches
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
    // if key is null
    if (key == null)
      throw new IllegalNullKeyException();
    // else, find node with the key
    Node<K, V> keyNode = getNodeWith(root, key);
    // if the node does not exist
    if (keyNode == null)
      throw new KeyNotFoundException();
    // else, if the left of the node does not exist
    if (keyNode.left == null)
      return null;
    // otherwise
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
    // if key is null
    if (key == null)
      throw new IllegalNullKeyException();
    // else, find node with the key
    Node<K, V> keyNode = getNodeWith(root, key);
    // if the node does not exist
    if (keyNode == null)
      throw new KeyNotFoundException();
    // else, if the right of the node does not exist
    if (keyNode.right == null)
      return null;
    // otherwsie
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
    // return the recursive method
    return getHeight(root);
  }

  /**
   * Recursive helper method as well as overloading method of getHeight
   * 
   * @param root A root node to find the height
   * @return height of the node
   */
  private int getHeight(Node<K, V> root) {
    // if root is leaf node
    if (root == null)
      return 0;
    // otherwise
    else {
      int lheight = getHeight(root.left);
      int rheight = getHeight(root.right);
      // find the larger height from either left or right subtree
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


  /**
   * Add the key,value pair to the data structure and increase the number of keys. If key is null,
   * throw IllegalNullKeyException; If key is already in data structure, throw
   * DuplicateKeyException(); Do not increase the num of keys in the structure, if key,value pair is
   * not added.
   */
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    // if key is null
    if (key == null)
      throw new IllegalNullKeyException();
    // othewise
    Node<K, V> input = new Node<K, V>(key, value);
    root = insert(input, root);
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

  /**
   * Recursive helper as well as overload method of print
   * 
   * @param root A root node of the tree
   * @param space A space for each branch
   */
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
    System.out.print(root.key + "\n");

    print(root.left, space);
  }

} // copyrighted material, students do not have permission to post on public sites



// deppeler@cs.wisc.edu
