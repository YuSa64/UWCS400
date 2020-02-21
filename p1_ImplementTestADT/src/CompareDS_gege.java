/**
 * Author: Gege Zhang 
 * Email: gzhang85@wisc.edu 
 * Lecture Number: 002 Program
 * Description: This program is to practice for implement, test, and compare alternate implementations of an abstract data type 
 */

/**
 * 
 * CompareDS - This class is to compares the performance of your implementation
 * DS_My against any of one of the TA implementations that works.
 * 
 * @author Gege Zhang(2020)
 *
 */
public class CompareDS_gege {

	public static void main(String[] args) {
		// Description message for what work is comparing here
		System.out.println("CompareDS.main Compares work time for: DS_My and DS_Srivatsan");
		System.out.println("Description: <Insert many pairs, get them >");

		// start time for run code
		long startTime = System.nanoTime();
		// create a DS_My object to use my code for call methods
		DS_My_gege obj1 = new DS_My_gege();

		// use a loop for repeat insert and get for a very large number times for
		// using my code
		for (int i = 0; i < 100001; i++) {
			// insert different pairs
			obj1.insert(Integer.toString(i), "one");
			// call get method to get previous insert item
			obj1.get(Integer.toString(i));

			// print the running time for process N times
			if (i == 100 || i == 1000 || i == 10000 || i == 100000) {
				// print description of the work done for each trial, run multiple trials for
				// each data structure and data set size
				System.out.println("DS_My is doing work for " + (i) + " values");
				System.out.println("It took " + (System.nanoTime() - startTime) + " ns to process " + (i) + " items");
			}
		}

		System.out.println("");
		startTime = System.nanoTime();
		// create a Ds_Srivantsan object to use his code
		DS_Srivatsan obj2 = new DS_Srivatsan();

		// use a loop for repeat insert and get for a very large number times for
		// using Ta code
		for (int i = 0; i < 100001; i++) {
			// insert different pairs
			obj2.insert(Integer.toString(i), "one");
			// call get method to get perivious item
			obj2.get(Integer.toString(i));
			// print the running time for process N times
			if (i == 100 || i == 1000 || i == 10000 || i == 100000) {
				// print description of the work done for each trial, run multiple trials for
				// each data structure and data set size
				System.out.println("DS_Srivatsan is doing work for " + (i) + " values");
				System.out.println("It took " + (System.nanoTime() - startTime) + " ns to process " + (i) + " items");
			}
		}

	}

}
