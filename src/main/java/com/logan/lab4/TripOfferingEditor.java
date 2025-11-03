package com.logan.lab4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

public class TripOfferingEditor {

    private static final Scanner scanner = new Scanner(System.in);

    public static void deleteTripOffering() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Trip Number: ");
            int tripNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Date (yyyy-mm-dd): ");
            String date = scanner.nextLine();

            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            String startTime = scanner.nextLine();

            String query = "DELETE FROM TripOffering WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, tripNumber);
                pstmt.setDate(2, Date.valueOf(date));
                pstmt.setTime(3, Time.valueOf(startTime));

                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0 ? "Trip offering deleted." : "No matching trip offering found.");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void addTripOfferings() {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";

            while (true) {
                System.out.print("Enter Trip Number: ");
                int tripNumber = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter Date (yyyy-mm-dd): ");
                String date = scanner.nextLine();

                System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
                String startTime = scanner.nextLine();

                System.out.print("Enter Scheduled Arrival Time (HH:MM:SS): ");
                String arrivalTime = scanner.nextLine();

                System.out.print("Enter Driver Name: ");
                String driverName = scanner.nextLine();

                System.out.print("Enter Bus ID: ");
                int busId = Integer.parseInt(scanner.nextLine());

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, tripNumber);
                    pstmt.setDate(2, Date.valueOf(date));
                    pstmt.setTime(3, Time.valueOf(startTime));
                    pstmt.setTime(4, Time.valueOf(arrivalTime));
                    pstmt.setString(5, driverName);
                    pstmt.setInt(6, busId);

                    pstmt.executeUpdate();
                    System.out.println("Trip offering added.");
                }

                System.out.print("Do you want to add another trip offering? (Y/N): ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void changeDriver() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Trip Number: ");
            int tripNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Date (yyyy-mm-dd): ");
            String date = scanner.nextLine();

            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            String startTime = scanner.nextLine();

            System.out.print("Enter New Driver Name: ");
            String newDriver = scanner.nextLine();

            String query = "UPDATE TripOffering SET DriverName = ? WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newDriver);
                pstmt.setInt(2, tripNumber);
                pstmt.setDate(3, Date.valueOf(date));
                pstmt.setTime(4, Time.valueOf(startTime));

                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0 ? "Driver updated." : "No matching trip offering found.");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void changeBus() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Trip Number: ");
            int tripNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Date (yyyy-mm-dd): ");
            String date = scanner.nextLine();

            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            String startTime = scanner.nextLine();

            System.out.print("Enter New Bus ID: ");
            int newBusId = Integer.parseInt(scanner.nextLine());

            String query = "UPDATE TripOffering SET BusID = ? WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, newBusId);
                pstmt.setInt(2, tripNumber);
                pstmt.setDate(3, Date.valueOf(date));
                pstmt.setTime(4, Time.valueOf(startTime));

                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0 ? "Bus updated." : "No matching trip offering found.");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n------ Edit Schedule ------");
            System.out.println("1. Delete a trip offering");
            System.out.println("2. Add a set of trip offerings");
            System.out.println("3. Change driver for a trip offering");
            System.out.println("4. Change bus for a trip offering");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> deleteTripOffering();
                case "2" -> addTripOfferings();
                case "3" -> changeDriver();
                case "4" -> changeBus();
                case "5" -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }            
        }
    }
}

