package com.logan.lab4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

public class TripScheduleViewer {

    public static void displayTripSchedule(String startLocation, String destination, String date) {
        String query = "SELECT T2.ScheduledStartTime, T2.ScheduledArrivalTime, T2.DriverName, T2.BusID " +
               "FROM Trip T, TripOffering T2 " +  
               "WHERE T.TripNumber = T2.TripNumber " +  
               "AND T.StartLocationName = ? " +
               "AND T.DestinationName = ? " +
               "AND T2.Date = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startLocation);
            pstmt.setString(2, destination);
            pstmt.setDate(3, Date.valueOf(date));

            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                Time startTime = rs.getTime("ScheduledStartTime");
                Time arrivalTime = rs.getTime("ScheduledArrivalTime");
                String driverName = rs.getString("DriverName");
                int busId = rs.getInt("BusID");

                System.out.printf("\nStart: %s | Arrival: %s | Driver: %s | Bus ID: %d%n",
                                  startTime, arrivalTime, driverName, busId);
            }

            if (!found) {
                System.out.println("No trips found for the inputted data.");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Start Location: ");
        String start = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();

        System.out.print("Enter Date (yyyy-mm-dd): ");
        String date = scanner.nextLine();

        displayTripSchedule(start, dest, date);
    }
}

