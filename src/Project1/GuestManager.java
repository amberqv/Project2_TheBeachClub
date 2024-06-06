
package Project1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author sarak
 */
public class GuestManager  {
    
    //Add guest 
    
    public static void addNewGuest(ArrayList<Guest> guests, Scanner scanner) {
    System.out.println("Adding New Guest...");
    
    String firstName = "";
    String lastName = "";
    int age = 0;
    String email = "";
    String phone = "";
    
    // First Name
    while (true) {
        System.out.println("Enter First Name: ");
        firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            break;
        }
        System.out.println("First Name cannot be empty. Please enter a valid name.");
    }
    
    // Last Name
    while (true) {
        System.out.println("Enter Last Name: ");
        lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            break;
        }
        System.out.println("Last Name cannot be empty. Please enter a valid name.");
    }
    
    // Age
    while (true) {
        try {
            System.out.println("Enter Age: ");
            age = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            if (Guest.isValidAge(age)) {
                break;
            }
            System.out.println("Guest must be 18 years of age or older.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter a valid integer.");
            scanner.nextLine(); // Consume invalid input
        }
    }
    
    // Email
    while (true) {
        System.out.println("Enter Email: ");
        email = scanner.nextLine().trim();
        if (Guest.isValidEmail(email)) {
            break;
        }
        System.out.println("Invalid email format. Please enter a valid email address.");
    }
    
    // Phone
    while (true) {
        System.out.println("Enter Phone: ");
        phone = scanner.nextLine().trim();
        if (Guest.isValidPhoneNumber(phone)) {
            break;
        }
        System.out.println("Invalid phone number format. Please enter a valid phone number.");
    }
    
    // Create new guest object and add to list
    Guest newGuest = new Guest(firstName, lastName, age, email, phone);
    guests.add(newGuest);
    Guest.writeGuests(guests);
    
    System.out.println("\n ——— Guest added successfully ———");
}

        
       
   
    // Upgrade to VIP
    public static void upgradeToVip(ArrayList<Guest> guests, Scanner scanner) {
        System.out.println("Upgrading Guest to VIP... ");

            // Display existing guest names
            System.out.println("Guests: \n");
            for (Guest guest : guests) {
                System.out.println(" ⛱ " + guest.getFirstName() + " " + guest.getLastName());
            }

            System.out.println("\nSelect guest to upgrade: ");
            String name = scanner.nextLine(); // Read the entire line of input

            Guest guestToUpgrade = null;
        for (Guest g : guests) {
            // Check if the input matches either the first name, last name, or full name of any guest
            if (g.getFirstName().equalsIgnoreCase(name) || 
                g.getLastName().equalsIgnoreCase(name) ||
                (g.getFirstName() + " " + g.getLastName()).equalsIgnoreCase(name)) {
                guestToUpgrade = g;
                break;
            }
        }

            if (guestToUpgrade != null) 
            {
                if (guestToUpgrade instanceof VIP) {
                    System.out.println("This guest is already a VIP.");
                } 
                else {
                        // Upgrade the guest to VIP and give joining reward
                        int loyaltyPoints = 200;
                        VIP newVIP = new VIP(guestToUpgrade.getFirstName(), guestToUpgrade.getLastName(),
                                guestToUpgrade.getAge(), guestToUpgrade.getEmail(),
                                guestToUpgrade.getPhone(), loyaltyPoints);
                        String VIPNumber = newVIP.generateVIPNumber(); // Accessing generateVIPNumber() from the VIP instance
                        newVIP.setVIPNumber(VIPNumber); // Set the generated VIP number
                        guests.remove(guestToUpgrade);

                        //write to guests.txt file
                        guests.add(newVIP);
                        Guest.writeGuests(guests);
                        System.out.println("\n——— ⛱ "
                                + guestToUpgrade.getFirstName() + " " + guestToUpgrade.getLastName() 
                                +" is now a VIP member ⛱ ——— \n");
                        System.out.println("200 loyalty points rewarded for joining!");
                        System.out.println("Current points is now: "+ newVIP.getLoyaltyPoints());
                      }
            } 
            
            else 
            {
                System.out.println("Guest not found. Upgrade to VIP cancelled.");
            }
    
    
    // Ask if the user wants to add another reservation or return to the menu
        System.out.println("\n1. Upgrade another Guest");
        System.out.println("2. Return to the menu");

        System.out.print(" ଳ : ");
        String userChoice = scanner.nextLine();

        switch (userChoice) {
            case "1":
                // Add another reservation
                upgradeToVip(guests, scanner);
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



    
    //Show all Guests
    public static void showAllGuests(ArrayList<Guest> guests, Scanner scanner) {
        
        System.out.println("Printing list of Guests...");
        
        if (guests.isEmpty())
        {
            System.out.println("No Guests in System");
        }
        
        else
        {
            System.out.println("\nList of Guests:\n");
                for (Guest guest : guests) 
                {
                    System.out.println(guest);
                    
                }
        }
        System.out.println("ଳ Press Enter to go back to main menu...");
            scanner.nextLine(); // Wait for Enter key press
        
      
        
    }
    
    //Search for Guest 
    public static void searchForGuest(ArrayList<Guest> guests, Scanner scanner){
        
        System.out.println("Searching for Guest...");
        System.out.println("Press 1 to search by name "
                + "\n Press 2 to search by phone number");
        
        int searchChoice = scanner.nextInt();
                
            switch(searchChoice) {
                case 1: 
                    System.out.println("Enter first or last name");
                    String name = scanner.next();
                    for(Guest guest : guests) {
                        if (guest.getFirstName().equalsIgnoreCase(name) || 
                                guest.getLastName().equalsIgnoreCase(name)) {
                    System.out.println(guest.toString());
                    }
                }
                    break; 
                case 2: 
                    System.out.println("Enter phone number: ");
                    String phoneNumber  = scanner.next();
                    for(Guest guest : guests) {
                        if(guest.getPhone().equals(phoneNumber)) {
                            System.out.println(guest.toString());
                        }
                    }
                    break;
                default: 
                    System.out.println("Invalid choice...");
            }
    }
    
    public static void removeGuest(ArrayList<Guest> guests, Scanner scanner){
         
        System.out.println("Removing guest...");
    
        System.out.println("Enter the name of the guest to remove: ");
        String name = scanner.nextLine();
    
        Guest removeGuest = null;
    
        for (Guest guest : guests) {
        if (guest.getFirstName().equalsIgnoreCase(name) || guest.getLastName().equalsIgnoreCase(name)) {
            removeGuest = guest;
            break;
            }
        }
    
        if (removeGuest == null) {
        System.out.println("Guest not found. Please enter a valid name");
        return;
        }
    
        System.out.println("Guest found:");
        System.out.println(removeGuest);
        System.out.println("Press 'X' to confirm, or any other key to cancel: ");
        String confirmation = scanner.nextLine();
    
        if (confirmation.equalsIgnoreCase("X")) {
            guests.remove(removeGuest);
            Guest.writeGuests(guests);
            
            System.out.println("Guest removed successfully."); 
        } else {
            System.out.println("Operation cancelled.");
            }
    
        System.out.println("Press Enter to go back to the main menu...");
        scanner.nextLine();
    }
    
    public static void editGuest(ArrayList<Guest> guests, Scanner scanner) {
        
        System.out.println("Editing guest...");
        
        System.out.println("\nList of Guests:\n");
                for (Guest guest : guests) 
                {
                    System.out.println(guest);
                    
                }
        
        System.out.println("Enter the name of the guest to edit");
        String name = scanner.nextLine();
        
        Guest editGuest = null;
        
        for(Guest guest : guests) {
            if(guest.getFirstName().equalsIgnoreCase(name) || 
                    guest.getLastName().equalsIgnoreCase(name)) {
                editGuest = guest;
                break;
            }
        }
        if (editGuest == null) {
            System.out.println("Guest not found. Please enter a valid name");
            return;
        }
        
        System.out.println("Guest found...");
        System.out.print(editGuest);
        System.out.println("\n1. change phone number +"
                + "\n2. change email address");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch(choice) {
            case 1: 
                System.out.println("Enter the new phone number");
                String newPhone = scanner.nextLine();
                editGuest.setPhone(newPhone);
                break;
            case 2: 
                System.out.println("Enter the new email address");
                String newEmail = scanner.nextLine();
                editGuest.setEmail(newEmail);
                break;
            default: 
                System.out.println("Invalid choice");
                return;
        }
        Guest.writeGuests(guests);
        
        System.out.println("Guest has been updated successfully");
             
    }
}

    

