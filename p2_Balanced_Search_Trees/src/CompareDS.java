
public class CompareDS {

  public static void main(String[] args) {

    BST<Integer, String> bst = new BST<Integer, String>();
    RBT<Integer, String> rbt = new RBT<Integer, String>();

    System.out.println("CompareDS.main Compares work time for: BST and RBT");
    System.out.println(
        "Description: In this comparison, each DS would insert 100, 1000, 10000, 100000 values, get the"
            + " last values inserted and comapre their processing time.\n");

    System.out.println("BST is doing work for 100 values");
    System.out.println("It took " + dsInput(bst, 100) + " ns to process 100 items");
    System.out.println("It took " + dsGet(bst) + " ns to process get the last item");
    System.out.println("RBT is doing work for 100 values");
    System.out.println("It took " + dsInput(rbt, 100) + " ns to process 100 items");
    System.out.println("It took " + dsGet(rbt) + " ns to process get the last item\n");

    bst = new BST<Integer, String>();
    rbt = new RBT<Integer, String>();
    System.out.println("BST is doing work for 1000 values");
    System.out.println("It took " + dsInput(bst, 1000) + " ns to process 1000 items");
    System.out.println("It took " + dsGet(bst) + " ns to process get the last item");
    System.out.println("RBT is doing work for 1000 values");
    System.out.println("It took " + dsInput(rbt, 1000) + " ns to process 1000 items");
    System.out.println("It took " + dsGet(rbt) + " ns to process get the last item\n");

    bst = new BST<Integer, String>();
    rbt = new RBT<Integer, String>();
    System.out.println("BST is doing work for 10000 values");
    System.out.println("It took " + dsInput(bst, 10000) + " ns to process 10000 items");
    System.out.println("It took " + dsGet(bst) + " ns to process get the last item");
    System.out.println("RBT is doing work for 10000 values");
    System.out.println("It took " + dsInput(rbt, 10000) + " ns to process 10000 items");
    System.out.println("It took " + dsGet(rbt) + " ns to process get the last item\n");

  }

  private static long dsInput(STADT<Integer, String> ds, int numWorks) {
    long start = System.nanoTime();
    try {
      for (int i = 1; i <= numWorks; i++) {
        ds.insert(i, "" + i);
      }
    } catch (Exception e) {
      System.out.print("Unexpected exception occured! dsInput failed!: " + e.getMessage());
    }
    long end = System.nanoTime();
    return end - start;
  }

  private static long dsGet(STADT<Integer, String> ds) {
    long start = System.nanoTime();
    try {
      ds.get(ds.numKeys());      
    } catch (Exception e) {
      System.out.print("Unexpected exception occured! dsInput failed!: " + e.getMessage());
    }
    long end = System.nanoTime();
    return end - start;
  }

}
