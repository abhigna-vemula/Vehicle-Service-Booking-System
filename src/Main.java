import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BookingDAO dao = new BookingDAO();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Vehicle Service Booking System ===");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addBooking(); break;
                case "2": viewBookings(); break;
                case "3": updateBooking(); break;
                case "4": deleteBooking(); break;
                case "5": searchByVehicle(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Goodbye!");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add booking");
        System.out.println("2. View bookings");
        System.out.println("3. Update service type/date");
        System.out.println("4. Delete booking");
        System.out.println("5. Search by vehicle number");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addBooking() {
        try {
            System.out.print("Vehicle No: ");
            String vehicleNo = sc.nextLine().trim();
            if (vehicleNo.isEmpty()) { System.out.println("Vehicle number required."); return; }

            System.out.print("Customer Name: ");
            String customerName = sc.nextLine().trim();
            if (customerName.isEmpty()) { System.out.println("Customer name required."); return; }

            System.out.print("Service Type: ");
            String serviceType = sc.nextLine().trim();
            if (serviceType.isEmpty()) { System.out.println("Service type required."); return; }

            System.out.print("Service Date (DD-MM-YYYY): ");
            String dateStr = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateStr, formatter);


            Booking b = new Booking(0, vehicleNo, customerName, serviceType, date);
            boolean ok = dao.addBooking(b);
            System.out.println(ok ? "Booking added." : "Failed to add booking.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use DD-MM-YYYY.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewBookings() {
        List<Booking> list = dao.getAllBookings();
        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Booking b : list) {
            System.out.println(b);
        }
    }

    private static void updateBooking() {
        try {
            System.out.print("Enter Booking ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Booking existing = dao.getBookingById(id);
            if (existing == null) {
                System.out.println("Booking not found!");
                return;
            }

            System.out.print("New Service Type (leave empty to keep current): ");
            String serviceType = sc.nextLine().trim();
            if (serviceType.isEmpty()) {
                serviceType = existing.getServiceType();
            }

            System.out.print("New Service Date (DD-MM-YYYY) (leave empty to keep current): ");
            String dateStr = sc.nextLine().trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate date = dateStr.isEmpty()
                    ? existing.getServiceDate()
                    : LocalDate.parse(dateStr, formatter);

            System.out.println(
                    dao.updateService(id, serviceType, date)
                    ? "Updated successfully!"
                    : "Update failed."
            );


        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Use DD-MM-YYYY.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input.");
        }
    }


    private static void deleteBooking() {
        try {
            System.out.print("Enter booking ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = dao.deleteBooking(id);
            System.out.println(ok ? "Booking deleted." : "Delete failed (maybe ID not found).");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Enter a number.");
        }
    }

    private static void searchByVehicle() {
        System.out.print("Enter vehicle number or part of it: ");
        String vehicleNo = sc.nextLine().trim();
        List<Booking> list = dao.searchByVehicleNo(vehicleNo);
        if (list.isEmpty()) System.out.println("No matching bookings.");
        else list.forEach(System.out::println);
    }
}
