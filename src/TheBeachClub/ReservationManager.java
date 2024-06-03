
package TheBeachClub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author sarak
 */
public class ReservationManager {
    
        private static final DateTimeFormatter formatter 
                = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    
        // Add New Reservation
        public static void addReservation(ArrayList<Reservations> reservations, ArrayList<Guest> guests, ArrayList<Room> rooms, Scanner scanner) {
        System.out.println("Adding New Reservation...\n");
        
       

        // Enter check-in date
        System.out.print("Enter check-in date (dd-MM-yyyy): "
                            + "\n'x' to cancel:                     ");          
                    String input = scanner.nextLine().trim();
                    
                    if (input.equalsIgnoreCase("x")) 
                    {
                        System.out.println("returning home...\n");
                        return; // Cancel 
                    }
                    
        LocalDate checkInDate;
     
        try {
            checkInDate = LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please enter date in format dd-MM-yyyy.");
            return;
        }

        // Enter check-out date
        System.out.print("Enter check-out date (dd-MM-yyyy): ");
        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine(), formatter);
        
        
        System.out.println("Dates selected: " + checkInDate + " to " + checkOutDate);

        
        // Ask if it's for an existing guest
        System.out.print("\n Is it for an existing guest? (y/n): ");
        String existingGuestChoice = scanner.nextLine().toLowerCase();
        boolean existingGuest = existingGuestChoice.equals("y");

        
        Guest guest = null; 

        
        // If it's for an existing guest, display existing guest names
        if (existingGuest) {
            System.out.println("Currently existing Guests:\n");
            for (Guest g : guests) {
                System.out.println(" ⛱ " + g.getFirstName() + " " + g.getLastName());
            }

            boolean guestFound = false;
            // Keep asking for the guest name until a valid one is entered or the user chooses to create a new guest
            while (!guestFound) {
                // Select guest for the reservation
                System.out.print("\nEnter full name of the guest \n(or 'new' to create a new guest): ");
                String guestName = scanner.nextLine();

                // Check if the user wants to create a new guest
                if (guestName.equalsIgnoreCase("new")) {
                    existingGuest = false;
                    break;
                }

                // Find the guest object from the provided list
                for (Guest g : guests) 
                {
                    // Check if the input matches either the first name, last name, or full name of any guest
                    if (g.getFirstName().equalsIgnoreCase(guestName) || 
                        g.getLastName().equalsIgnoreCase(guestName) ||
                        (g.getFirstName() + " " + g.getLastName()).equalsIgnoreCase(guestName.trim())) 
                    {
                        guest = g;
                        guestFound = true;
                        break;
                    }
                }


                if (!guestFound) {
                    System.err.println("Guest " + guestName + " not found.");
                }
            }
        }
        

        // If it's not for an existing guest, create a new guest
        if (!existingGuest) {
            System.out.println("\nCreating New Guest...\n");
            System.out.print("Enter guest first name: "
                    + " ଳ ");
            String firstName = scanner.nextLine();
            System.out.print("Enter guest last name: "
                    + " ଳ ");
            String lastName = scanner.nextLine();
            System.out.print("Enter guest age: "
                    + " ଳ ");
            int age;
            while (true) {
                try {
                    age = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a valid age.");
                }
            }
            System.out.print("Enter guest email: "
                    + " ଳ ");
            String email = scanner.nextLine();
            System.out.print("Enter guest phone number: "
                    + " ☏ ");
            String phone = scanner.nextLine();

            // Validate age, email, and phone number
            if (!Guest.isValidAge(age)) {
                System.out.println("Guest must be 18 years of age or older.");
                return;
            }
            if (!Guest.isValidEmail(email)) {
                System.out.println("Invalid email. Please enter a valid email address.");
                return;
            }
            if (!Guest.isValidPhoneNumber(phone)) {
                System.out.println("Invalid phone number. Please enter a valid phone number.");
                return;
            }

            guest = new Guest(firstName, lastName, age, email, phone);
            guests.add(guest); // Add the new guest to the list of guests
            Guest.writeGuests(guests); // Write the updated guest list to file
        }

    
    
