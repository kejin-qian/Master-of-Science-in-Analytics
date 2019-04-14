package HW5;

import java.text.ParseException;

// superclass Appointment
public abstract class Appointment {
	   protected String Description;
	   
	   // Constructor
	   public Appointment (String desc) {
	      Description = desc;
	   }
	   
	   @Override
	   //create a string representation for the general appointment
	   public String toString() {
		      return "Appointment[ " + Description + " ]";
		   }
	  //create an abstract method occursOn in the super class, will be overrode in 
	  //all subclasses
	   abstract boolean occursOn(int year, int month, int day) throws ParseException;
}
