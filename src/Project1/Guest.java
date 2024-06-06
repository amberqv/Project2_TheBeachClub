/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author sarak
 */
public class Guest {
    
    
    
    String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phone;
    
    public Guest(String firstName, String lastName, int age, String email, 
            String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public static boolean isValidAge(int age) {
        return age >= 18;
    }
    
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
        
    }
    
    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^(\\+[0-9]{1,3}[- ]?)?([0-9]{3}[- ]?)[0-9]{3}[- ]?[0-9]{4}$";
        return Pattern.matches(phoneRegex, phone);
    }
    
    
   
   // Read guests from file 
   public static ArrayList<Guest> readGuests() {
        ArrayList<Guest> guests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./resources/guests.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length == 5) {
                    String firstName = data[0];
                    String lastName = data[1];
                    int age = Integer.parseInt(data[2]);
                    String email = data[3];
                    String phone = data[4];
                    guests.add(new Guest(firstName, lastName, age, email, phone));
                } else if (data.length == 7) { // VIP guest
                    String firstName = data[0];
                    String lastName = data[1];
                    int age = Integer.parseInt(data[2]);
                    String email = data[3];
                    String phone = data[4];
                    int loyaltyPoints = Integer.parseInt(data[5]);
                    String VIPNumber = data[6];
                    guests.add(new VIP(firstName, lastName, age, email, phone, loyaltyPoints));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return guests;
    }  
   
    
   // Write guests to file
     public static void writeGuests(ArrayList<Guest> guests) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./resources/guests.txt"))) {
            for (Guest guest : guests) {
                if (guest instanceof VIP) {
                    VIP vip = (VIP) guest;
                    bw.write(vip.getFirstName() + ", " + vip.getLastName() + ", " +
                            vip.getAge() + ", " + vip.getEmail() + ", " + vip.getPhone() + ", " +
                            vip.getLoyaltyPoints() + ", " + vip.getVIPNumber());
                } else {
                    bw.write(guest.getFirstName() + ", " + guest.getLastName() + ", " +
                            guest.getAge() + ", " + guest.getEmail() + ", " + guest.getPhone());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override 
    public String toString() {
        return "—— ⛱  "
                + firstName + " " + lastName + " "
                + " —————————————"
                + "\nAge: " + getAge() 
                + "\nEmail Address: " + getEmail() 
                + "\nPhone Number: " + getPhone()
                + "\n";
    }
  
}
