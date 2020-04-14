import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Filename:   BPTree.java
 * Project:    p5 (BPlus Tree)
 * Authors:    YONGHEE, HAN
 * Email:      applehan8960@gmail.com
 * due date:   Dec 11 by 11:59pm
 * 
 */

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes for internal nodes of the tree
    private int branchingFactor;

	
    /**
     * Public constructor
     * i
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        
        this.branchingFactor = branchingFactor;
        root = null;	// empty tree at start
    }
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
    	LeafNode node;
    	
    	if (key == null) {
    		throw new IllegalArgumentException();
    	}
    	
        // search a leaf node for key-value should go into
    	if (root == null) {
    		// case 1 : root is null
    		node = new LeafNode();
    		node.keys = new ArrayList<K>();
    		node.values = new ArrayList<V>();
    		root = node;
    	} else if (root instanceof BPTree.LeafNode) {
    		// case 2 : root is leaf node
    		node = (BPTree.LeafNode) root;
    	} else {
    		// case 3 : root is internal node
        	node = lookupLeafNode(key) ;
    	}

    	// insert key-value into leaf node
		node.insert(key, value);

    	// split the leaf node and insert the new key into parent internal node
    	if ( node.isOverflow() == true ) {
    		node.split();
    		// insert node's next created new leaf node into parent node
    		insertNewLeafNodeIntoParentNode(node, key);
    	}
    }
	
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
    	List<V> valueList = null;
    	
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        
        // return the value list for values 
        // applying comparator and key to the leaf nodes linked list
        valueList = processRangeSearch(key, comparator);
       
        return valueList;
    }
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#get(java.lang.Object)
     */
     @Override
     public V get(K key) {
    	 LeafNode node = null;

    	 // get the first leaf node
    	 node = getFirstLeafNode();

    	 // find the value for key input parameter 
    	 while (node != null) {
    		 for (int i=0; i<node.keys.size(); i++) {
    			 if (node.keys.get(i).compareTo(key) == 0) {
    				 return node.values.get(i);
    			 }
    		 }
    		 node = node.next;
    	 }
    	 
        return null;
     }

    /*
     * (non-Javadoc)
     * @see BPTreeADT#size()
     */
     @Override
     public int size() {
    	 LeafNode node = null;
    	 int numLeaves = 0;		// number of total leaf nodes
    	 
    	 // get the first leaf node
    	 node = getFirstLeafNode();

    	 // find the value for key input parameter 
    	 while (node != null) {
    		 numLeaves += node.keys.size();
    		 node = node.next;
    	 }

    	 return numLeaves;
     }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }

    
    /**
     * look up leaf node which key can be inserted into
     * if key is greater than all of leaf nodes' key values, return the last leaf node
     * @param key to insert
     * @return leaf node 
     */
    private LeafNode lookupLeafNode(K key) {
    	InternalNode internalNode = null;
    	LeafNode leafNode = null;
    	Node node = null;
    	
    	leafNode = getFirstLeafNode();
    	
    	if (leafNode == root) {
			return leafNode;
    	} else if (leafNode == null) {
    		return null;
    	}

    	while (leafNode.next != null) {
           	for (int i=0; i < leafNode.keys.size(); i++) {
           		// if "leaf node's key" >= "key for search"
        		if ( leafNode.keys.get(i).compareTo(key) >= 0 ) {
        			return leafNode;
        		}
        	}
           	leafNode = leafNode.next;
    	}
    	
    	return leafNode;
    }
    
    /**
     * add leaf node's key and pointer to parent internal node
     * @param node that add to parent's children variable
     * @param key to add to parent's key variable
     */
    private void insertNewLeafNodeIntoParentNode(LeafNode node, K key) {
    	InternalNode parent = null;
    	V value = null;
    	
    	// case 1 : if root is leaf node, need to create new parent internal node
    	if (root instanceof BPTree.LeafNode) {
    		parent = new InternalNode();
    		parent.keys = new ArrayList<K>();
    		parent.children = new ArrayList<Node>();
    		
    		parent.insert(node.keys.get(node.keys.size()-1), value);
    		parent.children.add(node);
    		parent.children.add(node.next);

    		root = parent;	// assign the created new internal node to root
    		return;
    	} 
    	
    	// case 2 : root is internal node
    	parent = findParentNode(root, key);
    	
    	// insert "node's last key after leaf node split" into parent internal node
    	parent.insert(node.keys.get(node.keys.size()-1), value);
    	// add node's next leaf node to parent's children
    	for (int i=0; i<parent.children.size(); i++) {
    		if (parent.children.get(i) == node) {
            	parent.children.add(i+1, node.next);
            	break;
    		}
    	}

    	// split the parent node and add created new split node to parent's parent internal node
		if ( parent.isOverflow() == true ) {
	    	processParentOverflow(parent);
		}

    }
    
    /**
     * find parent node for child node having key
     * @param  node (ancestor node for starting search)
     * @param  key (child node's key value)
     * @return parent internal node
     */
    private InternalNode findParentNode(Node node, K key) {
    	InternalNode internalNode = null;
    	InternalNode parentNode = null;
    	
    	if (node instanceof BPTree.LeafNode) {
    		return null;
    	}

		internalNode = (BPTree.InternalNode) node;
		if (internalNode.children.get(0) instanceof BPTree.LeafNode) {
    		return internalNode;
		}

       	// if this internalNode is not parent, search next higher level of B+ tree
    	int keySize = internalNode.keys.size();
       	for (int i=0; i < keySize; i++) {
       		// if "internal node's key" >= "key for search"
    		if ( internalNode.keys.get(i).compareTo(key) >= 0 ) {
    			parentNode = findParentNode(internalNode.children.get(i), key);
    			break;
    		// if "key for search" > "internal node's last key"
    		} else if ( (i==keySize-1) && (internalNode.keys.get(i).compareTo(key) < 0) ) {
    			parentNode = findParentNode(internalNode.children.get(keySize), key);
    			break;
    		}
    	}

    	return parentNode;
    }
    
    /**
     * split the parent and add parent's key to grandparent node
     * if grandparent node does not exist, create the grandparent node
     * @param parent to be split
     */
	private void processParentOverflow(InternalNode parent) {
		V value=null;
		InternalNode upperParent = null;
		
		// split this pareant's internal node
		InternalNode sibParent = (BPTree<K, V>.InternalNode) parent.split();

		// get parent's parent internal node
		if (parent == root) {
			upperParent = new InternalNode();
			upperParent.keys = new ArrayList<K>();
			upperParent.children = new ArrayList<Node>();
			
			K key1 = parent.keys.remove(parent.keys.size()-1);
			upperParent.insert(key1, value);

			upperParent.children.add(parent);
			upperParent.children.add(sibParent);

			root = upperParent;
			
			return;
		} 

		upperParent = findParentNode(root, parent.keys.get(parent.keys.size()-1));
		
		// remove existing parent's last key and add it to parent's parent internal node
		K key1 = parent.keys.remove(parent.keys.size()-1);
		upperParent.insert(key1, value);
		
    	// add parent and sibParent to upperParent's children if upperParent is root
		// add parent's created new sibling internal node to upperParent
    	for (int i=0; i<parent.children.size(); i++) {
    		if (upperParent.children.get(i) == parent) {
    			upperParent.children.add(i+1, sibParent);
            	break;
    		}
    	}
		
		// if upperParent is overflow, call processParentOverflow() method recursively
		if (upperParent.isOverflow() == true) {
			processParentOverflow(upperParent);
		}
		
	}

    /**
     * start search from root and get first leaf node which have lowest key value
     * @return first leaf node of linked list
     */
    private LeafNode getFirstLeafNode() {
    	LeafNode leafNode = null;
    	InternalNode internalNode = null;
    	
    	if (root == null) {
    		return null;
    	} else if (root instanceof BPTree.LeafNode) {
    		leafNode = (BPTree.LeafNode) root;
    	} else {
        	// get the first leafNode
    		internalNode = (BPTree.InternalNode) root;
        	while (true) {
        		if (internalNode.children.get(0) instanceof BPTree.LeafNode) {
        			leafNode = (BPTree.LeafNode) internalNode.children.get(0);
        			break;
        		}
        		internalNode = (BPTree.InternalNode) internalNode.children.get(0);
        	}
    	}
    	
    	return leafNode;	
    }

    /**
     * get the value list satisfied with search comparator
     * @param key : key to search
     * @param comparator string for search
     * @return value list 
     */
    private List<V> processRangeSearch(K key, String comparator) {
    	List<V> valueList = new ArrayList<V>();
    	LeafNode node = null;
    	
        // get the first leaf node
	 	node = getFirstLeafNode();
	 	
	 	switch (comparator) {
   	 	case ">=":
	       	 while (node != null) {
	    		 for (int i=0; i<node.keys.size(); i++) {
	    			 if (node.keys.get(i).compareTo(key) >= 0) {
	    				 valueList.add((V) node.values.get(i));
	    			 }
	    		 }
	    		 node = node.next;
	    	 }
   	 		break;
   	 	case "==":
	       	 while (node != null) {
	    		 for (int i=0; i<node.keys.size(); i++) {
	    			 if (node.keys.get(i).compareTo(key) == 0) {
	    				 valueList.add((V) node.values.get(i));
	    			 }
	    		 }
	    		 node = node.next;
	    	 }
   	 		break;
   	 	case "<=":
	       	 while (node != null) {
	    		 for (int i=0; i<node.keys.size(); i++) {
	    			 if (node.keys.get(i).compareTo(key) <= 0) {
	    				 valueList.add((V) node.values.get(i));
	    			 }
	    		 }
	    		 node = node.next;
	    	 }
   	 		break;
	 	}

    	return valueList;
    }

	 	
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            keys = null;
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = null;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	LeafNode node = null;
        	K k = null;
        	
            // get the first leaf node
    	 	node = getFirstLeafNode();
    	 	
    	 	if (node != null) {
        	 	k = node.keys.get(0);
    	 	}
    	 	
            return k;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	
        	// internal node's max number of keys == branchingFactor-1
        	if (this.keys.size() > branchingFactor-1 )
        		return true;
        	else
        		return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
        	int index=0;

    		for (index=0; index < this.keys.size(); index++) {
    	    	// includes '=' for key-value pairs with duplicate keys
    			if (this.keys.get(index).compareTo(key) >= 0) {
    	    		break;
    			}
    		}
    		this.keys.add(index, key);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	InternalNode sibNode = new InternalNode();	// new sibling leaf node after split
        	sibNode.keys = new ArrayList<K>();
        	sibNode.children = new ArrayList<Node>();
        	K tempKey = null;
        	Node tempNode = null;
        	int keyLength=0;

        	// d : end index of first node (m is 3 => d is 2, m is 4 => d = 2)
        	int d = (int) Math.ceil( (double) (branchingFactor)/ 2); 

        	// copy node's key values to new internal node
    		keyLength = this.keys.size();
        	for (int i=d; i<keyLength; i++) {
        		tempKey = this.keys.remove(d);
        		sibNode.keys.add(tempKey);
        	}
        	// copy node's children to new internal node
        	keyLength = this.children.size();
        	for (int i=d; i<keyLength; i++) {
        		tempNode = this.children.remove(d);
        		sibNode.children.add(tempNode);
        	}

            return sibNode;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> valueList = null;
        	
            if (!comparator.contentEquals(">=") && 
                !comparator.contentEquals("==") && 
                !comparator.contentEquals("<=") )
                return new ArrayList<V>();
            
            valueList = processRangeSearch(key, comparator);
           
            return valueList;
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            values = null;
            next = null;
            previous = null;
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	LeafNode node = null;
        	K k = null;
        	
            // get the first leaf node
    	 	node = getFirstLeafNode();
    	 	
    	 	if (node != null) {
        	 	k = node.keys.get(0);
    	 	}
    	 	
            return k;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	
        	// leaf node's max number of keys == branchingFactor
        	if (this.keys.size() > branchingFactor )
        		return true;
        	else
        		return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	int index=0;

    		for (index=0; index < this.keys.size(); index++) {
    	    	// includes '=' for key-value pairs with duplicate keys
    			if (this.keys.get(index).compareTo(key) >= 0) {
    	    		break;
    			}
    		}
        	
    		this.keys.add(index, key);
    		this.values.add(index, value);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {

        	LeafNode sibLeafNode = new LeafNode();
        	sibLeafNode.keys = new ArrayList<K>();
        	sibLeafNode.values = new ArrayList<V>();
        	int keyLength = 0;
        	K tempKey = null;
        	V tempValue = null;

        	// d : end index of first node (m is 3 => d is 2, m is 4 => d = 2)
        	int d = (int) Math.ceil(branchingFactor/2.0);

        	sibLeafNode.next = this.next;
        	this.next = sibLeafNode;
        	sibLeafNode.previous = this;
        	
        	// copy node's key values to new internal node
    		keyLength = this.keys.size();
        	for (int i=d; i<keyLength; i++) {
        		tempKey = this.keys.remove(d);
        		sibLeafNode.keys.add(tempKey);
        		tempValue = this.values.remove(d);
        		sibLeafNode.values.add(tempValue);
        	}

            return sibLeafNode;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> valueList = null;
        	
            if (!comparator.contentEquals(">=") && 
                !comparator.contentEquals("==") && 
                !comparator.contentEquals("<=") )
                return new ArrayList<V>();
            
            valueList = processRangeSearch(key, comparator);
           
            return valueList;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
