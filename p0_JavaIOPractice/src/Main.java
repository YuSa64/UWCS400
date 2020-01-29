/**
 * Name: Hyeon Jun Jeong
 * Email: hjeong44@wisc.edu
 * Lecture: CS400-001
 * Decription: This file do Standard Output, Input, File Input, output.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
  private static final Scanner scn = new Scanner(System.in);
  private static final String title = "Hyeon Jun Jeong, hjeong44@wisc.edu, CS400-001";

  public static void main(String[] args) {

    // Standard Output
    System.out.println(title);
    //

    // Standard Input
    while (true)
      try {
        System.out.print("Please type your race: ");
        String race = scn.nextLine();
        System.out.print("Please type your age: ");
        int age = Integer.parseInt(scn.nextLine());
        System.out.print("Please type your major: ");
        String major = scn.nextLine();
        System.out.println("Your race is " + race);
        System.out.println("Your age is " + age);
        System.out.println("Your major is " + major);
        break;
      } catch (Exception e) {
        System.out.println("Error has occured!");
      }
    //

    // File Input
    while(true)
    try {
      System.out.print("Please type input file name (default name is \"input.txt\"): ");
      Scanner fscn = new Scanner(new File(scn.nextLine()));
      while (fscn.hasNext())
        System.out.println("It is written: " + fscn.nextLine());
      fscn.close();
      break;
    } catch (FileNotFoundException e1) {
      System.out.println("File does not Exist!");
    }
    //

    // File Output
    try {
      System.out.print("Please type output file name (dfault name is \"output.txt\"): ");
      PrintWriter pw = new PrintWriter(new File(scn.nextLine()));
      System.out.print("Please type anything you want to type in (Enter \"Exit\" to exit): ");
      String type = "";
      do {
        type = scn.nextLine();
        pw.println(type);
      } while (!type.equalsIgnoreCase("exit"));
      pw.close();
      System.out.println("You are now good to leave");
    } catch (Exception e) {
      System.out.println("Error has occured!");
    }
    //

  }
}
