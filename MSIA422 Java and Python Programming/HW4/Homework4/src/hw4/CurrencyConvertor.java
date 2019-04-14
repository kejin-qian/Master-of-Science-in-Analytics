package hw4;

import java.util.Scanner;

public class CurrencyConvertor {
    @SuppressWarnings("serial")
    // create a BadNumberException class
    // which will capture bad inputs such as negative exchange rates, conversion amounts
	static class BadNumberException extends Exception {
        BadNumberException() {
        }

    }

    @SuppressWarnings("serial")
    // create a MainMenuException class
    // which will capture invalid input for menu options (integers not in 1 - 4)
	static class MainMenuException extends Exception {
        MainMenuException() {
        }

    }

    public static void main(String[] args) {
        float YentoUSD;  // exchange rate
        int UserSelection = 0;  // user input for the main menu
        int preset = 0; // used to track the user selection
        boolean resetPrice = false; // to track if the user is reseting the exchange rate
        YentoUSD = 1; // initialize an exchange rate, will be changed to user input later
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            boolean ReceivedRate = false;
            while (true) {
                try {
                    if (preset == 0) {
                      // print an user prompt
                        System.out.println(
                                "What do you want to do today:\n"  + "Please select from the following four options\n" + "\n"
                                		+ "1. Enter/Change a price for one dollar in Japanese Yen\n"
                                        + "2. Convert from USD to Japanese Yen\n" + "3. Convert from Japanese Yen to USD\n"
                                        + "4. Quit the Currency Convertor");
                        // read the user input
                        UserSelection = scanner.nextInt();
                    } else { 
                        UserSelection = preset; // proceed to the target section without input
                    }
                    if (UserSelection == 1) {
                        System.out.println("What is the current price for one USD in Japanese Yen today?");
                        YentoUSD = scanner.nextFloat(); // read user input for exchange rate
                        if (YentoUSD < 0) {
                            throw new BadNumberException(); // raise exception for inputting negative numbers
                        } else {
                            ReceivedRate = true; // one exchange rate is stored in the system
                            // back to main menu and ask user input again
                            UserSelection = 0; 
                            preset = 0;
                        }
                     // an exchange rate has already been stored, ready to do conversions from USD to Yen.
                    } else if (ReceivedRate) {
                        if (UserSelection == 2 && !resetPrice) {
                            System.out.println("How much USD do you want to convert?\n"
                                    + "Enter 0 if you want to change the price for one USD in Japanese Yen today");
                            float UserInput = scanner.nextFloat();
                            //the user selects to reset price
                            if (UserInput == 0) {
                                preset = 2; 
                                resetPrice = true;
                            } else if (UserInput < 0) {
                                throw new BadNumberException();
                            } else {
                            	// convert from USD to Yen
                                System.out.printf(" Yen : %.2f %n", UserInput * YentoUSD); 
                                // conversion finished.. go back to main menu, ask user for a new input
                                UserSelection = 0; 
                                preset = 0;
                            }
                            // interface for reseting price
                        } else if (UserSelection == 2 && resetPrice) {
                            System.out.println("Enter a new price for one dollar in Japanese Yen");
                            YentoUSD = scanner.nextFloat();
                            if (YentoUSD < 0) {
                                throw new BadNumberException();
                            } else {
                            	// go back to the interface for option 2 (conversion from USD -> Yen)
                                preset = 2;
                                resetPrice = false;
                            }
                        // an exchange rate has already been stored, ready to do conversions from Yen to USD.
                        } else if (UserSelection == 3 && !resetPrice) {
                            System.out.println("How much Yen do you want to convert?\n"
                                    + "Enter 0 if you want to change the price for one USD in Japanese Yen today");
                            float UserInput = scanner.nextFloat(); // read user input for amount of Yen he/her wants to convert to USD
                            // user chooses to reset price, go back to interface of main menu option 3
                            if (UserInput == 0) {
                                preset = 3;
                                resetPrice = true; // activate reset price to be True
                            } else if (UserInput < 0) {
                                throw new BadNumberException();
                            } else {
                                System.out.printf(" USD : %.2f %n", UserInput / YentoUSD); // convert Yen to USD
                                // go back to main menu
                                UserSelection = 0;
                                preset = 0;
                            }
                        } else if (UserSelection == 3 && resetPrice) {
                            System.out.println("Enter a new price for one dollar in Japanese Yen");
                            YentoUSD = scanner.nextFloat(); // read user's new input for price
                            if (YentoUSD < 0) {
                                throw new BadNumberException();
                            } else {
                                preset = 3;
                                resetPrice = false;
                            }
                        // if user input is 4, quit the system
                        } else if (UserSelection == 4) {
                            System.out.println("End");
                            break;
                        } else {
                            throw new MainMenuException(); // raise exception for invalid integer input
                        }
                      // even if the price has not been set, user can quit the system by inputing 4
                    } else if (UserSelection == 4) {
                        System.out.println("End");
                        break;
                    } else if (UserSelection > 4 || UserSelection < 1) {
                        throw new MainMenuException();
                     // user has to set a price before doing any conversion
                    } else {
                        System.out.println("Please enter 1 to set a price for one USD in Japanese Yen !");
                    }
                } catch (BadNumberException e) {
                    System.out.println("Warning: Bad Number!\n" + "Please try again!");
                    preset = UserSelection;
                } catch (MainMenuException e) {
                    System.out.println("Warning: You have to select from 1 to 4!\n" + "Please try again!");
                    UserSelection = 0;
                  //catch all other exception 
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


