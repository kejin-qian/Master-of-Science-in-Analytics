package HW5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OneTimeAppointment extends Appointment {
    // Private member variables
    // date is in the format of MM/DD/YYYY
    private String date;

    // Constructor
    public OneTimeAppointment(String Description, String date) {
        super(Description);
        this.date = date;
    }

    @Override
    // Create a string representation for One Time appointment
    public String toString() {
        return date + " - One Time - " + Description;
    }

    // Override the inherited occursOn() to provide the proper implementation
    @Override
    public boolean occursOn(int year, int month, int day) throws ParseException {
        // parse the appointment date and user-input date into data type Date
    	Date aptdate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        Date SelectedDate = new SimpleDateFormat("MM/dd/yyyy").parse(month+ "/" + day + "/" + year);
        // compare two dates. If aptdate = selecteddate, then True, this appointment will happen on the selected date, otherwise False.
        if (aptdate.equals(SelectedDate)) {
            return true;
        } else {
            return false;}
    }
}
