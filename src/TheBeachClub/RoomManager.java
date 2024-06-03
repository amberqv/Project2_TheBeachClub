
package TheBeachClub;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomManager {
    
    
        // Add New Room
        public static void addNewRoom(ArrayList<Room> rooms, Scanner scanner) 
        {
            
            System.out.println(" Adding New Room...\n");
            
            // Display existing room IDs
                    System.out.println("\nCurrently existing Room IDs: \n");
                    for (Room room : rooms) 
                    {
                        System.out.println(" ⛱ Room "+room.getId());
                    }
            
            
            int id, floor, capacity;
            String type, description;
            double price;

            // Room ID
            while (true) 
            {
                try 
                {
                    System.out.print("\nଳ Add new room ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    
                    if (isRoomIdExists(rooms, id)) 
                    {
                        System.out.println("Room ID already exists. Please enter a different ID.");
                        continue; // Ask user to enter a different ID
                    }

                    break; // Exit the loop if input is successfully read
                    
                    
                } 
                
                catch (InputMismatchException e) 
                {
                    System.out.println("Invalid input format for room ID. Please enter a valid integer.");
                    scanner.nextLine(); // Consume invalid input
                }
                
            }
            

            // Floor
            while (true) {
                try {
                    System.out.print("Enter floor: ");
                    floor = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    break; // Exit the loop if input is successfully read
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Enter integer");
                    scanner.nextLine(); // Consume invalid input
                }
            }

            // Capacity
            while (true) {
                try {
                    System.out.print("Enter capacity: ");
                    capacity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    break; // Exit the loop if input is successfully read
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Enter integer.");
                    scanner.nextLine(); // Consume invalid input
                }
            }

            // Type
            System.out.print("Enter type: ");
            type = scanner.nextLine();

            // Description
            System.out.print("Enter description: ");
            description = scanner.nextLine();

            // Price
            while (true) {
                try {
                    System.out.print("Enter price: ");
                    price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character
                    break; // Exit the loop if input is successfully read
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Enter number");
                    scanner.nextLine(); // Consume invalid input
                }
            }

            // Create new room object and add to file
            Room room = new Room(id, floor, capacity, type, description, price);
            rooms.add(room);
            Room.writeRooms(rooms);

            System.out.println("\nRoom added successfully.");
            
            // Prompt the user to add another room or go back to the main menu
                    while (true) 
                    {
                    System.out.print("\n1. Add another Room\n2. Main Menu\n\n ଳ ");
                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (choice == 2) {
                            return; // Return to the main menu
                        } else if (choice == 1) {
                            addNewRoom(rooms, scanner); // Add another room
                            return;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        System.out.println(" ଳ ");
                        scanner.nextLine(); // Consume invalid input
                    }
                }
        }

        
        // Show All Rooms
        public static void showAllRooms(ArrayList<Room> rooms) 
        {

//            ArrayList<Room> rooms = Room.readRooms(); // Read rooms from file

            if (rooms.isEmpty()) 
            {
                System.out.println("No rooms available.");
            } 

            else 
            {
                System.out.println("\nList of rooms:\n");
                for (Room room : rooms) 
                {
                    System.out.println(room);
                }
            }
            
            System.out.println("ଳ Press Enter to go back to main menu...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); // Wait for Enter key press
        }
        
        
        // Edit a Room
        public static void editRoom(ArrayList<Room> rooms, Scanner scanner) 
        {
            
            int roomId;
            while (true) 
            {
                try 
                {
                    // Display existing room IDs
                    System.out.println("\n——— Existing Room IDs: ———\n");
                    for (Room room : rooms) 
                    {
                        System.out.println(" ⛱ Room "+room.getId());
                    }
                    
                    System.out.print("\nEnter the ID of the room you want to edit"
                            + "\n'x' to cancel: "
                            +"\n ଳ ");
            
                    
                    String input = scanner.nextLine().trim();
                    
                    if (input.equalsIgnoreCase("x")) {
                        System.out.println("\nCancelling edit operation...");
                        return; // Cancel editing
                    }
                   

                    roomId = Integer.parseInt(input);
                    
                    // Find the room with the given ID
                    Room roomToEdit = null;
                    for (Room room : rooms) 
                    {
                        if (room.getId() == roomId) 
                        {
                            roomToEdit = room;
                            break;
                        }
                    }

                    if (roomToEdit == null) {
                        System.out.println("Room " + roomId + " not found.");
                        continue; // Prompt for room ID again
                    }
                    

                    System.out.println("\nNow editing:\n");
                    System.out.println(roomToEdit);

                    // Gather new room details
                    while (true) 
                    {
                        try {
                            System.out.print("Enter new floor (or press Enter to skip): ");
                            String floorInput = scanner.nextLine();
                            if (!floorInput.isEmpty()) {
                                int newFloor = Integer.parseInt(floorInput);
                                roomToEdit.setFloor(newFloor);
                            }
                            break; // Exit the loop if floor input is successfully read
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input format. Please enter a valid integer for floor.");
                        }
                    }

                    while (true) 
                    {
                        try {
                            System.out.print("Enter new capacity (press Enter to skip): ");
                            String capacityInput = scanner.nextLine();
                            if (!capacityInput.isEmpty()) {
                                int newCapacity = Integer.parseInt(capacityInput);
                                roomToEdit.setCapacity(newCapacity);
                            }
                            break; // Exit the loop if capacity input is successfully read
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input format. Please enter a valid integer for capacity.");
                        }
                    }

                    System.out.print("Enter new type (press Enter to skip): ");
                    String newType = scanner.nextLine();
                    if (!newType.isEmpty()) {
                        roomToEdit.setType(newType);
                    }

                    System.out.print("Enter new description (press Enter to skip): ");
                    String newDescription = scanner.nextLine();
                    if (!newDescription.isEmpty()) {
                        roomToEdit.setDescription(newDescription);
                    }

                    while (true) {
                        try {
                            System.out.print("Enter new price (press Enter to skip): ");
                            String priceInput = scanner.nextLine();
                            if (!priceInput.isEmpty()) {
                                double newPrice = Double.parseDouble(priceInput);
                                roomToEdit.setPrice(newPrice);
                            }
                            break; // Exit the loop if price input is successfully read
                        } 
                        
                        catch (NumberFormatException e) 
                        {
                            System.out.println("Invalid input format. Please enter a valid number for price.");
                        }
                    }
                    
                    
                    // Write updated rooms to file
                    System.out.println("\n——— ⛱ " + "Room "+ roomId +" updated successfully ⛱ ——— \n");
                    Room.writeRooms(rooms); 

                    
                    // Prompt the user to edit another room or go back to the main menu
                    while (true) 
                    {
                        try {
                            System.out.print("\n1. Edit another room\n2. Main Menu \nଳ  ");
                            int choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            if (choice == 2) {
                                System.out.println("\nGoing back to the main menu...");
                                return; // Return to the main menu
                            } else if (choice == 1) {
                                System.out.println("\nEditing another room...");
                                break; // Continue editing another room
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input format.");
                            scanner.nextLine(); // Consume invalid input
                        }
                    }
                    
                }
                catch (InputMismatchException e) 
                    {
                        System.out.println("Invalid input format.");
                        scanner.nextLine(); // Consume invalid input
                    }
            }
    }
        
        // Delete a Room
        public static void deleteRoom(ArrayList<Room> rooms, Scanner scanner) {
            System.out.println("\n⛱ Delete a Room ⛱\n");

            // Display existing room IDs
            System.out.println("Existing Room IDs: ");
            for (Room room : rooms) {
                System.out.println(" ⛱ Room " + room.getId());
            }

            // Prompt user for the room ID to delete
            System.out.print("\nEnter the ID of the room you want to delete"
                             + "\n'x' to cancel: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("x")) {
                System.out.println("\nCancelling delete operation...");
                return; // Cancel deleting
            }

            int roomIdToDelete;
            try {
                roomIdToDelete = Integer.parseInt(input);

                // Find the room with the given ID
                Room roomToDelete = null;
                for (Room room : rooms) {
                    if (room.getId() == roomIdToDelete) {
                        roomToDelete = room;
                        break;
                    }
                }
                

                if (roomToDelete == null) {
                    System.out.println("Room with ID " + roomIdToDelete + " not found.");
                } else {
                    // Remove the room from the list
                    rooms.remove(roomToDelete);
                    // Write updated rooms to file
                    Room.writeRooms(rooms);
                    System.out.println("\nRoom " + roomIdToDelete + " deleted successfully.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter a valid integer.");
            }
        }

        
        
        
        
        
        // Check if room ID already exists
            public static boolean isRoomIdExists(ArrayList<Room> rooms, int roomId) 
            {
                
                for (Room room : rooms) 
                {
                    if (room.getId() == roomId) 
                    {
                        return true;
                    }
                }
                return false;
            }
    

}
