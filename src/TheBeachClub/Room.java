/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBeachClub;

/**
 *
 * @author triciaamber
 */
public class Room {
    
    private int roomID;
    private int capacity;
    private String type;
    private Double price;

    public Room(int roomID, String roomType, int roomCapacity, double roomPrice) {
        this.roomID = roomID;
        this.capacity = roomCapacity;
        this.type = roomType;
        this.price = roomPrice;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}
