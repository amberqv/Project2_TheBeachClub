
package Project1;

import Project1.Guest;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author triciaamber
 */
public class SpaManager {
    
    // 4 methods
    
    // Add New Spa Appointment
    public static void addAppointment(ArrayList<Spa> appointments, ArrayList<Guest> guests, Scanner scanner) {
        System.out.println("Adding New Appointment...\n");
        
        // Display existing guest names
        System.out.println("Currently existing Guest Names:\n");
        for (Guest guest : guests) {
            System.out.println(" ⛱ " + guest.getFirstName() + " " + guest.getLastName());
        }
        
        int appointmentId;
        Guest guest = null;
        String service;
        String time;
        double price;
        
        // Generate a new appointment ID
        appointmentId = generateAppointmentId(appointments);
        
        // Select guest for the appointment
        System.out.print("\nEnter full name: ");
        String guestName = scanner.nextLine();
        
        // Find the guest object from the provided list
        for (Guest g : guests) {
            if ((g.getFirstName() + " " + g.getLastName()).equalsIgnoreCase(guestName.trim())) {
                guest = g;
                break;
            }
        }
        
        if (guest == null) {
            System.err.println("Guest " + guestName + " not found.");
            return;
        }
        
        // Enter service details
        System.out.print("Enter the spa service: ");
        service = scanner.nextLine();
        
        // Enter appointment time
        System.out.print("Enter the appointment time: ");
        time = scanner.nextLine();
        
        // Enter price
        while (true) {
            try {
                System.out.print("Enter the price: ");
                price = scanner.nextDouble();
                scanner.nextLine(); // Consume newline character
                break; // Exit the loop if input is successfully read
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Enter a valid number for price.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        
        // Apply VIP discount if applicable
        if (guest instanceof VIP) 
        {
            VIP vipGuest = (VIP) guest;
            if (vipGuest.getLoyaltyPoints() >= 80) 
            {
                
                double discount = price * 0.50; // 50% discount for VIP guests with at least 80 loyalty points
                price -= discount; // Apply discount to the price
                
                


                System.out.println("\nVIP Discount Applied:");
                System.out.println("Guest is a VIP.\n");
                System.out.println("Loyalty Points balance: " + vipGuest.getLoyaltyPoints());
                System.out.println("Discount Applied: " + discount);
                System.out.println("Total: " + price);
                
                // Deduct loyalty points
                vipGuest.useSpa();
            }
        }
        
        // Create new appointment object and add to file
        Spa appointment = new Spa(appointmentId, guest, service, time, price);
        appointments.add(appointment);
        Spa.writeAppointments(appointments);
        
        System.out.println("\nAppointment added successfully.");
        
        // Prompt the user to add another room or go back to the main menu
                    while (true) 
                    {
                    System.out.print("\n1. Add another Appointment\n2. Main Menu\n\n ଳ ");
                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (choice == 2) 
                        {
                            return; // Return to the main menu
                        } else if (choice == 1) 
                        {
                            addAppointment(appointments, guests, scanner); // Add another appointment
                            return;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        System.out.println(" ଳ ");
                        scanner.nextLine(); // Consume invalid input
                    }
                }
    }
    
    // Generate a new appointment ID
    private static int generateAppointmentId(ArrayList<Spa> appointments) {
        int maxId = 0;
        for (Spa appointment : appointments) {
            if (appointment.getAppointmentId() > maxId) {
                maxId = appointment.getAppointmentId();
            }
        }
        return maxId + 1;
    } 



    // Show all Spa Appointments
    public static void showAllAppointments(ArrayList<Spa> appointments) 
        {

//            ArrayList<Room> appointments = Appointments.readAppointments(); // Read rooms from file

            if (appointments.isEmpty()) 
            {
                System.out.println("No appointments available.");
            } 

            else 
            {
                System.out.println("\nList of appointments:\n");
                for (Spa appointment : appointments) 
                {
                    System.out.println(appointment);
                    System.out.println("\n");
                }
            }
            
            System.out.println("ଳ Press Enter to go back to main menu...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); // Wait for Enter key press
        }
    
        
        // Edit a Spa Appointment
        public static void editAppointment(ArrayList<Spa> appointments, Scanner scanner) {
            int appointmentId;
            while (true) {
                try {
                    // Display existing appointment IDs
                    System.out.println("\n——— Current Appointment: ———\n");
                    for (Spa appointment : appointments) {
                        System.out.println(" ⛱ " + appointment.getAppointmentId()+ " " 
                                + appointment.getGuest().getFirstName() + " " 
                                + appointment.getGuest().getLastName());
                    }

                    System.out.print("\nEnter the ID of the appointment you want to edit"
                            + "\n'x' to cancel: "
                            + "\n ଳ ");

                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("x")) {
                        System.out.println("\nCancelling edit operation...");
                        return; // Cancel editing
                    }

                    appointmentId = Integer.parseInt(input);

                    // Find the appointment with the given ID
                    Spa appointmentToEdit = null;
                    for (Spa appointment : appointments) {
                        if (appointment.getAppointmentId() == appointmentId) 
                        {
                            appointmentToEdit = appointment;
                            break;
                        }
                    }

                    if (appointmentToEdit == null) {
                        System.out.println("Appointment ID " + appointmentId + " not found.");
                        continue; // Prompt for appointment ID again
                    }

                    System.out.println("\nNow editing:\n");
                    System.out.println(appointmentToEdit);

                    // Gather new appointment details
                    
                    // New Service
                    System.out.print("Enter new service (press Enter to skip): ");
                    String newService = scanner.nextLine();
                    if (!newService.isEmpty()) {
                        appointmentToEdit.setService(newService);
                    }

                    // New Time
                    System.out.print("Enter new Time (press Enter to skip): ");
                    String newTime = scanner.nextLine();
                    if (!newTime.isEmpty()) {
                        appointmentToEdit.setTime(newTime);
                    }
                    
                    //New Total
                    while (true) {
                        try {
                            System.out.print("Enter new Price (press Enter to skip): ");
                            String priceInput = scanner.nextLine();
                            if (!priceInput.isEmpty()) {
                                double newPrice = Double.parseDouble(priceInput);
                                appointmentToEdit.setPrice(newPrice);
                            }
                            break; // Exit the loop if price input is successfully read
                        } 
                        
                        catch (NumberFormatException e) 
                        {
                            System.out.println("Invalid input format. Please enter a valid number for price.");
                        }
                    }
                    
                    
                    

                    // Write updated appointments to file
                    System.out.println("\n——— ⛱ Appointment ID " + appointmentId + " updated successfully ⛱ ——— \n");
                    Spa.writeAppointments(appointments);

                    // Prompt the user to edit another appointment or go back to the main menu
                    while (true) {
                        try {
                            System.out.print("\n1. Edit another appointment\n2. Main Menu \nଳ  ");
                            int choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            if (choice == 2) {
                                System.out.println("\nGoing back to the main menu...");
                                return; // Return to the main menu
                            } else if (choice == 1) {
                                System.out.println("\nEditing another appointment...");
                                break; // Continue editing another appointment
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input format.");
                            scanner.nextLine(); // Consume invalid input
                        }
                    }

                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Invalid input format.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
        }


    
    
    
    

}