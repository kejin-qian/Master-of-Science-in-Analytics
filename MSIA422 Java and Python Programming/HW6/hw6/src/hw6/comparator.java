package hw6;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

// User Manual:
// by running this file, it will ask user to input an array size (100 or 200)
// then the output will be 3 txt. files of the distributed names using three different hash functions
// output file can be distinguished by file names (eg. output(hashCode)-size 100.txt)
// there is also another file containing the variances (variance of the numbers of names per each line)
// of the three hashing functions (e.g. Variance_Comparison_size 100.txt)

public class comparator {
    // create BadInputException to catch bad user input
	@SuppressWarnings("serial")
	static class BadInputException extends Exception {
		BadInputException() {
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner scan = null;
		int UserSelection = 0;
		int size = 0;
		boolean status = true;
		try {
			scan = new Scanner(System.in);
			while (status) {
				try {
					// ask user to input a size of the array
					System.out.println("Please enter the size of the array (100 or 200)");
					// read in user input
					UserSelection = scan.nextInt();
					// if user input is valid
					if (UserSelection == 100 || UserSelection == 200) {
						//record the size
						size = UserSelection;
						//change status to false to terminate the while loop
						status = false;
					} else {
						//if user input is not valid, throw a badinputexception
						throw new BadInputException();
					}
				} catch (BadInputException e) {
					System.out.println("Invalid Input! Please enter 100 or 200!");
				} catch (Exception e) {
					System.out.println("Invalid Input! Please enter 100 or 200!");
					scan.next();
				}
			}
		} finally {
			scan.close();

		}
		//create a list of string to record the variances we are going to calculate
		String[] VarList = new String[3];
		//initialize the output format
		VarList[0] = "Variance of Hashing Method1 (ASCII) with size = " + size + " : ";
		VarList[1] = "Variance of Hashing Method2 (HashCode) with size = " + size + " : ";
	    VarList[2] = "Variance of Hashing Method3 (Exponential) with size = " + size + " : ";
		
	    //run the methods in three hashing classes and add the variance into VarList
	    double var1 = Hashing1.hashing(size);
	    VarList[0] += Double.toString(var1);
	    double var2 = Hashing2.hashing(size);
	    VarList[1] += Double.toString(var2);
	    double var3 = Hashing3.hashing(size);
	    VarList[2] += Double.toString(var3);
	    
	    //write the variances output to a txt file
	    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Variance_Comparison_size " + size + ".txt"));
	    for (int i = 0; i < VarList.length; i++) {
	        bufferedWriter.write(VarList[i]+"\n"); 
	    }
	    bufferedWriter.close();
	    
	    

	}
}
