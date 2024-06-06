
package Project1;

import Project1.Guest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class Reservations {
    
    private int reservationsId;
    private LocalDate checkIn; 
    private LocalDate checkOut; 
    private double price;
    private String status;
    private Guest guest;
    private Room room;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public Reservations(int reservationsId, LocalDate checkIn, LocalDate checkOut, double price, String status, Guest guest, Room room) {
        this.reservationsId = reservationsId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.status = status;
        this.guest = guest;
        this.room = room;
    }

    // Getters and setters
    
    public int getReservationsId() {
        return reservationsId;
    }

    public void setReservationsId(int reservationsId) {
        this.reservationsId = reservationsId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCheckInToString() {
        return checkIn.format(formatter);
    }

    public String getCheckOutToString() {
        return checkOut.format(formatter);
    }
    

        public static ArrayList<Reservations> readReservations(ArrayList<Guest> guests, ArrayList<Room> rooms) {
        ArrayList<Reservations> reservations = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("./resources/reservations.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    try {
                        int reservationsId = Integer.parseInt(parts[0].trim()); // Read reservationsId
                        LocalDate checkIn = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        LocalDate checkOut = LocalDate.parse(parts[2].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        double price = Double.parseDouble(parts[3].trim());
                        String status = parts[4].trim();
                        String guestFirstName = parts[5].trim();
                        String guestLastName = parts[6].trim();
                        int roomId = Integer.parseInt(parts[7].trim());

                        Guest guest = null;
                        for (Guest g : guests) {
                            if (g.getFirstName().equals(guestFirstName) && g.getLastName().equals(guestLastName)) {
                                guest = g;
                                break;
                            }
                        }

                        Room room = null;
                        for (Room r : rooms) {
                            if (r.getId() == roomId) {
                                room = r;
                                break;
                            }
                        }

                        // Create and add Reservation object
                        if (guest != null && room != null) {
                            reservations.add(new Reservations(reservationsId, checkIn, checkOut, price, status, guest, room));
                        } else {
                            System.err.println("Guest or Room not found for reservation: " + line);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing reservation data: " + line);
                    }
                } else {
                    System.err.println("Invalid reservation data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }

        return reservations;

    }
    
    
    public static void writeReservations(ArrayList<Reservations> reservations) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./resources/reservations.txt"))) {
            for (Reservations reservation : reservations) {
                // Update the room ID to match the current state of the Room object
                int roomId = reservation.getRoom().getId();

                pw.println(String.format("%d,%s,%s,%.2f,%s,%s,%s,%d",
                        reservation.getReservationsId(), // Write reservationsId
                        reservation.getCheckIn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        reservation.getCheckOut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        reservation.getPrice(), reservation.getStatus(),
                        reservation.getGuest().getFirstName(), reservation.getGuest().getLastName(),
                        roomId)); // Write the updated room ID
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    @Override
        public String toString() 
        {
        return "—— ⛱ Reservation " + reservationsId + " ———————————\n" +
            "Check-in Date: " + checkIn.format(formatter) + "\n" +
            "Check-out Date: " + checkOut.format(formatter) + "\n" +
            "Price: " + price + "\n" +
            "Status: " + status + "\n" +
            "Guest First Name: " + guest.getFirstName() + "\n" +
            "Guest Last Name: " + guest.getLastName() + "\n" +
            "Room ID: " + room.getId() + "\n";
            }


}
