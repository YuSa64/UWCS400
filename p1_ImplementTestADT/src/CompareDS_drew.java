

/**
 * CompareDS - This class serves as an area to view the amount of time two data structures take to
 * perform insert and get methods.
 * 
 * @author halverson (2020)
 *
 */
public class CompareDS_drew {

  public static void main(String[] args) {
    System.out.println("Compares work time for DS_My and DS_Brian.");
    System.out.println("Each trial will insert and then get \"N\" amount of items, where N will "
            + "change based on " + "the test size.\n");
    testInsert(100);
    testInsert(1000);
    testInsert(10000);
    testInsert(100000);
    testInsert(1000000); // 1 million
    testInsert(1000000000); // 1 billion
  }

  /**
   * testInsert - inserts something into DS_My \"times\" amount of times and does the same in
   * DS_Brian. Also outputs the amount of time each operation took to the console for visual
   * comparison.
   * 
   * @param times The integer amount of times that the test will be ran on each DS.
   */
  private static void testInsert(int times) {
    DS_My_drew dsMy = new DS_My_drew();
    DS_Brian dsBrian = new DS_Brian();

    // DS_My
    System.out.println("DS_My is doing work for " + times + " values.");
    // inserting and recording start and end times
    long startTime = System.nanoTime();
    for (int i = 0; i < times; i++) {
      dsMy.insert(Integer.toString(i), "value");
    }
    long endTime = System.nanoTime();
    System.out.println("It took " + (endTime - startTime) + "ns to insert " + times + " items.");
    testGet(dsMy, times);

    // DS_Brian
    System.out.println("DS_Brian is doing work for " + times + " values.");
    // inserting and recording start and end times
    startTime = System.nanoTime();
    for (int i = 0; i < times; i++) {
      dsBrian.insert(Integer.toString(i), "value");
    }
    endTime = System.nanoTime();
    System.out.println("It took " + (endTime - startTime) + "ns to insert " + times + " items.");
    testGet(dsBrian, times);

    System.out.println();
  }

  /**
   * testGet - Runs the get() method on ds times amount of times.
   * 
   * @param ds    The data structure that will be running.
   * @param times the amount of times that get will run.
   */
  private static void testGet(DataStructureADT<String, String> ds, int times) {
    long startTime = System.nanoTime();
    for (int i = 0; i < times; i++) {
      ds.get(Integer.toString(i));
    }
    long endTime = System.nanoTime();
    System.out.println("It took " + (endTime - startTime) + "ns to get " + times + " values.");
  }

}
