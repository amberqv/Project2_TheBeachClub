/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBeachClub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author triciaamber
 */

public class GuestManager {
    
    
    private static final String URL = "jdbc:derby://localhost:1527/thebeachclubDB";
//    private static final String USERNAME = ""; // Your username
//    private static final String PASSWORD = ""; // Your password


        // Method to add a guest to the database
        public static void addGuest(Guest guest) {
            try (Connection connection = DriverManager.getConnection(URL)) {
                
                        connection.setAutoCommit(false); // Disable auto-commit mode

                String query = "INSERT INTO GUEST (guestID, guest_firstName, guest_lastName, guest_email, guest_phone) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, guest.getGuestID());
                statement.setString(2, guest.getFirstName());
                statement.setString(3, guest.getLastName());
                statement.setString(4, guest.getEmail());
                statement.setString(5, guest.getPhone());
                
                
                statement.executeUpdate();
                
                connection.commit(); // Commit the transaction
        
        // Re-enable auto-commit mode
        connection.setAutoCommit(true);
        
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // Method to update a guest in the database
        public static void updateGuest(Guest guest) {
            try (Connection connection = DriverManager.getConnection(URL)) {
                String query = "UPDATE Guest SET guest_firstName=?, guest_lastName=?, guest_email=?, guest_phone=? WHERE guest_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, guest.getFirstName());
                statement.setString(2, guest.getLastName());
                statement.setString(3, guest.getEmail());
                statement.setString(4, guest.getPhone());
                statement.setInt(5, guest.getGuestID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to delete a guest from the database
        public static void deleteGuest(int guestId) {
            try (Connection connection = DriverManager.getConnection(URL)) {
                String query = "DELETE FROM Guest WHERE guest_id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, guestId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//
//        // Method to retrieve all guests from the database
//        public static ArrayList<Guest> getAllGuest() {
//            ArrayList<Guest> guest = new ArrayList<>();
//            try (Connection connection = DriverManager.getConnection(URL)) {
//                String query = "SELECT * FROM Guest";
//                PreparedStatement statement = connection.prepareStatement(query);
//                ResultSet resultSet = statement.executeQuery();
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("guest_id");
//                    String firstName = resultSet.getString("guest_firstName");
//                    String lastName = resultSet.getString("guest_lastName");
//                    String email = resultSet.getString("guest_email");
//                    String phone = resultSet.getString("guest_phone");
//                    guest.add(new Guest(guestID, firstName, lastName, email, phone));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return guest;
//        }
}