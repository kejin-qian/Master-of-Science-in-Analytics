package HW5;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

// create a class called appointment manager
public class AppointmentManager {
    //initialize an empty ArrayList called database which is used to store all the appointments booked by users in a session
    private List<Appointment> database = new ArrayList<Appointment>();

    // Instantiate an AppointmentManager
    public AppointmentManager() {
    }
    // create a method which gets the input of an appointment and stores it into AppointmentManager database
    public void addAppointment(Appointment a) {
        this.database.add(a);
    }
    // since database is a private variable, so design a method to get the database
    public List<Appointment> getDatabase() {
        return database;
    }

    // Save all the appointments in the current database to a text file with given path
    public void saveAppointment(String address) throws IOException {
        String pathname = address;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathname));
        for (int i = 0; i < database.size(); i++) {
            bufferedWriter.write(database.get(i) + "\n");
        }
        bufferedWriter.close();
    }
    // Load appointments from a file to the current database
    public void loadAppointment(String filepath) throws IOException {
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);
        String aptType;
        while (scanner.hasNextLine()) {
        	// each appointment is stored as Date - Type - Description, so use " - " to split the line
            String[] line = scanner.nextLine().split(" - ");
            // get the type of the appointment 
            aptType = line[1];
            //initialize an empty appointment
            Appointment apt;
            //create an appointment based on the appointment type obtained above and store this appointment to database
            if (aptType.equals("One Time")) {
                apt = new OneTimeAppointment(line[2], line[0]);
                this.addAppointment(apt);
            } else if (aptType.equals("Daily")) {
                String sdate = line[0].split(" to ")[0];
                String edate = line[0].split(" to ")[1];
                apt = new DailyAppointment(line[2], sdate, edate);
                this.addAppointment(apt);
            } else if (aptType.equals("Monthly")) {
                String sdate = line[0].split(" to ")[0];
                String edate = line[0].split(" to ")[1];
                String day = sdate.split("/")[0];
                String smonth = sdate.split("/")[1];
                String syear = sdate.split("/")[2];
                String emonth = edate.split("/")[1];
                String eyear = edate.split("/")[2];
                apt = new MonthlyAppointment(line[2],day, smonth, syear, emonth, eyear);
                this.addAppointment(apt);
            }

        }scanner.close();
    }
    
    //Have the user enter a date and print out all appointments that occur on that date.
    public void listAppointment(int year, int month, int day) throws ParseException {
    	// create an empty list which will be used to store appointments booked for the user selected date
        List<Appointment> occurson = new ArrayList<Appointment>();
        // for each appointment in the current database, use occurson method to test whether it is booked on the user selected date
        // if yes, add it to the occurson list
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).occursOn(year, month, day) == true) {
                occurson.add(database.get(i));
            }
         // if the occurson list is empty, then no appointments are booked for this date
        }if (occurson.size() == 0) {
        	System.out.println("There are no appointments booked for this date!");
        // else print out all the booked appointments 
        }else {
        for (int i = 0; i < occurson.size(); i++) {
            System.out.println(occurson.get(i));
        }
        }
    }

    // check the given date in string is a valid date
    public static boolean dateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    // check that the first date is the same or before the second date
    public static boolean dateOrder(String date1, String date2) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setLenient(false);
        Date Date1 = df.parse(date1);
        Date Date2 = df.parse(date2);
        if (Date1.before(Date2) || Date1.equals(Date2)) {
            return true;
        } else {
            return false;
        }

    }

    @SuppressWarnings("serial")
    static class MainMenuException extends Exception {
        MainMenuException() {
        }

    }

    @SuppressWarnings("serial")
    static class InvalidDateException extends Exception {
        InvalidDateException() {
        }

    }

    @SuppressWarnings("serial")
    static class BadTypeException extends Exception {
        BadTypeException() {
        }
    }

    @SuppressWarnings("serial")
    static class DateOrderException extends Exception {
        DateOrderException() {
        }
    }

    // TODO: 2018/11/18 User Interface in Main
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = null;
        int UserSelection = 0;
        int preset = 0;
        int typeEntered = 0;
        String lastType = ""; //to record the last type entered by user


        // preset values for Daily
        int sdateEntered = 0;
        String lastSdate = "";  // used to record the start date entered by user

        // initialize a new appointment manager 
        AppointmentManager am = new AppointmentManager();
        try {
            scanner = new Scanner(System.in);
            while (true) {
                try {
                    if (preset == 0) {
                    	// print out the main menu of the appointment book
                        System.out.println("Welcome to the Appointment Book!\n"
                                + "Please select from the following five options\n" + "\n" + "1. Add A New Appointment\n"
                                + "2. Save All Appointments Into A Text File\n"
                                + "3. Load Appointments From A Text File\n"
                                + "4. Check Appointments Booked For A Specific Day\n" + "5. Quit the Appointment Book\n");
                        // read the user input
                        UserSelection = scanner.nextInt();

                    } else {
                        UserSelection = preset;
                    }
                    if (UserSelection == 1) {
                    	// if the user chooses to book an appointment
                        String AptType;
                        if (typeEntered == 0) {
                            System.out.println("Please Enter The Type Of Appointment (Enter OneTime/Daily/Monthly)");
                            AptType = scanner.next();
                            lastType = AptType; // record the appointment type user just entered
                            typeEntered = 1;
                        } else {
                            AptType = lastType;
                        }
                        // to book an one time appointment
                        if (AptType.equals("OneTime")) {
                            String date;
                            System.out.println("Please Enter the Date of Appointment in (MM/DD/YYYY)");
                            date = scanner.next();
                            // raise a InvalidDateException if the date user entered is not valid
                            if (!dateValid(date)) {
                                throw new InvalidDateException();
                            }

                            System.out.println("Please Enter a Description For This Appointment");
                            scanner.nextLine();
                            String desc = scanner.nextLine();
                            Appointment onetimeap = new OneTimeAppointment(desc, date);
                            // add the created new appointment to current database
                            am.addAppointment(onetimeap);
                            System.out.println("Appointment Booked!\n");
                            typeEntered = 0;
                            preset = 0;
                            //to book a daily appointment
                        } else if (AptType.equals("Daily")) {
                            String sdate;
                            if (sdateEntered == 0) {
                                System.out.println("Please Enter the Start Date of Appointment in (MM/DD/YYYY)");
                                sdate = scanner.next();
                                if (!dateValid(sdate)) {
                                    throw new InvalidDateException();
                                }
                                lastSdate = sdate;
                                sdateEntered = 1;
                            } else {
                                sdate = lastSdate;
                            }
                            String edate;
                            System.out.println("Please Enter the End Date of Appointment in (MM/DD/YYYY)");
                            edate = scanner.next();
                            if (!dateValid(edate)) {
                                throw new InvalidDateException();
                            }
                            // if the start date is later than the end date, throw DateOrderException
                            if (!dateOrder(sdate, edate)) {
                                throw new DateOrderException();
                            }
                            System.out.println("Please Enter a Description For This Appointment");
                            scanner.nextLine();
                            String desc = scanner.nextLine();
                            Appointment dap = new DailyAppointment(desc, sdate, edate);
                            am.addAppointment(dap);
                            System.out.println("Appointment Booked!\n");
                            sdateEntered = 0;
                            typeEntered = 0;
                            preset = 0;
                            //to create a monthly appointment
                        } else if (AptType.equals("Monthly")) {
                            String userinput;
                            System.out.println(
                                    "Please Enter the Day(DD), Start Month(MM), Start Year(YYYY), End Month(MM), End Year(YYYY) and separated by comma, with no space.");
                            scanner.nextLine();
                            userinput = scanner.next();
                            // if the user input doesn't satisfy the requirement above(too many inputs or missing some inputs)
                            // throw an InvalidDateException
                            if (userinput.split(",").length != 5) {
                                throw new InvalidDateException();
                            }
                            String day = userinput.split(",")[0];
                            String smonth = userinput.split(",")[1];
                            String syr = userinput.split(",")[2];
                            String emonth = userinput.split(",")[3];
                            String eyr = userinput.split(",")[4];
                            String date1 = smonth + "/" + day + "/" + syr;
                            if (!dateValid(date1)) {
                                throw new InvalidDateException();
                            }
                            String date2 = emonth + "/" + day + "/" + eyr;
                            if (!dateValid(date2)) {
                                throw new InvalidDateException();
                            }
                            if (!dateOrder(date1, date2)) {
                                throw new DateOrderException();
                            }
                            System.out.println("Please Enter a Description For This Appointment");
                            scanner.nextLine();
                            String desc = scanner.nextLine();
                            Appointment map = new MonthlyAppointment(desc, day, smonth, syr, emonth, eyr);
                            am.addAppointment(map);
                            System.out.println("Appointment Booked!\n");
                            UserSelection = 0;
                            typeEntered = 0;
                            preset = 0;
                        } else {
                        	//if the user enters an invalid appointment type, raise BadTypeException
                            throw new BadTypeException();
                        }
                        //2. Save All Appointments Into A Text File
                    } else if (UserSelection == 2) {
                        System.out.println("Please Enter the Path That You Want to Text File To Be Saved.\n"
                        		+ "Example: /Users/qiankejin/Desktop/MSiA/MSiA422/HW5/save.txt");
                        scanner.nextLine();
                        String path = scanner.nextLine();
                        am.saveAppointment(path);
                        System.out.println("The text file has been successfully saved to " + path);
                        preset = 0;
                       //3. Load Appointments From A Text File
                    } else if (UserSelection == 3) {
                        System.out.println("Please Enter the Path Of The File.");
                        scanner.nextLine();
                        String path = scanner.nextLine();
                        am.loadAppointment(path);
                        System.out.println("The text file has been successfully loaded");
                        preset = 0;
                       //Check Appointments Booked For A Specific Day
                    } else if (UserSelection == 4) {
                        System.out.println("Please enter a date that you want to check in (MM/DD/YYYY)");
                        String date = scanner.next();
                        if (!dateValid(date)) {
                            throw new InvalidDateException();
                        }
                        int month = Integer.parseInt(date.split("/")[0]);
                        int day = Integer.parseInt(date.split("/")[1]);
                        int year = Integer.parseInt(date.split("/")[2]);
                        //call the list appointment function in appointment manager to check if there are any appointments booked on this date
                        am.listAppointment(year, month, day);
                        preset = 0;
                       //quite the appointment book
                    } else if (UserSelection == 5) {
                        System.out.println("End");
                        break;
                       // if the user selects an invalid option from main menu, raise MainMenuException
                    } else if (UserSelection > 5 || UserSelection < 1) {
                        throw new MainMenuException();
                    }

                } catch (MainMenuException e) {
                    System.out.println("Warning: You have to select from 1 to 5!\n" + "Please try again!");
                    UserSelection = 0;
                } catch (InvalidDateException e) {
                    System.out.println("Warning: Please enter a valid date(s)!");
                    preset = UserSelection;
                } catch (BadTypeException e) {
                    System.out.println("Warning: Please enter a valid Appointment Type (OneTime/Daily/Monthly)!");
                    preset = UserSelection;
                    typeEntered = 0;
                } catch (DateOrderException e) {
                    System.out.println("Warning: Please check the order of start date and end date! ");
                    preset = UserSelection;
                    sdateEntered = 0;
                    // when the user selects to load a file, if the file is not found, raise FileNotFound Exception
                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found! Please find the right path! Press C then Enter to retry.");
                    preset = UserSelection;
                    scanner.next();

                } catch (Exception e) {
                    System.out.println("Warning: This is not a valid entry!\n" + "Please try again!");
                    preset = UserSelection;
                    scanner.next();
                }
                continue;
            }

        } finally {
            // close the scanner
            if (scanner != null) {
                scanner.close();
            }
        }

    }

}