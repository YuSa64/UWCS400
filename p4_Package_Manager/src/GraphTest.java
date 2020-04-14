import static org.junit.jupiter.api.Assertions.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GraphTest {

  Graph graph;


  @Before
  public void setUp() throws Exception {
    graph = new Graph();
  }

  @After
  public void tearDown() throws Exception {}


  @Test
  public void test000_addVertex() {
    graph.addVertex("test");
    if (!graph.getAllVertices().contains("test"))
      fail("Fail Add Vertex");
  }

  @Test
  public void test001_addVertex_nullvertex() {
    graph.addVertex(null);
    if (!graph.getAllVertices().isEmpty())
      fail("Null Vertex Added");
  }

  @Test
  public void test002_addVertex_containsvertex() {
    graph.addVertex("test");
    if (graph.getAllVertices().size() != 1)
      fail("Existing Vertex Added");
  }

  @Test
  public void test003_removeVertex() {
    graph.addVertex("test");
    graph.removeVertex("test");
    if (graph.order() != 0 || graph.getAllVertices().contains("test"))
      fail("Vertex is Not Removed");
  }

  @Test
  public void test004_removeVertex_nullvertex() {
    graph.addVertex("test");
    graph.removeVertex(null);
    if (graph.order() != 1)
      fail("Null Vertex is Removed");
  }

  @Test
  public void test005_removeVertex_notcontainsvertex() {
    graph.addVertex("test");
    graph.removeVertex("TEST");
    if (graph.order() != 1)
      fail("Non Existing Vertex is Removed");
  }

  @Test
  public void test006_addEdge() {
    graph.addVertex("test");
    graph.addVertex("TEST");
    graph.addEdge("test", "TEST");
    if (graph.size() != 1 || !graph.getAdjacentVerticesOf("test").contains("TEST"))
      fail("Edge is not Created");
  }

  @Test
  public void test007_addEdge_nonexistingvertex() {
    graph.addVertex("test");
    graph.addEdge("test", "TEST");
    if (graph.order() != 2 || !graph.getAllVertices().contains("TEST"))
      fail("Non Existing Vertex is Not Created");
    if (graph.size() != 1 || !graph.getAdjacentVerticesOf("test").contains("TEST"))
      fail("Edge is not Created");
  }

  @Test
  public void test008_addEdge_nonexistingvertexes() {
    graph.addEdge("test", "TEST");
    if (graph.order() != 2 || !graph.getAllVertices().contains("test")
        || !graph.getAllVertices().contains("TEST"))
      fail("Non Existing Vertex is Not Created");
    if (graph.size() != 1 || !graph.getAdjacentVerticesOf("test").contains("TEST"))
      fail("Edge is not Created");
  }

  @Test
  public void test09_addEdge_nullvertex() {
    graph.addEdge(null, "test");
    if (graph.order() != 0 || graph.size() != 0)
      fail("Null Vertex or Edge is Created");
  }

  @Test
  public void test010_addEdge_notcontainsdstvertex() {
    graph.addVertex("test");
    graph.addVertex("TEST");
    graph.addEdge("tester", "TEST");
    if (graph.size() != 1)
      fail("Edge for Non Existing Vertex is Created");
  }

  @Test
  public void test011_addEdge_notcontainstgtvertex() {
    graph.addVertex("test");
    graph.addVertex("TEST");
    graph.addEdge("test", "TEST");
    graph.addEdge("test", "TEST");
    if (graph.size() != 1)
      fail("Already Existing Edge is Created");
  }

  @Test
  public void test012_removeEdge() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addEdge("1", "3");
    graph.addEdge("4", "5");
    graph.removeEdge("2", "1");
    if (graph.size() != 3)
      fail("Edge is not Removed Correctly");
  }

  @Test
  public void test013_removeEdge_nullvertex() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addEdge("1", "3");
    graph.addEdge("4", "5");
    graph.removeEdge(null, "2");
    graph.removeEdge("2", null);
    if (graph.size() != 4)
      fail("Edge is not Removed Correctly");
  }

  @Test
  public void test014_removeEdge_notcontainsvertex() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addEdge("1", "3");
    graph.addEdge("4", "5");
    graph.removeEdge("3", "6");
    graph.removeEdge("6", "1");
    if (graph.size() != 4)
      fail("Edge is not Removed Correctly");
  }

  @Test
  public void test015_removeEdge_noedge() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addEdge("1", "3");
    graph.addEdge("4", "5");
    graph.removeEdge("3", "1");
    graph.removeEdge("5", "4");
    if (graph.size() != 4)
      fail("Edge is not Removed Correctly");
  }

  @Test
  public void test016_getAllVertices() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addVertex("6");
    graph.addEdge("1", "3");
    graph.addEdge("4", "5");
    graph.addVertex("1");
    String[] array = {"1", "2", "3", "4", "5", "6"};
    String[] vertex = graph.getAllVertices().toArray(new String[0]);
    for (int i = 0; i < array.length; i++)
      if (!array[i].equals(vertex[i]))
        fail("getAllVertices Does Not Work Intended");
  }

  @Test
  public void test017_getAdjacentVerticesOf() {
    graph.addEdge("1", "2");
    graph.addEdge("1", "5");
    graph.addVertex("6");
    graph.addEdge("1", "3");
    graph.addEdge("1", "4");
    String[] array = {"2", "5", "3", "4"};
    String[] adj = graph.getAdjacentVerticesOf("1").toArray(new String[0]);
    for (int i = 0; i < array.length; i++)
      if (!array[i].equals(adj[i]))
        fail("getAllVertices Does Not Work Intended");
  }

  @Test
  public void test018_size() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addEdge("1", "3");
    graph.addEdge("3", "2");
    graph.addEdge("4", "1");
    graph.addEdge("4", "3");
    graph.removeEdge("1", "2");
    graph.removeEdge("3", "1");
    if (graph.size() != 5)
      fail("Number of Edge is Not Counted Correctly");
  }

  @Test
  public void test019_order() {
    graph.addEdge("1", "2");
    graph.addEdge("2", "1");
    graph.addEdge("1", "3");
    graph.addEdge("3", "2");
    graph.addEdge("4", "1");
    graph.addEdge("4", "3");
    graph.addVertex("5");
    graph.addVertex("6");
    graph.removeVertex("7");
    if (graph.order() != 6)
      fail("Number of Vertex is Not Counted Correctly");
  }

}