        // Displays available rooms
        System.out.println("\nAvailable Rooms:\n");
        for (Room room : rooms) {
            if (room.isRoomAvailable(checkInDate, checkOutDate)) 
            {
                System.out.println(" ⛱ Room Id " + room.getId() + ", " + room.getType() + ", $" + room.getPrice());
            }
        }

        // Select room for the reservation
        Room room = null;
        boolean roomFound = false;
        while (!roomFound) {
            System.out.print("\nEnter Room Id to reserve (or 'x' to choose a different room): ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("x")) {
                // Reset the roomFound flag to allow user to select a different room
                break;
            }

            try {
                int roomId = Integer.parseInt(userInput);
                // Check if the selected room ID exists
                for (Room r : rooms) {
                    if (r.getId() == roomId) {
                        roomFound = true;
                        room = r; // Assign the selected room to the 'room' variable
                        break;
                    }
                }
                if (!roomFound) {
                    System.out.print("Invalid Room Id. Please enter a valid number: ");
                } else if (!room.isRoomAvailable(checkInDate, checkOutDate)) {
                    System.out.println("Room " + roomId + " is not available for the selected dates. Please choose a different room.");
                    roomFound = false; // Reset roomFound flag to allow user to select a different room
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid Room ID: ");
            }
        }
        
        
        // Update the booked dates of the room
        room.bookRoom(checkInDate, checkOutDate); // Book the room with the new dates

        
        
        // Get the price of the selected room
        double price = room.getPrice();

        // Calculate the number of days of the reservation
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        // Calculate the total price based on the number of days and the price per day from the room
        double totalPrice = numberOfDays * room.getPrice();
        
        // VIP rate if discounts are applicable
        double discounted = 0;
        
        
        
        // Check if the guest is a VIP member
        if (guest instanceof VIP) {
            VIP vipGuest = (VIP) guest;
            
            System.out.println("\n");
            System.out.println(vipGuest.getFirstName() + " " + vipGuest.getLastName() + " is a VIP Member ♧");
            System.out.println("15% Discount Available for 200 points");
            System.out.println("Loyalty Points Balance: " + vipGuest.getLoyaltyPoints());
            
            //apply discount to total price
            discounted = totalPrice * 0.85;
            System.out.println("Reservation Total after discount will be: " + (discounted)); // Apply discount to total price

            
            
            // Ask if the user wants to apply the 15% discount to the room price
            System.out.println("Do you want to apply a 15% discount to the room price? (y/n): ");
            String discountChoice = scanner.nextLine().toLowerCase();
            switch (discountChoice) {
                case "y":
    // Apply the discount directly within the switch statement
    if (vipGuest.VIPRoomDiscount(totalPrice)) { // Call the method and check if discount applied successfully
        System.out.println("New Reservation Total: " + (totalPrice * 0.85));

        // No need to update loyalty points here since it's already updated within the VIPRoomDiscount method
        // Also, no need to read and write guest information here
    } else {
        System.out.println("Failed to apply discount."); // Handle the case when discount cannot be applied
    }
    break;
            }
        } else {
            System.out.println("Discounts Applicable: 0"+
                    guest.getFirstName() + " " + guest.getLastName() + " is not a VIP Member\n");
        }

        
        

        // Display invoice 
            System.out.println("\n ⭒⭒⭒⭒ Invoice ⭒⭒⭒⭒");
        System.out.println("\nPrice per night  $" + room.getPrice());
        System.out.println("Number of nights:   " + numberOfDays);
        System.out.println("Total Rate         $" + discounted);
        System.out.println("⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒");
        
        
        // Verify to continue or choose a different room ID
        System.out.print("\nPress Enter to continue "
                + "\nor 'x' to enter a different Room ID: ");
        String userInput = scanner.nextLine();

        
            // If 'x' was entered, return to the beginning of the reservation process
            if (!roomFound) {
                System.out.println("Returning to room selection...\n");
                return;
            } else {
            // Set status to "Reserved"
            String status = "Reserved";

        

        // Create and add the reservation to the list
        Reservations newReservation = new Reservations(reservations.size() + 1, checkInDate, checkOutDate, totalPrice, status, guest, room);
        reservations.add(newReservation);

        // Write the updated reservations list to file
        Reservations.writeReservations(reservations);

        // Write the updated room information to file
        Room.writeRooms(rooms);

        // Display confirmation message
        System.out.println("\n⋅˚‧ ଳ ‧˚⋅ Room now Reserved ⋅˚‧ ଳ ‧˚⋅\n");
        
        // Reward the VIP guest with 100 points for making a reservation
                    if (guest instanceof VIP) {
                        ((VIP) guest).reservationReward();
                    }
        
        }
      
    
        // Ask if the user wants to pay for the reservation now or later
        System.out.println("Do you want to pay for the reservation now? (y/n): ");
        String payNowChoice = scanner.nextLine().toLowerCase();
        switch (payNowChoice) {
            case "y":
                // Pay for the reservation
                payForReservations(reservations, scanner);
                break;
            case "n":
                System.out.println("You can pay for the reservation later.");
                break;
            default:
                System.err.println("Invalid choice. Please enter 'y' or 'n'.");
                break;      
        }
        
        
        // Ask if the user wants to add another reservation or return to the menu
        System.out.println("\n1. Add another reservation");
        System.out.println("2. Return to the menu");

        System.out.print(" ଳ ");
        String userChoice = scanner.nextLine();

        switch (userChoice) {
            case "1":
                // Add another reservation
                addReservation(reservations, guests, rooms, scanner);
                break;
            case "2":
                // Return to the menu
                System.out.println("Returning to the main menu...");
                break;
            default:
                System.err.println("Invalid input. Please enter either 1 or 2.");
                break;
        }
}


