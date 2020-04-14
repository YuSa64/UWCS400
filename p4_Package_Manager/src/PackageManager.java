import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

/**
 * Filename: PackageManager.java Project: p4 Authors:
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test classes.
 */

public class PackageManager {

  private Graph graph;

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    graph = new Graph();
  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException if the give file cannot be read
   * @throws ParseException if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    if (jsonFilepath == null)
      throw new FileNotFoundException("Null file path");

    JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(jsonFilepath));
    JSONArray ja = (JSONArray) jo.get("packages");
    for (Object o : ja) {
      JSONObject jpackage = (JSONObject) o;
      String name = (String) jpackage.get("name");
      graph.addVertex(name);
      JSONArray dependencies = (JSONArray) jpackage.get("dependencies");
      for (Object d : dependencies)
        graph.addEdge(name, (String) d);
    }
  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding the installation
   *         order for a particular package. Tip: Cycles in some other part of the graph that do not
   *         affect the installation order for the specified package, should not throw this
   *         exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    if (!getAllPackages().contains(pkg))
      throw new PackageNotFoundException();
    return orderpkg(pkg);
  }


  private List<String> orderpkg(String pkg) throws CycleException {
    checkCycle(pkg);
    ArrayList<String> order = new ArrayList<String>();
    orderpkg(order, new ArrayList<String>(), pkg);
    return order;
  }

  private void orderpkg(ArrayList<String> order, ArrayList<String> checked, String pkg) {
    checked.add(pkg);
    List<String> adjacent = graph.getAdjacentVerticesOf(pkg);
    for (String s : adjacent)
      if (!checked.contains(s))
        orderpkg(order, checked, s);
    order.add(pkg);
  }

  private void checkCycle(String pkg) throws CycleException {
    Stack<String> pkgCheck = new Stack<String>();
    ArrayList<String> pkgChecked = new ArrayList<String>();
    pkgCheck.addAll(graph.getAdjacentVerticesOf(pkg));
    pkgChecked.add(pkg);
    while (!pkgCheck.isEmpty()) {
      String temp = pkgCheck.pop();
      if (pkgChecked.contains(temp))
        throw new CycleException();
      pkgCheck.addAll(graph.getAdjacentVerticesOf(temp));
      if (!graph.getAdjacentVerticesOf(temp).isEmpty())
        pkgChecked.add(temp);
    }
  }

  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding the dependencies of
   *         the given packages. If there is a cycle in some other part of the graph that doesn't
   *         affect the parsing of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *         graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    if (!getAllPackages().contains(installedPkg))
      throw new PackageNotFoundException();

    List<String> newPkgList = orderpkg(newPkg);
    List<String> installedPkgList = orderpkg(installedPkg);
    List<String> output = new ArrayList<String>();
    for (String s : newPkgList)
      if (!installedPkgList.contains(s))
        output.add(s);
    return output;
  }

  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    Object[] temp = getAllPackages().toArray();
    List<String> output = new ArrayList<String>();
    for (Object o : temp) {
      List<String> ordered = orderpkg((String) o);
      for (String s : ordered)
        if (!output.contains(s))
          output.add(s);
    }
    return output;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    String output = "";
    int numdependencies = 0;
    Object[] pkgs = getAllPackages().toArray();
    for (Object o : pkgs) {
      List<String> ordered = orderpkg((String) o);
      if (numdependencies < ordered.size()) {
        output = (String) o;
        numdependencies = ordered.size();
      }
    }
    return output;
  }
}
