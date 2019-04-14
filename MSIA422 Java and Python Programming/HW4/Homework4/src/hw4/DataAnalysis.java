package hw4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;

public class DataAnalysis {
	public static void main(String[] args) throws IOException, ParseException {


        File file = new File("/Users/qiankejin/Desktop/HW4/employees.txt");
        // initialize empty array lists to store name, age and salary
        List<String> name = new ArrayList<>();
        List<Double> age = new ArrayList<>();
        List<Integer> salary = new ArrayList<>();
        Date today = new Date();
        // read lines from file
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(","); // split the line by comma
                // append the first item (name) of the line into name array list
                name.add(line[0]);
                // parse the date of birth into simple date format dd/mm/yyyy
                Date bdate = new SimpleDateFormat("dd/MM/yyyy").parse(line[1]);
                // find the age of an employee by calculating the time difference between
                // his/her date of birth and current time.
                long diffInMillies = Math.abs(bdate.getTime() - today.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                // convert days to years as age
                age.add(diff/365.0);
                // append salary to the salary array list
                salary.add(Integer.parseInt(line[2]));
            }
        }

        // 1. How many Employees exist in the file?
		System.out.println("There are " + name.size() + " employees in the file");
		// 2. Who has the highest salary?
		System.out.println(name.get(salary.indexOf(Max(salary))) + " has the highest salary");
		// 3. What is the average salary?
		// avarage_salary = sum of all employees' salaries / total number of employees
		Double average_salary = salary.stream().mapToInt(Integer::intValue).sum()/((double) salary.size());
		System.out.printf("The average salary of employees is %.2f %n", average_salary);
		// 4. How many employees make above the average?
		int count = 0;
		for(int i=1;i < salary.size();i++) {
			if(salary.get(i) > average_salary) {
				count = count + 1;
			}
		}
		System.out.println("There are " + count + " employees make salares above the average.");
		// 5. What is the average age of employees?
		double sumAge = age.stream().mapToDouble(Double::doubleValue).sum(); //sum up all the ages of all employees
		double avgAge = sumAge/age.size();
		System.out.printf("The average age of employees is %.2f %n", avgAge);
		
		// Write to an output file the employee names sorted according to their salary?
	    ArrayIndexComparator comparator = new ArrayIndexComparator(salary);
	    Integer[] indexes = comparator.createIndexArray();
	    //sort the index by using key = salary in ascending order
	    Arrays.sort(indexes, comparator);
	    String pathname = "/Users/qiankejin/Desktop/HW4/sorted_employees.txt"; 
	    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathname));//write the employees' names to the path given above
	    for (int i = 0; i < indexes.length; i++) {
	        bufferedWriter.write(name.get(indexes[i])+"\n"); //write the names line by line
	    }
	    bufferedWriter.close();
	}
	// write my own function for finding max value in a list of integers
     public static Integer Max(List<Integer> salary){
	    Integer max = salary.get(0);
	    for(int i=1;i < salary.size();i++){
		    if(salary.get(i) > max){
			  max = salary.get(i);
			}
	  }
	  return max;
	}
    // implement my own index comparison criterion
    static class ArrayIndexComparator implements Comparator<Integer>
    {
        public List<Integer> array;

        public ArrayIndexComparator(List<Integer> array)
        {
            this.array = array; //initialize an attribute called array from input
        }

        public Integer[] createIndexArray()
        {// create an array of index [0,1,2,...]
            Integer[] indexes = new Integer[array.size()];
            for (int i = 0; i < array.size(); i++)
            {
                indexes[i] = i; 
            }
            return indexes;
        }

        @Override
        // sort the index based on key = salary in ascending order
        public int compare(Integer index1, Integer index2)
        {
            return array.get(index1).compareTo(array.get(index2));
        }
    }
}
