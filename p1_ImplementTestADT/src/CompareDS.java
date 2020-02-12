
public class CompareDS {

  public static void main(String[] args) {

    DS_My my = new DS_My();
    DS_Brian brian = new DS_Brian();
    
    
    System.out.println("CompareDS.main Compares work time for: DS_My and DS_Brian");
    System.out.println(
        "Description: In this comparison, each DS would insert 100, 1000, 10000, 100000 values and comapre their processing time.\n");

    System.out.println("DS_My is doing work for 100 values");
    System.out.println("It took " + DSwork(my, 100) +" ns to process 100 items");
    System.out.println("DS_Brian is doing work for 100 values");
    System.out.println("It took " + DSwork(brian, 100) +" ns to process 100 items\n");
    my = new DS_My();
    brian = new DS_Brian();
    
    System.out.println("DS_My is doing work for 1000 values");
    System.out.println("It took " + DSwork(my, 1000) +" ns to process 1000 items");
    System.out.println("DS_Brian is doing work for 1000 values");
    System.out.println("It took " + DSwork(brian, 1000) +" ns to process 1000 items\n");
    my = new DS_My();
    brian = new DS_Brian();
    
    System.out.println("DS_My is doing work for 10000 values");
    System.out.println("It took " + DSwork(my, 10000) +" ns to process 10000 items");
    System.out.println("DS_Brian is doing work for 10000 values");
    System.out.println("It took " + DSwork(brian, 10000) +" ns to process 10000 items\n");
    my = new DS_My();
    brian = new DS_Brian();
    
    System.out.println("DS_My is doing work for 100000 values");
    System.out.println("It took " + DSwork(my, 100000) +" ns to process 100000 items");
    System.out.println("DS_Brian is doing work for 100000 values");
    System.out.println("It took " + DSwork(brian, 100000) +" ns to process 100000 items\n");
    
  }

  private static long DSwork(DataStructureADT<String, String> DS, int numWorks) {
    long start = System.nanoTime();
    for (int i = 0; i < numWorks; i++) {
      DS.insert("" + i, "" + i);
    }
    long end = System.nanoTime();
    return end - start;
  }

}
