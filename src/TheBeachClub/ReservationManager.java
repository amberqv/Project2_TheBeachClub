/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBeachClub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author triciaamber
 */
public class ReservationManager {
    
    // Method to get available rooms for a given date range
    public List<Integer> getAvailableRooms(Connection connection, Date checkInDateObj, Date checkOutDateObj) throws SQLException {
        List<Integer> availableRooms = new ArrayList<>();
        
        try {
            // Convert the Date objects to strings
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String checkInDate = dateFormat.format(checkInDateObj);
            String checkOutDate = dateFormat.format(checkOutDateObj);
            
            String query = "SELECT Room.ROOM_ID FROM Room LEFT JOIN Reservation ON Room.ROOM_ID = Reservation.ROOM_ID "
             + "WHERE (Reservation.RES_CHECKOUT IS NULL OR Reservation.RES_CHECKOUT > ? OR Reservation.RES_CHECKIN < ?)";

            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, checkOutDate);
            statement.setString(2, checkInDate);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                availableRooms.add(resultSet.getInt("Room_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return availableRooms;
    }
    
    
    
}
