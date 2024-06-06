package Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


    public class Room 
    
    {
        private int id;
        private int floor;
        private int capacity;
        private String type;
        private String description;
        private double price;
        private Map<LocalDate, Boolean> bookedDates; // Store check-in and check-out dates
        private Map<LocalDate, Boolean> newBookingDates; // Track new booking dates separately
        

        public Room(int id, int floor, int capacity, String type, String description, double price) 
        {
            this.id = id;
            this.floor = floor;
            this.capacity = capacity;
            this.type = type;
            this.description = description;
            this.price = price;
            this.bookedDates = new HashMap<>();
            this.newBookingDates = new HashMap<>();
        }
        
        
    
        // Getters & Setters

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return the floor
         */
        public int getFloor() {
            return floor;
        }

        /**
         * @param floor the floor to set
         */
        public void setFloor(int floor) {
            this.floor = floor;
        }

        /**
         * @return the capacity
         */
        public int getCapacity() {
            return capacity;
        }

        /**
         * @param capacity the capacity to set
         */
        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the price
         */
        public double getPrice() {
            return price;
        }

        /**
         * @param price the price to set
         */
        public void setPrice(double price) {
            this.price = price;
        }
        
        
        
        public void bookRoom(LocalDate checkIn, LocalDate checkOut) 
        {
            LocalDate date = checkIn;
            while (!date.isAfter(checkOut)) 
            {
                // If the date is not already booked, add it to the bookedDates map
                if (!bookedDates.containsKey(date)) {
                    bookedDates.put(date, true);
                }
                date = date.plusDays(1);
            }
        }
        
        
    public List<LocalDate> getBookedDates() 
    {
    List<LocalDate> allBookedDates = new ArrayList<>(bookedDates.keySet());
    allBookedDates.addAll(new ArrayList<>(newBookingDates.keySet()));
    return allBookedDates;
    }


    // Method to remove booking dates for a canceled reservation
    public void cancelBooking(LocalDate checkInDate, LocalDate checkOutDate) {
        // Iterate through the booked dates within the provided range
        LocalDate date = checkInDate;
        while (!date.isEqual(checkOutDate.plusDays(1))) {
            // Remove the date from both bookedDates and newBookingDates maps if it exists
            bookedDates.remove(date);
            newBookingDates.remove(date);
            date = date.plusDays(1);
        }
    }



    // Method to check if the room is available for the specified date range
    public boolean isRoomAvailable(LocalDate checkInDate, LocalDate checkOutDate) 
    {
        LocalDate date = checkInDate;
        while (!date.isAfter(checkOutDate)) 
        {
            if (bookedDates.containsKey(date) || newBookingDates.containsKey(date)) 
            {
                return false; // Room is not available for at least one date in the range
            }
            date = date.plusDays(1);
        }
        return true; // Room is available 
    }

    
    
    // Method to add new booking dates
    public void addNewBookingDate(LocalDate date) 
    {
        newBookingDates.put(date, true);
    }

    // Method to get the map of new booking dates
    public Map<LocalDate, Boolean> getNewBookingDates() 
    {
        return newBookingDates;
    }
        
        
    
        // File I/O for Room

        public static ArrayList<Room> readRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("./resources/rooms.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                // Split content of a line by using a comma
                String[] parts = line.split(",");
                if (parts.length >= 6) { // Ensure the line has at least the basic room information
                    try {
                        // Parse id, floor, capacity to int
                        int id = Integer.parseInt(parts[0].trim());
                        int floor = Integer.parseInt(parts[1].trim());
                        int capacity = Integer.parseInt(parts[2].trim());
                        String type = parts[3].trim();
                        String description = parts[4].trim();
                        double price = Double.parseDouble(parts[5].trim());

                        // Create a Room object
                        Room room = new Room(id, floor, capacity, type, description, price);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        for (int i = 6; i < parts.length; i++) {
                            LocalDate date = LocalDate.parse(parts[i].trim(), formatter);
                            room.addNewBookingDate(date);
                        }

                        // Add the room to the list
                        rooms.add(room);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing room data: " + line);
                    }
                } else {
                    System.err.println("Invalid room data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rooms;
    }

      
        

    public static void writeRooms(ArrayList<Room> rooms) 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter("./resources/rooms.txt"))) {
            for (Room room : rooms) {
                StringBuilder roomInfo = new StringBuilder();
                roomInfo.append(room.getId()).append(", ")
                        .append(room.getFloor()).append(", ")
                        .append(room.getCapacity()).append(", ")
                        .append(room.getType()).append(", ")
                        .append(room.getDescription()).append(", ")
                        .append(room.getPrice());

                // Append existing booked dates
                for (LocalDate date : room.getBookedDates()) {
                    roomInfo.append(", ")
                            .append(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }

                // Append new booking dates (if any)
                for (LocalDate date : room.getNewBookingDates().keySet()) {
                    if (!room.getBookedDates().contains(date)) {
                        roomInfo.append(", ")
                                .append(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    }
                }

                writer.println(roomInfo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    
        @Override
        public String toString() 
        {
            return "—— ⛱ "
                    + "Room " + id 
                    + " ——————————————————"
                    + "\n\nFloor: " + floor 
                    + "\nOccupancy: " + capacity 
                    + "\nType: " + type 
                    + "\nDescription: " + description 
                    + "\nPrice: " + price + "\n";
        }

    
    

}