import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * TODO Stores (key,value) pair for each (State,List<DailyStateDataEntry>).
 * 
 * This class also provides additional data functionality.
 * @author deppeler ALL RIGHTS RESERVED, FOR STUDENT USE DURING EXAM ONLY.
 */
public class DS {

	/** TODO add private fields for your data structure here */    
	    
	  List<DailyStateDataEntry>[] buckets;
	  private int capacity;
	  private double loadfactorthreshold;
	  private int size;
	
	/** PRO-TIP: use Hashtable or TreeMap  */

	/** PRO-TIP: store as (State,List<DailyStateDataEntry>) pairs. */
			
	/** TODO initial internal data structure field(s) here */
	public DS() {
	  capacity = 59;
	  loadfactorthreshold = 0.75;
	  size = 0;
	  buckets = (List<DailyStateDataEntry>[]) new List[capacity];
	}

	/** TODO Add a daily entry to the list for a given state */
	public void add(DailyStateDataEntry dataEntry) {
		
		/** 
		 * PRO-TIP get state name from record
		 * If not found, add to internal ds
		 * Then, add this data entry to the correct list for the state.
		 */
	  
		String stateName = dataEntry.getStateName();
		int index = Math.abs(stateName.hashCode() % capacity);
		
		if(buckets[index] == null) {
		  buckets[index] = new ArrayList<DailyStateDataEntry>();
		  buckets[index].add(dataEntry);
		}
		else {
		  buckets[index].add(dataEntry);
		}
		size ++;

		if ((double)size / capacity > loadfactorthreshold) {
		  List<DailyStateDataEntry>[] oldbuckets = buckets;
		  capacity = capacity * 2 + 1;
		  size = 0;
		  buckets = (List<DailyStateDataEntry>[]) new List[capacity];
		  for(List<DailyStateDataEntry> l: oldbuckets) {
		    if(l != null)
  		      for(DailyStateDataEntry e: l) 
  		        if(e != null)
  		          add(e);
		  }
		}
	}

	/** TODO Return a summary of all the records for specified State */
	public StateSummary getStateSummaryFor(String stateName) {
		
		/** 
		 * PRO TIP: use your internal data structure 
		 * to get list of records for the state and 
		 * then instantiate and return a StateSummary instance
		 */
	    int index = Math.abs(stateName.hashCode() % capacity);
		return new StateSummary(stateName, buckets[index]);
		
	}

	/** TODO Return an array of all state names in sorted order. */
	public String[] getStateNamesInSortedOrder() {
		
		// TODO complete this method so that it returns an array
		// of all state and territory (no dups) in the data structure
		
		// There must not be any null elements as the user
		// must be able to use the length to know how many state/territories
		
		// partial credit if not in sorted order by state name here
		
	    String[] tempArray = new String[buckets.length];
	    int index = 0;
	    for(List<DailyStateDataEntry> l: buckets) {
	      if(l != null)
	        tempArray[index++] = l.get(0).getStateName();
	    }
	    
	    String[] stateNames = new String[index];
	    for(int i = 0; i < index; i++)
	      stateNames[i] = tempArray[i];

	    Arrays.sort(stateNames);
	    
		return stateNames;
		
	}
	
	/** 
	 * Mini-test code of StateSummary class. 
	 * STUDENTS MAY EDIT for their own use.
	 */
	public static void main(String [] args) {
		DS ds = Main.parseData("test.csv");
		String [] names = ds.getStateNamesInSortedOrder();
		for (String name : names) {
			System.out.print(name+",");
		}
		System.out.println();
		System.out.println(ds.getStateSummaryFor("Dane"));
		System.out.println(ds.getStateSummaryFor("Green"));
		System.out.println(ds.getStateSummaryFor("Milwaukee"));
	}

}
