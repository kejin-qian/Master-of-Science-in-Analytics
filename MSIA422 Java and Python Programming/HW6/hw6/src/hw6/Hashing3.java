package hw6;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static java.lang.Math.pow;

public class Hashing3 {
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
                String line = scanner.nextLine();
                double sum = 0;
                for (int i = 0; i < line.length(); i++){
                	//find the ASCII value c for each character in the name
                    char c = line.charAt(i);
                    //sum = sum of (c^e mod size)
                    sum += pow((double)c, Math.E)% size;
                }
                //the hashvalue of this name is calculated by sum mod size
                Integer hashValue = (int)(sum) % size;
                if (hashValue > 0) {
                    hash[hashValue - 1] += line + ", ";
                } else if (hashValue == 0) {
                    hash[size - 1] += line + ", ";
                } else {
                    hash[hashValue + size - 1] += line + ", ";
                }
            }
            
            // evaluate the performance of this output by calculating the variance
            metric m = new metric(hash,size);
            double var = m.getVar();
            //write final output to a txt file
            String output = "output(Exponential)-size " + size + ".txt";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(output));
            for (int i = 0; i < size; i++) {
                String[] temp = hash[i].split(" ");
                if (temp.length == 1) {
                    hash[i] += "EMPTY LINE...\n\n";
                } else {
                    hash[i] = hash[i].substring(0, hash[i].length() - 2) + "\n\n";
	                }
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
