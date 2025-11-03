package com.logan.lab4;

import java.util.Scanner;

public class PomonaTransitApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n------ Lab 4: Pomona Transit System ------");
            System.out.println("1. View Trip Schedules");
            System.out.println("2. Edit Trip Offerings");
            System.out.println("3. Other Functions");
            System.out.println("4. Exit");
            System.out.print("\nSelect an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> runScheduleViewer(); // seperate function for schedule viewer
                case "2" -> TripOfferingEditor.main(null); // uses offering editor class
                case "3" -> OtherFunctions.main(null); // uses administration class
                case "4" -> {
                    System.out.println("Exiting program.");
                    System.exit(0);
                }
                default -> System.out.println("Please enter 1, 2, 3, or 4.");
            }
        }
    }

    private static void runScheduleViewer() {
        System.out.print("\nEnter Start Location: ");
        String start = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();

        System.out.print("Enter Date (yyyy-mm-dd): ");
        String date = scanner.nextLine();

        TripScheduleViewer.displayTripSchedule(start, destination, date);
    }
}
