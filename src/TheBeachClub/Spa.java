
package TheBeachClub;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author triciaamber
 */
public class Spa {
    
    private int appointmentId;
    private Guest guest;
    private String service;
    private String time;
    private double price;

    public Spa(int appointmentId, Guest guest, String service, String time, double price) {
        this.appointmentId = appointmentId;
        this.guest = guest;
        this.service = service;
        this.time = time;
        this.price = price;
    }

    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * @param guest the guest to set
     */
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
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
    
    
    
        // Read spa appointments from file
        public static ArrayList<Spa> readAppointments(ArrayList<Guest> guests) {
        ArrayList<Spa> appointments = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("./resources/spaAppointments.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        int appointmentId = Integer.parseInt(parts[0].trim());
                        String guestFirstName = parts[1].trim();
                        String guestLastName = parts[2].trim();
                        String service = parts[3].trim();
                        String time = parts[4].trim();
                        double price = Double.parseDouble(parts[5].trim());

                        // Find the corresponding guest object from the provided list
                        Guest guest = null;
                        for (Guest g : guests) {
                            if (g.getFirstName().equals(guestFirstName) && g.getLastName().equals(guestLastName)) {
                                guest = g;
                                break;
                            }
                        }

                        if (guest != null) {
                            appointments.add(new Spa(appointmentId, guest, service, time, price));
                        } else {
                            System.err.println("Guest " + guestFirstName + " " + guestLastName + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing spa appointment data: " + line);
                    }
                } else {
                    System.err.println("Invalid spa appointment data: " + line);
                }
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return appointments;
    }

    
   
    // Write spa appointments to file
    public static void writeAppointments(ArrayList<Spa> appointments) 
    {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./resources/spaAppointments.txt"))) 
        
        {
            for (Spa appointment : appointments) 
            {
                pw.println(String.format("%d,%s,%s,%s,%s,%.2f",
                        appointment.getAppointmentId(), 
                        appointment.getGuest().getFirstName(),
                        appointment.getGuest().getLastName(),
                        appointment.getService(), 
                        appointment.getTime(), 
                        appointment.getPrice()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
        public String toString() 
        {
        return "—— ⛱ "
                + "Appointment " +appointmentId
                + " ———————————"
                + "\n\nName: " +  guest.getFirstName() 
                + " " + guest.getLastName() 
                + "\nService: " + service 
                + "\nTime: " + time
                + "\nTotal:" + price
                ;
        }
    
        
    
    
}
