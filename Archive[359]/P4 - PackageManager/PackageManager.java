import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename:   PackageManager.java
 * Project:    p4 (PACKAGE MANAGER)
 * Authors:    YONGHEE, HAN
 * Email:      yhan259@wisc.edu
 * due date:   Nov 14 by 11:59pm
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    private Graph graph;
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
    	// initialize an empty "internal" graph object
        this.graph = new Graph();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
    	if (jsonFilepath == null) {
        	throw new FileNotFoundException("json file is null");
        }
    	// parsing json file for jsonFilepath variable and typecasting to JSONObject
    	JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(jsonFilepath));
        JSONArray ja = (JSONArray) jo.get("packages");
        
        for (int i=0; i< ja.size(); i++) {
           JSONObject joPackage = (JSONObject) ja.get(i);
           String name = (String) joPackage.get("name");
           graph.addVertex(name);
           JSONArray dependencies = (JSONArray)joPackage.get("dependencies");
           for(int k=0; k<dependencies.size(); k++) {
              graph.addEdge(name, (String) dependencies.get(k));
           }
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
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
    	if ( graph.getAllVertices().contains(pkg) == false ) {
    		throw new PackageNotFoundException();
    	} 
         List<String> installPkgs = ordering(pkg); 
         return installPkgs;
    }
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
    	if (graph.getAllVertices().contains(installedPkg)== false)
    		throw new PackageNotFoundException();
    	
    	List<String> newpkgs = ordering(newPkg);
    	List<String> installedPkgs = ordering(installedPkg);
    	List<String> toInstallPkgs = new ArrayList<String>();         
    	for (int i=0; i<newpkgs.size(); i++) {
    		if (installedPkgs.contains(newpkgs.get(i))==false) {
    			toInstallPkgs.add(newpkgs.get(i));
    		}
         }
         return toInstallPkgs;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
    	Object[] tempPkgs = graph.getAllVertices().toArray();
    	List<String> allInstallPkgs = new ArrayList<String>();
        for (int i=0; i<tempPkgs.length; i++) {
           List<String> order = ordering((String) tempPkgs[i]); 
           for (int k=0; k<order.size(); k++) {
              if (allInstallPkgs.contains(order.get(k))==false)
            	  allInstallPkgs.add(order.get(k));
                }
        }
         return allInstallPkgs;

    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {
        String maxDependentPkg = "";
    	int maxDependencies = 0 ;
        Object[] installPkgs = graph.getAllVertices().toArray();
        for (int i=0; i<installPkgs.length; i++) {
            List<String> order = ordering((String) installPkgs[i]); 
            if (maxDependencies < order.size()) {
            	maxDependencies = order.size();
               maxDependentPkg = (String) installPkgs[i];
            }
        }
        return maxDependentPkg;
    }

    private void recursiveOrder(Stack<String> stack, ArrayList<String> visited, String start) {
        visited.add(start);
        List<String> list = graph.getAdjacentVerticesOf(start); 
         for (String i : list) {
            if(visited.contains(i) == false)
            	recursiveOrder(stack, visited,i);
         } 
         stack.push(start);
     }
     
     private List<String> ordering(String start) throws CycleException {
    	 //check the CylcleException
    	 Stack<String> stackCheck = new Stack<String>();
    	 ArrayList<String> visitedCkeck = new ArrayList<String>();
    	 stackCheck.addAll(graph.getAdjacentVerticesOf(start));
    	 visitedCkeck.add(start);
        while(stackCheck.isEmpty()== false) {
           String tempPkg = stackCheck.pop();
           if(visitedCkeck.contains(tempPkg))
              throw new CycleException();
           stackCheck.addAll(graph.getAdjacentVerticesOf(tempPkg));
           if(graph.getAdjacentVerticesOf(tempPkg).isEmpty()== false)
        	   visitedCkeck.add(tempPkg);
        }
        //make new Stack and  new ArrayList
    	 Stack<String> stack = new Stack<String>();
    	 recursiveOrder(stack, new ArrayList<String>(), start);
    	 return stack;
     }

//    public static void main (String [] args) {
//    	PackageManager pm;
//    	Set<String> pkgs = null;
//    	List<String> installPkgs = null;
//
//    	System.out.println("PackageManager.main()");
//        
//        if (args[0] != null) {
////        	System.out.println("please input json filename when run the PackageManager");
//        	System.exit(-1);
//        }
//        
//    	pm = new PackageManager();
//    	try {
//			pm.constructGraph(args[0]);
//		} catch (IOException | ParseException e) {
////			System.out.println("json file read error occurrs.");
//		}
//
//    	pkgs = pm.getAllPackages();
//    	for (String pkg : pkgs) {
////    		System.out.println("package = " + pkg);
//    	}
//
//    	try {
//			installPkgs = pm.getInstallationOrder("A");
//		} catch (PackageNotFoundException e) {
////			System.out.println("Package Exception occurs.");
//		} catch (CycleException e) {
////    		System.out.println("Cycle Exception occurs.");
//		}
//    	
//    	try {
//			installPkgs = pm.getInstallationOrderForAllPackages();
//		} catch (CycleException e) {
////    		System.out.println("Cycle Exception occurs.");
//		}
//
//    	try {
//			installPkgs = pm.toInstall("A", "C");
//		} catch (PackageNotFoundException e) {
////			System.out.println("Package Exception occurs.");
//		} catch (CycleException e) {
////    		System.out.println("Cycle Exception occurs.");
//		}
//    	
//    	try {
//			String maxPackage = pm.getPackageWithMaxDependencies();
//		} catch (CycleException e) {
////    		System.out.println("Cycle Exception occurs.");
//		}
//    }
}
