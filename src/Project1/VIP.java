
package Project1;

import Project1.Guest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author sarak
 */
public class VIP extends Guest {
    
    private int loyaltyPoints; 
    private String VIPNumber; 
    
    private static final Set<String> vipNumbers = new HashSet<>();
    
    
    public VIP(String firstName, String lastName, int age, String email, 
            String phone, int loyaltyPoints) 
    {
        super(firstName, lastName, age, email, phone);
        this.loyaltyPoints = loyaltyPoints; 
        this.VIPNumber = generateVIPNumber();
        
        
    }

    /**
     * @return the loyaltyPoints
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * @param loyaltyPoints the loyaltyPoints to set
     */
    public void setLoyaltyPoints(int loyaltyPoints) 
    {
        this.loyaltyPoints = loyaltyPoints;
    }

    /**
     * @return the VIPNumber
     */
    public String getVIPNumber() 
    {
        return VIPNumber;
    }

    /**
     * @param VIPNumber the VIPNumber to set
     */
    public void setVIPNumber(String VIPNumber) 
    {
        this.VIPNumber = VIPNumber;
    }
    
    
     public final String generateVIPNumber() 
     {
        String vipNumber;
        do {
            // Generate a random 6-digit number
            Random random = new Random();
            int randomNumber = random.nextInt(900000) + 100000;
            vipNumber = "BC" + randomNumber;
        } while (!vipNumbers.add(vipNumber)); // Ensure uniqueness by checking against existing VIP numbers

        return vipNumber;
    }
     
     
    private boolean rewardGiven = false;
     
    public void reservationReward() 
    {
        if (!rewardGiven) {
            this.loyaltyPoints += 100;
            System.out.println("100 loyalty points rewarded for making a reservation!");
            System.out.println("Current points is now: " + this.loyaltyPoints);
            rewardGiven = true;

            // Update loyalty points in the guests list
            ArrayList<Guest> guests = Guest.readGuests();
            for (Guest guest : guests) {
                if (guest instanceof VIP && guest.getFirstName().equals(this.getFirstName()) && guest.getLastName().equals(this.getLastName())) {
                    ((VIP) guest).setLoyaltyPoints(this.loyaltyPoints);
                    break;
                }
            }

            // Write the updated guests list to file
            Guest.writeGuests(guests);
        } 
        else 
        {
            System.out.println("100 loyalty points have already been rewarded for making a reservation.");
        }
    }


    
    public boolean VIPRoomDiscount(double roomRate) 
    {
        if (loyaltyPoints >= 200) 
        {
            double discountedAmount = roomRate * 0.15; // Calculate the discount amount
            roomRate -= discountedAmount; // Apply the discount to the room rate
            loyaltyPoints -= 200; // Deduct loyalty points for applying the discount

            System.out.println("Discount of 15% applied (-200 points).");
            System.out.println("New total Loyalty Points Balance for " + getFirstName() + " " + getLastName() + ": " + loyaltyPoints);

            // Update loyalty points in guests.txt
            ArrayList<Guest> guests = Guest.readGuests();
            for (Guest guest : guests) {
                if (guest instanceof VIP) {
                    VIP vipGuest = (VIP) guest;
                    if (vipGuest.getFirstName().equals(getFirstName()) && vipGuest.getLastName().equals(getLastName())) {
                        vipGuest.setLoyaltyPoints(loyaltyPoints);
                        break;
                    }
                }
            }
            Guest.writeGuests(guests);

            return true; // Discount applied successfully
        } 

        else 
        {
            System.out.println("Unfortunately you do not have enough Loyalty Points.");
            System.out.println("Your current balance is: " + loyaltyPoints);
            return false; // Not enough loyalty points to apply the discount
        }
    }


 
    public boolean useSpa() 
    {
        if (loyaltyPoints >= 80) 
        {
            loyaltyPoints -= 80; 
            System.out.println("You are all booked in for the Spa! Enjoy.");
            System.out.println("Loyalty points balance: " + loyaltyPoints);
            
            return true; 
        } else {
            System.out.println("Unfortunately you do not have enough Loyalty Points.");
            System.out.println("Your current balance is: " + loyaltyPoints);
            return false;
        }
    }
    
    
    
    public void deductLoyaltyPoints(int points) {
        if (points >= 0 && points <= loyaltyPoints) {
            loyaltyPoints -= points;
        } else {
            System.out.println("Invalid loyalty points deduction amount.");
        }
    }
    
    
    
        @Override 
        public String toString() 
        {
             return "—— ⛱  "
                    + getFirstName() + " " + getLastName() + " "
                    + " —————————————"
                    + "\nAge: " + getAge() 
                    + "\nEmail Address: " + getEmail() 
                    + "\nPhone Number: " + getPhone()
                    + "\nVip Number: " + getVIPNumber()
                    + "\nLoyalty Points: " + getLoyaltyPoints()
                    + "\n";
        }
              
}
