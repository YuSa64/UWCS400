import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4 (PACKAGE MANAGER)
 * Authors:    YONGHEE, HAN
 * Email:      applehan8960@gmail.com
 * due date:   Nov 14 by 11:59pm
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {
	/**
	 * adjacentMap:  data structure for each vertex and related adjacent vertices in the graph
	 * String:       vertex name (package name)
	 * List<String>: given vertex name's adjacent vertices (using ArrayList class)
	 */
	private HashMap<String, List<String>> adjacentMap;
	// maintains total number of edges in graph
	private int numEdges;

	public HashMap<String, List<String>> getAdjacentMap() {
		return adjacentMap;
	}
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		this.adjacentMap = new HashMap<>();
		this.numEdges = 0;
	}

	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vertex) {
		List<String> newPkg = new ArrayList<String>();
		
		if ( (vertex == null) || (this.adjacentMap.containsKey(vertex) == true) ) {
			return;
		}
		
		this.adjacentMap.put(vertex, newPkg);
	}

	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removeVertex(String vertex) {
		if ( (vertex == null) || (this.adjacentMap.containsKey(vertex) == false) ) {
			return;
		}
		this.adjacentMap.remove(vertex);
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		List<String> adjacentVertices = new ArrayList<>();
		
		// if vertex1 or vertex2 does not exist, exit addEdge method
		if ( (vertex1 == null) || (vertex2 == null) ) {
			return;
		}
		// if vertex1 does not exist in adjacentMap, add vertex1 to adjacentMap
		if (this.adjacentMap.containsKey(vertex1) == false) {
			this.addVertex(vertex1);
		}
		// if vertex2 does not exist in adjacentMap, add vertex2 to adjacentMap
		if (this.adjacentMap.containsKey(vertex2) == false) {
			this.addVertex(vertex2);
		}
		
		// If the edge does not exists in the graph, add edge in vertex1's adjacentVertices list
		if (this.adjacentMap.get(vertex1) != null) {
			adjacentVertices = this.adjacentMap.get(vertex1);
		} else {
			this.adjacentMap.put(vertex1, adjacentVertices);
		}
		
		if ( adjacentVertices.contains(vertex2) == false ) {
			adjacentVertices.add(vertex2);
			this.adjacentMap.put(vertex1, adjacentVertices);
			this.numEdges++;	// increment 1 to the numEdges
		}
		
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vertex1, String vertex2) {
		List<String> adjacentVertices;
		
		// if vertex1 or vertex2 is null, exit removeEdge method
		if ( (vertex1 == null) || (vertex2 == null) ) {
			return;
		}
		// if vertex1 or vertex2 does not exist in adjacentMap, exit removeEdge method
		if ( (this.adjacentMap.containsKey(vertex1) == false) || 
				(this.adjacentMap.containsKey(vertex2) == false) ) {
			return;
		}
		
		// If the edge exists in the graph, remove edge in vertex1's adjacentVertices list
		adjacentVertices = this.adjacentMap.get(vertex1);
		if (adjacentVertices.contains(vertex2) == true) {
			adjacentVertices.remove(vertex2);
			this.numEdges--;
		}

	}	

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() {
		return adjacentMap.keySet();
	}

	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		return adjacentMap.get(vertex);
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return this.numEdges;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() {
        return adjacentMap.size();
    }
}
