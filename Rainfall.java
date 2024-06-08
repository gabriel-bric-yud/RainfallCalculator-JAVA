import java.util.Scanner;
import java.text.DecimalFormat;

public class Rainfall {
	
	//Here I create static variables that I will use statically in my functions.
	private static String[] monthList =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};	
	private static Scanner scan = new Scanner(System.in);
	private static DecimalFormat dfmt = new DecimalFormat("#.##");
	
	
	//This function uses a monthList array to return the String name of month at index num-1.
	private static String getMonth(int num) {
		return monthList[num-1];
	}
	
	
	//This function prompts to user whether they want to start/restart the application. Depending on the user-input, it will return true or false.
	private static boolean restart() {
		System.out.print("\nPress ENTER to start application (Q to Quit) ");
		String start = scan.nextLine();	
		if (start.toLowerCase().equals("q"))
			return false;
		else
			return true;
	}
	
	
	//This function gets the total years of data that a user wants to enter.
	//I use a while loop for validation to make sure the total years is within an acceptable range. (0 - 2024)
	//I also validate whether the scan.nextInt() runs without error. Only integers are accepted.
	private static int getYears() {
		int years = 0;
		while (years <= 0 || years > 2024) {
			try {
				System.out.print("\nEnter how many years of rainfall data you would like to calculate: ");
				years = scan.nextInt();
				scan.nextLine();
				
				if (years <= 0 || years > 2024) {
					System.out.println("\nPlease enter a valid number of years!");
				}
			}
			catch (Exception e) {
				System.out.println("\nPlease enter a valid whole number!");
		        scan.nextLine();
			}
		}
		 return years;
	}
	
	
	//This function will display the menu and allow the user to choose which option they want.
	//The parameters are all the data that has been collected.
	//I use a do-while loop to give the user multiple uses of the menu before they choose to move on to the next part of the program.
	private static void createMenu(int i, int years, int months, int currentMonth, int currentTotalMonths, int totalMonths, double totalRain, double yearlyRain, double averageYearly, double averageTotal) {
		String showData = "";
		do {
			//Display Menu.
			System.out.println("\nMENU:");
			System.out.print("R for RAINFALL totals");
			System.out.print(" | A for AVERAGES");
			System.out.print(" | M for MONTH");
			System.out.print(" | S to SHOW ALL stats");
			System.out.print(" | ENTER to continue ");
			showData = scan.nextLine();
			
			//Check user choice and display data.
			if (showData.toLowerCase().trim().equals("s")) {
				System.out.println("\nCurrent yearly rainfall (YEAR " + (i+1) + "): " + yearlyRain + " inches");
				System.out.println("Average monthly rainfall for YEAR " + (i+1) + ": " + dfmt.format(averageYearly) + " inches");
				System.out.println("Total rainfall declared for a period of " + (totalMonths + currentMonth) + " months: " + totalRain + " inches");
				System.out.println("Average monthly rainfall for a period of "+ (totalMonths + currentMonth) + " months: " + dfmt.format(averageTotal) + " inches");
				
			}
			else if (showData.toLowerCase().trim().equals("r")) {
				System.out.println("\nCurrent yearly rainfall: " + yearlyRain + " inches");
				System.out.println("Total rainfall declared for a period of " + (totalMonths + currentMonth) + " months: " + totalRain + " inches");
				
			}
			else if (showData.toLowerCase().trim().equals("a")) {
				System.out.println("\nAverage monthly rainfall for YEAR " + (i+1) + ": " + dfmt.format(averageYearly) + " inches");
				System.out.println("Average monthly rainfall for a period of "+ (totalMonths + currentMonth) + " months: " + dfmt.format(averageTotal) + " inches");
				
			}
			else if (showData.toLowerCase().trim().equals("m")) {
				System.out.println("\nCurrently on month " + currentTotalMonths + " out of " + (years * months));	
			}					
		} 
	
		while (showData != "");
	}
	
	
	//This function instantiates new variables for all the data I want to calculate for the Period of years that the user requested.
	//The outer for-loop iterates every year, the inner for-loop iterates once every month (12 times).
	//The inner loop uses a while loop to validate that monthly rainfall is not negative.
	//I also validate the monthly rainfall to make sure no errors are thrown by scan.nextDouble()
	private static void collectRainfallData(int months, int years) {
		double totalRain = 0.0;
		double averageYearly = 0.0;
		double averageTotal = 0.0;
		
		//outer loop start.
		for (int i = 0; i < years; i++) {
			int totalMonths = i * months;
			double yearlyRain = 0.0;
			
			//inner loop start.
			for (int currentMonth = 1; currentMonth <= months; currentMonth++) {
				double monthlyRain = -1;
				while (monthlyRain < 0) {
					try {
						System.out.print("\nEnter inches of rainfall for " + getMonth(currentMonth) + " in YEAR " + (i + 1) + ": ");
						monthlyRain = scan.nextDouble();
						scan.nextLine();
						
						if (monthlyRain < 0) {
							System.out.println("\nERROR! Please enter a valid number for monthly rainfall!");
						}
					}
					catch (Exception e) {
						System.out.println("\nERROR! Please enter a number!");
				        scan.nextLine();
					}
				}
				
				//Do calculations.
				int currentTotalMonths = totalMonths + currentMonth;
				yearlyRain += monthlyRain;
				averageYearly = yearlyRain / currentMonth;
				totalRain += monthlyRain;
				averageTotal = totalRain / currentTotalMonths;
				
				//Send calculations to Menu for user to interact with.
				createMenu(i, years, months, currentMonth, currentTotalMonths, totalMonths, totalRain, yearlyRain, averageYearly, averageTotal);
				
				//Display special message if 12 months have passed.
				if (currentMonth == 12 && currentTotalMonths != (months * years)) {
					System.out.println("\nTotal yearly rainfall in year " + (i+1) + ": " + dfmt.format(yearlyRain) + " inches");
					System.out.println("Average monthly rainfall for year " + (i+1) + ": " + dfmt.format(averageYearly) + " inches");
					
					if (i+2 <= years)
						System.out.println("\nStarting YEAR " + (i+2));
					else
						System.out.println("\nPERIOD FINISHED. DATA PROCESSED.");
				}
			}
		} 
		
		//Outer and inner loop ends.
		
		//Display final data.
		System.out.println("\nTotal rainfall for period of " + years + " years: " + totalRain + " inches");
		System.out.println("Average monthly rainfall for period of " + years + " years: " + dfmt.format(averageTotal) + " inches");
		System.out.println("\nThanks for using the Rainfall Application!");
		
	}
	
	
	
	public static void main(String[] args) {
		//instantiate constants and delimiters.
		final int MONTHS = 12;
		scan.useDelimiter("\\R");
		
		//Welcome the user
		System.out.println("Welcome to the Rainfall Application!");
		
		//Ask the user if they initially want to start the application.
		Boolean startApp = restart();
		
		//start application loop which runs until user is prompted my restart() function again and chooses to end.
		while (startApp) {
			//get years for this iteration.
			int years = getYears();
			//Display confirmation to user.
			System.out.println("\nYou have chosen to submit rainfall data for " + years * MONTHS + " months.");
			System.out.println("\nPress ENTER to begin");
			scan.nextLine();
			//Start collection data for all months.
			collectRainfallData(MONTHS, years);
			//Ask user if they want to restart or quit the application.
			startApp = restart();
		}
		
		//End program and close scanner.
		System.out.println("\nProgram Terminated. Goodbye!");
		scan.close();
	}

}
