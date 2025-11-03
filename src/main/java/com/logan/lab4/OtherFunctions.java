package com.logan.lab4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

public class OtherFunctions {

    private static final Scanner scanner = new Scanner(System.in);

    // Display stops of a given trip
    public static void displayTripStops() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Trip Number: ");
            int tripNumber = Integer.parseInt(scanner.nextLine());

            String query = "SELECT * FROM TripStopInfo WHERE TripNumber = ? ORDER BY SequenceNumber";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, tripNumber);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int stopNumber = rs.getInt("StopNumber");
                    int sequence = rs.getInt("SequenceNumber");
                    int drivingTime = rs.getInt("DrivingTime");

                    System.out.printf("Stop #%d | Sequence: %d | Driving Time: %d mins%n", stopNumber, sequence, drivingTime);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Display weekly schedule of a driver
    public static void displayDriverSchedule() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Driver Name: ");
            String driverName = scanner.nextLine();

            System.out.print("Enter Date (yyyy-mm-dd, will show week containing this date): ");
            Date inputDate = Date.valueOf(scanner.nextLine());

            String query = "SELECT * FROM TripOffering " +
                           "WHERE DriverName = ? " +
                           "AND WEEK(Date, 1) = WEEK(?, 1) " +
                           "ORDER BY Date, ScheduledStartTime";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, driverName);
                pstmt.setDate(2, inputDate);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.printf("Trip #%d | Date: %s | Start: %s | Arrival: %s | Bus: %d%n",
                            rs.getInt("TripNumber"),
                            rs.getDate("Date"),
                            rs.getTime("ScheduledStartTime"),
                            rs.getTime("ScheduledArrivalTime"),
                            rs.getInt("BusID"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Add driver
    public static void addDriver() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Driver Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Telephone Number: ");
            String phone = scanner.nextLine();

            String query = "INSERT INTO Driver (DriverName, DriverTelephoneNumber) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, name);
                pstmt.setString(2, phone);
                pstmt.executeUpdate();
                System.out.println("Driver added.");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // 4. Add a bus
    public static void addBus() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Bus ID: ");
            int busId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Model: ");
            String model = scanner.nextLine();

            System.out.print("Enter Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            String query = "INSERT INTO Bus (BusID, Model, Year) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, busId);
                pstmt.setString(2, model);
                pstmt.setInt(3, year);
                pstmt.executeUpdate();
                System.out.println("Bus added.");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // 5. Delete a bus
    public static void deleteBus() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Bus ID to delete: ");
            int busId = Integer.parseInt(scanner.nextLine());

            String query = "DELETE FROM Bus WHERE BusID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, busId);
                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0 ? "Bus deleted." : "Bus not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // 6. Insert actual trip stop info
    public static void recordActualTripData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Trip Number: ");
            int tripNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Date (yyyy-mm-dd): ");
            Date date = Date.valueOf(scanner.nextLine());

            System.out.print("Enter Scheduled Start Time (HH:MM:SS): ");
            Time schedStart = Time.valueOf(scanner.nextLine());

            System.out.print("Enter Stop Number: ");
            int stopNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Scheduled Arrival Time (HH:MM:SS): ");
            Time schedArrival = Time.valueOf(scanner.nextLine());

            System.out.print("Enter Actual Start Time (HH:MM:SS): ");
            Time actualStart = Time.valueOf(scanner.nextLine());

            System.out.print("Enter Actual Arrival Time (HH:MM:SS): ");
            Time actualArrival = Time.valueOf(scanner.nextLine());

            System.out.print("Enter # of Passengers In: ");
            int in = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter # of Passengers Out: ");
            int out = Integer.parseInt(scanner.nextLine());

            String query = "INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, " +
                           "ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, tripNumber);
                pstmt.setDate(2, date);
                pstmt.setTime(3, schedStart);
                pstmt.setInt(4, stopNumber);
                pstmt.setTime(5, schedArrival);
                pstmt.setTime(6, actualStart);
                pstmt.setTime(7, actualArrival);
                pstmt.setInt(8, in);
                pstmt.setInt(9, out);

                pstmt.executeUpdate();
                System.out.println("Actual trip stop info saved.");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Transit Admin Menu ---");
            System.out.println("1. Display stops of a trip");
            System.out.println("2. Display weekly schedule of driver");
            System.out.println("3. Add driver");
            System.out.println("4. Add bus");
            System.out.println("5. Delete bus");
            System.out.println("6. Record actual trip stop info");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> displayTripStops();
                case "2" -> displayDriverSchedule();
                case "3" -> addDriver();
                case "4" -> addBus();
                case "5" -> deleteBus();
                case "6" -> recordActualTripData();
                case "7" -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }
}

