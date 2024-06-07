/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBeachClub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author triciaamber
 */
public class RoomManager {
    
    public static Room getRoomByID(Connection connection, int roomID) throws SQLException {
        Room room = null;
        String query = "SELECT * FROM Room WHERE ROOM_ID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int id = resultSet.getInt("Room_ID");
                String type = resultSet.getString("room_type");
                int capacity = resultSet.getInt("room_capacity");
                double price = resultSet.getDouble("room_price");
                
                room = new Room(id, type, capacity, price);
            }
        }
        
        return room;
    }
    
}
