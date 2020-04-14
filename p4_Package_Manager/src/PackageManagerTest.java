import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PackageManagerTest {

  PackageManager manager;
  String cyclic;
  String shared;
  String valid;


  @Before
  public void setUp() throws Exception {
    manager = new PackageManager();
    cyclic = "src/cyclic.json";
    shared = "src/shared_dependencies.json";
    valid = "src/valid.json";
  }

  @After
  public void tearDown() throws Exception {}


  @Test
  public void test000_constructGraph() {
    try {
      manager.constructGraph(valid);
    } catch (Exception e) {
      fail("Unexpected Exception Occured: " + e.getMessage());
    }
  }

  @Test
  public void test001_constructGraph_nullpath() {
    try {
      manager.constructGraph(null);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test002_constructGraph_notreadable() {
    try {
      manager.constructGraph("test");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test003_getAllPackages() {
    try {
      manager.constructGraph(valid);
      String[] pkgs = manager.getAllPackages().toArray(new String[0]);
      String[] cpkgs = {"A", "B", "C", "D", "E"};
      for (int i = 0; i < pkgs.length; i++)
        if (!pkgs[i].equals(cpkgs[i]))
          fail("At least one package is not correct");
    } catch (Exception e) {
      fail("Unexpected Exception Occured: " + e.getMessage());
    }
  }

  @Test
  public void test004_getInstallationOrder() {
    try {
      manager.constructGraph(shared);
      String[] pkgs = manager.getInstallationOrder("A").toArray(new String[0]);
      String[] cpkgs = {"D", "B", "C", "A"};
      for (int i = 0; i < pkgs.length; i++)
        if (!pkgs[i].equals(cpkgs[i]))
          fail("At least one package is not correct");
    } catch (Exception e) {
      fail("Unexpected Exception Occured: " + e.getMessage());
    }
  }

  @Test
  public void test005_getInstallationOrder_nopkg() {
    try {
      manager.constructGraph(valid);
      manager.getInstallationOrder("F");
    } catch (PackageNotFoundException e) {
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test006_getInstallationOrder_cycle() {
    try {
      manager.constructGraph(cyclic);
      manager.getInstallationOrder("A");
    } catch (CycleException e) {
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test007_toInstall() {
    try {
      manager.constructGraph(shared);
      String[] pkgs = manager.toInstall("A", "B").toArray(new String[0]);
      String[] cpkgs = {"C", "A"};
      for (int i = 0; i < pkgs.length; i++)
        if (!pkgs[i].equals(cpkgs[i]))
          fail("At least one package is not correct");
    } catch (Exception e) {
      fail("Unexpected Exception Occured: " + e.getMessage());
    }
  }

  @Test
  public void test008_toInstall_nopkg() {
    try {
      manager.constructGraph(shared);
      manager.toInstall("A", "F");
    } catch (PackageNotFoundException e) {
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test009_toInstall_cycle() {
    try {
      manager.constructGraph(cyclic);
      manager.toInstall("A", "B");
    } catch (CycleException e) {
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test010_getInstallationOrderForAllPackages() {
    try {
      manager.constructGraph(shared);
      String[] pkgs = manager.getInstallationOrderForAllPackages().toArray(new String[0]);
      String[] cpkgs = {"D", "B", "C", "A"};
      for (int i = 0; i < pkgs.length; i++)
        if (!pkgs[i].equals(cpkgs[i]))
          fail("At least one package is not correct");
    } catch (Exception e) {
      fail("Unexpected Exception Occured: " + e.getMessage());
    }
  }

  @Test
  public void test011_getInstallationOrderForAllPackages_cycle() {
    try {
      manager.constructGraph(cyclic);
      manager.getInstallationOrderForAllPackages();
    } catch (CycleException e) {
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }

  @Test
  public void test012_getPackageWithMaxDependencies() {
    try {
      manager.constructGraph(valid);
      String pkg = manager.getPackageWithMaxDependencies();
      String cpkg = "A";
      if (!pkg.equals(cpkg))
        fail("getPackageWithMaxDependencies Does Not Work as Intended");
    } catch (Exception e) {
      fail("Unexpected Exception Occured: " + e.getMessage());
    }
  }

  @Test
  public void test013_getPackageWithMaxDependencies_cycle() {
    try {
      manager.constructGraph(cyclic);
      manager.getPackageWithMaxDependencies();
    } catch (CycleException e) {
    } catch (Exception e1) {
      fail("Unexpected Exception Occured: " + e1.getMessage());
    }
  }
}