        // Cancel Reservation
        public static void cancelReservation(ArrayList<Reservations> reservations, ArrayList<Room> rooms, Scanner scanner) {
        System.out.println("Canceling Reservation...\n");
        
        if (reservations.isEmpty()) 
            {
                System.out.println("No reservations available.");
            } 

            else 
            {
                System.out.println("\nList of Reservations:\n");
                for (Reservations r : reservations) 
                {
                    System.out.println(r);
                    System.out.println("\n");
                }
            }
        

        // Ask for reservation ID
        System.out.print("Select reservation ID or Enter to cancel: ");
        int reservationId;
        try {
            reservationId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid reservation ID.");
            return;
        }

        // Find the reservation
        Reservations reservationToRemove = null;
        for (Reservations reservation : reservations) {
            if (reservation.getReservationsId() == reservationId) {
                reservationToRemove = reservation;
                break;
            }
        }

        // If reservation found, remove it
        if (reservationToRemove != null) {
            // Free up booked dates of the room
            Room room = reservationToRemove.getRoom();
            room.cancelBooking(reservationToRemove.getCheckIn(), reservationToRemove.getCheckOut());

            // Remove reservation from the list
            reservations.remove(reservationToRemove);

            // Write the updated reservations list to file
            Reservations.writeReservations(reservations);

            // Write the updated room information to file
            Room.writeRooms(rooms);

            System.out.println("Reservation with ID " + reservationId + " canceled successfully!");
        } else {
            System.out.println("Reservation with ID " + reservationId + " not found.");
        }
    }

	
    
  
   public static void showAllReservations(ArrayList<Reservations> reservations, Scanner scanner) 
   {
    System.out.println("Showing all Reservations...");
    
    for (int i = 0; i < reservations.size(); i++) {
           
        Reservations r = reservations.get(i); 
        
        Guest guest = r.getGuest();
        Room room = r.getRoom();
        
        System.out.println("\n—— ⛱ Reservation " + (i+1) + "———————————————\n");
        
        String fullName = guest.getFirstName() + " " + guest.getLastName();
        System.out.println("Guest Name: " + fullName);
        System.out.println("Check In Date: " + r.getCheckInToString());
        System.out.println("Check Out Date: " + r.getCheckOutToString());

        System.out.println("Room id: " + room.getId());
        System.out.println("Room type: "
                +r.getRoom().getType()+" "+r.getRoom().getDescription());
        System.out.println("Price: " + r.getPrice());
        System.out.println("Status: " + r.getStatus());
        System.out.println("\n");
    }
        System.out.println("ଳ Press Enter to go back to the main menu...");
        scanner.nextLine();
}
 
