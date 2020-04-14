import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * Filename: Graph.java Project: p4 Authors: Hyeon Jun Jeong
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {

  private HashMap<String, ArrayList<String>> map;
  private int edges;

  /*
   * Default no-argument constructor
   */
  public Graph() {
    map = new HashMap<>();
    edges = 0;
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or throwing an
   * exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be added
   */
  @Override
  public void addVertex(String vertex) {
    if (vertex == null || map.containsKey(vertex))
      return;
    map.put(vertex, new ArrayList<String>());
  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex, edges, or throwing
   * an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be removed
   */
  @Override
  public void removeVertex(String vertex) {
    if (vertex == null || !map.containsKey(vertex))
      return;
    map.remove(vertex);
  }

  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted)
   * 
   * If either vertex does not exist, VERTEX IS ADDED and then edge is created. No exception is
   * thrown.
   *
   * If the edge exists in the graph, no edge is added and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge is not in the graph
   * 
   * @param vertex1 the first vertex (src)
   * @param vertex2 the second vertex (dst)
   */
  @Override
  public void addEdge(String vertex1, String vertex2) {
    if (vertex1 == null || vertex2 == null)
      return;
    addVertex(vertex1);
    addVertex(vertex2);
    if (!map.get(vertex1).contains(vertex2)) {
      map.get(vertex1).add(vertex2);
      edges++;
    }
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge from vertex1 to vertex2 is in the graph
   * 
   * @param vertex1 the first vertex
   * @param vertex2 the second vertex
   */
  @Override
  public void removeEdge(String vertex1, String vertex2) {
    if (vertex1 == null || vertex2 == null || !map.containsKey(vertex1)
        || !map.containsKey(vertex2))
      return;
    if (map.get(vertex1).contains(vertex2)) {
      map.get(vertex1).remove(vertex2);
      edges--;
    }
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   * @return a Set<String> which contains all the vertices in the graph
   */
  @Override
  public Set<String> getAllVertices() {
    return map.keySet();
  }

  /**
   * Get all the neighbor (adjacent-dependencies) of a vertex
   * 
   * For the example graph, A->[B, C], D->[A, B] getAdjacentVerticesOf(A) should return [B, C].
   * 
   * In terms of packages, this list contains the immediate dependencies of A and depending on your
   * graph structure, this could be either the predecessors or successors of A.
   * 
   * @param vertex the specified vertex
   * @return an List<String> of all the adjacent vertices for specified vertex
   */
  @Override
  public List<String> getAdjacentVerticesOf(String vertex) {
    return map.get(vertex);
  }

  /**
   * Returns the number of edges in this graph.
   * 
   * @return number of edges in the graph.
   */
  @Override
  public int size() {
    return edges;
  }

  /**
   * Returns the number of vertices in this graph.
   * 
   * @return number of vertices in graph.
   */
  @Override
  public int order() {
    return map.size();
  }



}
