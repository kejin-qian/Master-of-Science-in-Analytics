package HW5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Assumption: 
// We know that February doesn't have 30th and 31st and in most of the years it doesn't have 29th too
// Also April, June, Sept and Nov don't have 31st too.
// If a user wants to book a monthly appointment on day = 29/30/31, he/she will have no appointments booked
// in those months which don't have this specific day.
// For example, if a user books a monthly appointment for day = 31, startmonth = 1, startyear = 2000, endmonth = 12,
// endyear = 2000. Appointments will only be booked for January,March,May,July,August,October and December in 2000.

public class MonthlyAppointment extends Appointment {
    // Private member variables
    // date is in the format of MM/DD/YYYY
    private String Day;
    private String startmonth;
    private String startyr;
    private String endmonth;
    private String endyr;

    // Constructor
    public MonthlyAppointment(String Description, String Day, String startmonth,
                              String startyr, String endmonth, String endyr) {
        super(Description);
        this.Day = Day;
        this.startmonth = startmonth;
        this.startyr = startyr;
        this.endmonth = endmonth;
        this.endyr = endyr;
    }

    @Override
    // create a string representation of a monthly appointment
    public String toString() {
        return Day+"/"+startmonth+"/"+startyr + " to " + Day+"/"+endmonth+"/"+endyr + " - Monthly - " + Description;
    }

    // Override the inherited occursOn() to provide the proper implementation
    public boolean occursOn(int year, int month, int day) throws ParseException {
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(startmonth+"/01/"+startyr);
        Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(endmonth+"/01/"+endyr);
        Date SelectedDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/01/" + year);
        //Check whether the user selected date has a monthly appointment booked 
        if ((Integer.parseInt(Day) == day) && (SelectedDate.before(date2) || SelectedDate.equals(date2)) && (SelectedDate.after(date1) || SelectedDate.equals(date1))) {
            return true;
        } else {
            return false;
        }
    }}