     public static void reservationByGuestName(ArrayList<Reservations> reservations, Scanner scanner) {
       System.out.println("Searching for Reservation by Guest Name...");
       
       System.out.println("Press 'Y' to proceed");
       System.out.println("Press 'N' to cancel");
       
       String choice = scanner.next(); 
       
       switch(choice) {
           case "Y":
               System.out.println("Enter the guest's name");
               String guestName = scanner.nextLine();
               System.out.println("Searching...");
               
               boolean guestFound = false; 
               for (Reservations r : reservations) {
                   String fullName = r.getGuest().getFirstName() + " " 
                           + r.getGuest().getLastName();
                   if(fullName.equalsIgnoreCase(guestName)) {
                       System.out.println(r.toString());
                       guestFound = true; 
                   }
               } if (!guestFound) {
                   System.out.println("No reservations under guest name");
                   break;
               }
               
           case "N": 
               System.out.println("Searching for guest cancelled.");
               break; 
           default: 
               System.out.println("This choice is not valid. Please enter 'Y' or 'N'.");
       }
       
           System.out.println("ଳ Press Enter to go back to the main menu...");
           scanner.nextLine();
        
    }
     
     
     
        public static boolean applyDiscountToReservation(Reservations reservation, VIP vipGuest) 
        {
        double roomRate = reservation.getRoom().getPrice();

        // Call VIPRoomDiscount method to apply discount
        boolean discountApplied = vipGuest.VIPRoomDiscount(roomRate);

        if (discountApplied) {
            // Print updated loyalty points and updated price of the room
            System.out.println("Your current Loyalty Points Balance is: " + vipGuest.getLoyaltyPoints());
            System.out.println("Updated price of the room: " + roomRate);
        }

        return discountApplied; // Return whether discount was applied or not
        }

     
        // Pay reservation
        public static void payForReservations(ArrayList<Reservations> reservations, Scanner scanner) 
        {
            System.out.println("Current Reservations:\n");
            for (Reservations r : reservations) {
                System.out.println("Reservation ID: " + r.getReservationsId() + ", Guest Name: " + r.getGuest().getFirstName() + " " + r.getGuest().getLastName());
            }

            System.out.print("\nEnter the ID of the reservation to pay for: ");
            int reservationId;
            try {
                reservationId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid reservation ID. Please enter a valid ID.");
                return;
            }

            boolean found = false;
            for (Reservations r : reservations) {
                if (r.getReservationsId() == reservationId) {
                    found = true;
                    if (r.getStatus().equalsIgnoreCase("Reserved")) {
                        // Display the invoice for the selected reservation
                        System.out.println("\n\n⭒⭒⭒⭒ ⭒⭒⭒⭒ Invoice for Id " + r.getReservationsId() + " ⭒⭒⭒⭒ ⭒⭒⭒⭒ \n");
                        System.out.println("Guest Name: "+ r.getGuest().getFirstName() + " " + r.getGuest().getLastName());
                        System.out.println("Arrival:    " + r.getCheckInToString());
                        System.out.println("Departure:  " + r.getCheckOutToString());
                        System.out.println("\nRoom:       "+ r.getRoom().getType() 
                                         + "\n          " +r.getRoom().getDescription());
                        System.out.println("\nTotal      $" + r.getPrice());
                        System.out.println("\n⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒ ⭒⭒⭒⭒\n");

                        
                        // Prompt user to continue
                        System.out.print("\nEnter to confirm payment: ");
                        String userInput = scanner.nextLine();

                        
                        r.setStatus("Paid");
                        System.out.println("Reservation paid successfully!");
                        
                        // Write updated reservations to file
                        Reservations.writeReservations(reservations);
                        
                        
                       
                    } else {
                        System.out.println("Reservation is already paid.");
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("Reservation with ID " + reservationId + " not found.");
            }
            
            
            System.out.println("ଳ Enter to return ...");
            scanner.nextLine(); // Wait for Enter key press
            
            
//                // Delay before returning home
//                System.out.println("\nReturning...");
//                try {
//                    for (int i = 3; i > 0; i--) {
//                        System.out.print(".");
//                        Thread.sleep(777); // Delay 
//                    }
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }

        }
}



