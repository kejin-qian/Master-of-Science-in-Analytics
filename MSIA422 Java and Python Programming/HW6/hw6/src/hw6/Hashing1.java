package hw6;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Hashing1 {
	Integer size;

	public static double hashing(Integer size) throws IOException {
		File file = new File("/Users/qiankejin/Desktop/MSiA/MSiA422/HW6/input.txt");
		//initialize a list of string which is used to store final output
		String[] hash = new String[size];
		//create an initial format for the final output
		for (int i = 0; i < size; i++) {
			hash[i] = Integer.toString(i + 1) + ": ";
		}
		//read the names line by line from input.txt
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				int sum = 0;
				String line = scanner.nextLine();
				//calculate the sum of ASCII values of characters in a name
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					sum += (int) c;
				}
				//find the hashvalue by calculating sum mod size
				Integer hashValue = sum % size;
				//if hashvalue = x where x > 0 then put this name at index x - 1 in hash
				if (hashValue > 0) {
					hash[hashValue - 1] += line + ", ";
				//put the name on the last line of the output
				} else if (hashValue == 0) {
					hash[size - 1] += line + ", ";
				} else {
					//if hashvalue is negative, add size to hashvalue first, then put it at index hashValue + size - 1 in hash
					hash[hashValue + size - 1] += line + ", ";
				}
			}
			// evaluate the performance of this output by calculating the variance
			metric m = new metric(hash, size);
			double var = m.getVar();
			
            //write output
			String output = "output(ASCII)-size " + size + ".txt";
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(output));
			for (int i = 0; i < size; i++) {
				String[] temp = hash[i].split(" ");
				//if temp.length == 1, then there is no name being distributed to this line
				if (temp.length == 1) {
					hash[i] += "EMPTY LINE...\n\n";
				} else {
					//if there are names distributed to this line, remove the comma and empty space at the end
					//change to a new line and create a blank line
					hash[i] = hash[i].substring(0, hash[i].length() - 2) + "\n\n";
				}
				//for the last line in the output, remove the \n\n at the end
				if (i == size - 1) {
					hash[i] = hash[i].trim();
				}
				bufferedWriter.write(hash[i]);
			}
			bufferedWriter.close();
			return var;
			
			
		}
	}
}
