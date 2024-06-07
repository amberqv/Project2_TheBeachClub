/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package TheBeachClub;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author triciaamber
 */
public class ReservationUI extends javax.swing.JPanel {

    /**
     * Creates new form ReservationUI
     */
    public ReservationUI() {
        initComponents();
        
        // Method
        populateResTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        resTable = new javax.swing.JTable();

        resTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Reservation ID", "Check In", "Check Out", "Room ID", "Guest ID"
            }
        ));
        jScrollPane1.setViewportView(resTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void populateResTable() {
    DefaultTableModel model = (DefaultTableModel) resTable.getModel();
    model.setRowCount(0); // Clear existing rows
    
    try {
        // Establish database connection
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT resID, res_checkIn, res_checkOut, ROOM_ID, guestID FROM reservation");
        
        while (resultSet.next()) {
            int resID = resultSet.getInt("resID");
            Date checkInDate = resultSet.getDate("res_checkIn");
            Date checkOutDate = resultSet.getDate("res_checkOut");
            int roomID = resultSet.getInt("ROOM_ID");
            int guestID = resultSet.getInt("guestID");
            
            model.addRow(new Object[]{resID, checkInDate, checkOutDate, roomID, guestID});
        }
        
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable resTable;
    // End of variables declaration//GEN-END:variables
}
