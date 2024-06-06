package Project1;

import Project1.EmployeeManager;
import Project1.GuestManager;
import Project1.Employee;
import Project1.Guest;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException 
    {
        
        // Local variables
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Guest> guests = new ArrayList<>();
        ArrayList<Spa> appointments = new ArrayList<>();
        ArrayList<Reservations> reservations = new ArrayList<>();

        
        Scanner scanner = new Scanner(System.in);

        // Read from file
        rooms = Room.readRooms();
        employees = Employee.readEmployees();
        guests = Guest.readGuests();
        appointments = Spa.readAppointments(guests);
        reservations = Reservations.readReservations(guests, rooms);
        
        
 
        System.out.println("\n Welcome to The Beach Club Reservation System");
            
        // Display options
        int choice;
    do {
        System.out.println("\n⛱  Main Menu  ⛱\n");
        System.out.println("1. Guests");
        System.out.println("2. Reservations");
        System.out.println("3. Rooms");
        System.out.println("4. Employees");
        System.out.println("5. Spa");
        System.out.println("6. Exit");

        System.out.print("\n ଳ Enter here: ");
           
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) 
            {
                case 1:
                    // Guests Menu
                    int guestChoice;
                    do {
                        System.out.println("\n⛱  Guests  ⛱\n");
                        System.out.println("1. Show all Guests");
                        System.out.println("2. Add new Guest");
                        System.out.println("3. Edit Guest");
                        System.out.println("4. Upgrade Guest to VIP");
                        System.out.println("5. Search for Guest");
                        System.out.println("6. Remove Guests");
                        System.out.println("7. Return to Main Menu");

                        System.out.print("\n ଳ Enter here: ");
                        guestChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        switch (guestChoice) {
                            case 1:
                                GuestManager.showAllGuests(guests, scanner);
                                break;
                            case 2:
                                GuestManager.addNewGuest(guests, scanner);
                                break;
                            case 3:
                                GuestManager.editGuest(guests, scanner);
                                break;
                            case 4:
                                GuestManager.upgradeToVip(guests, scanner);
                                break;
                            case 5:
                                GuestManager.searchForGuest(guests, scanner);
                                break;
                            case 6:
                                GuestManager.removeGuest(guests, scanner);
                            case 7:
                                break; // Return to Main Menu
                                
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                                break;
                        }
                    } while (guestChoice != 7); // Return to Main Menu
                    break;
                case 2:
                    // Reservations Menu
                    int reservationChoice;
                    do {
                        System.out.println("\n⛱  Reservations  ⛱\n");
                        System.out.println("1. Show all Reservations");
                        System.out.println("2. Add Reservation");
                        System.out.println("3. Cancel Reservation");
                        System.out.println("4. Pay for Reservations");
                        System.out.println("5. Return to Main Menu");

                        System.out.print("\n ଳ Enter here: ");
                        reservationChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        switch (reservationChoice) {
                            case 1:
                                ReservationManager.showAllReservations(reservations, scanner);
                                break;
                            case 2:
                                ReservationManager.addReservation(reservations, guests, rooms, scanner);
                                break;
                            case 3:
                                ReservationManager.cancelReservation(reservations, rooms, scanner);
                                break;
                            case 4:
                                ReservationManager.payForReservations(reservations, scanner);
                                break;
                            case 5:
                                break; // Return to Main Menu
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                                break;
                        }
                    } while (reservationChoice != 5); // Return to Main Menu
                    break;
                case 3:
                    // Rooms Menu
                    int roomChoice;
                    do {
                        System.out.println("\n⛱  Rooms  ⛱\n");
                        System.out.println("1. Show all Rooms");
                        System.out.println("2. Add new Room");
                        System.out.println("3. Edit Room");
                        System.out.println("4. Delete Room");
                        System.out.println("5. Return to Main Menu");

                        System.out.print("\n ଳ Enter here: ");
                        roomChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        switch (roomChoice) {
                            case 1:
                                RoomManager.showAllRooms(rooms);
                                break;
                            case 2:
                                RoomManager.addNewRoom(rooms, scanner);
                                break;
                            case 3:
                                RoomManager.editRoom(rooms, scanner);
                                break;
                            case 4:
                                RoomManager.deleteRoom(rooms, scanner);
                                break;
                            case 5:
                                break; // Return to Main Menu
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                                break;
                        }
                    } while (roomChoice != 5); // Return to Main Menu
                    break;
                case 4:
                    // Employees Menu
                    int employeeChoice;
                    do {
                        System.out.println("\n⛱  Employees  ⛱\n");
                        System.out.println("1. Show all Employees");
                        System.out.println("2. Add new Employee");
                        System.out.println("3. Edit Employee");
                        System.out.println("4. Delete Employee");
                        System.out.println("5. Return to Main Menu");

                        System.out.print("\n ଳ Enter here: ");
                        employeeChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        switch (employeeChoice) {
                            case 1:
                                EmployeeManager.showAllEmployees(employees);
                                break;
                            case 2:
                                EmployeeManager.addNewEmployee(employees, scanner);
                                break;
                            case 3:
                                EmployeeManager.editEmployee(employees, scanner);
                                break;
                            case 4:
                                EmployeeManager.deleteEmployee(employees, scanner);
                                break;
                            case 5:
                                break; // Return to Main Menu
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                                break;
                        }
                    } while (employeeChoice != 5); // Return to Main Menu
                    break;
                case 5:
                    // Spa Menu
                    int spaChoice;
                    do {
                        System.out.println("\n⛱  Spa  ⛱\n");
                        System.out.println("1. Add Spa Appointment");
                        System.out.println("2. Show all Spa Appointments");
                        System.out.println("3. Edit Spa Appointment");
                        System.out.println("4. Return to Main Menu");

                        System.out.print("\n ଳ Enter here: ");
                        spaChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        switch (spaChoice) {
                            case 1:
                                SpaManager.addAppointment(appointments, guests, scanner);
                                break;
                            case 2:
                                SpaManager.showAllAppointments(appointments);
                                break;
                            case 3:
                                SpaManager.editAppointment(appointments, scanner);
                                break;
                            case 4:
                                break; // Return to Main Menu
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                                break;
                        }
                    } while (spaChoice != 4); // Return to Main Menu
                    break;
                case 6:
                    // Write rooms to file before exiting
                    Room.writeRooms(rooms);
                    System.out.println("Rooms saved to file. Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    break;
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input.");
                scanner.nextLine(); // Consume invalid input
                choice = 0; // Reset choice to continue the loop
            }
        } while (choice != 6);


        scanner.close();
    }
}
