package HW5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyAppointment extends Appointment {
    // Private member variables
    // date is in the format of MM/DD/YYYY
    private String startdate;
    private String enddate;

    // Constructor
    public DailyAppointment(String Description, String startdate, String enddate) {
    	super(Description); // The variable Description is inherited from the superclass
        this.startdate = startdate;
        this.enddate = enddate;
    }

    @Override
    // Design a string representation of a Daily Appointment
    public String toString() {
        return startdate + " to " + enddate + " - Daily - " + Description;
    }

    // Override the inherited occursOn() to provide the proper implementation
    @Override
    public boolean occursOn(int year, int month, int day) throws ParseException {
        //parse appointment start date, end date and user-input date into Date datatype
    	Date sdate = new SimpleDateFormat("MM/dd/yyyy").parse(startdate);
        Date edate = new SimpleDateFormat("MM/dd/yyyy").parse(enddate);
        Date SelectedDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/" + day + "/" + year);
        //compare whether user selected date is between the start date and end date (inclusive) of the daily appointment
        if ((SelectedDate.before(edate) || SelectedDate.equals(edate)) && (SelectedDate.after(sdate) || SelectedDate.equals(sdate))) {
            return true;
        } else {
            return false;
        }
    }

}
