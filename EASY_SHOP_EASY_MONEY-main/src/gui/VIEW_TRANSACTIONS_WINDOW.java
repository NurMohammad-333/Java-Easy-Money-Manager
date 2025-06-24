package gui;

import controller.database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VIEW_TRANSACTIONS_WINDOW extends JFrame {

    private JTable transactionTable;

    public VIEW_TRANSACTIONS_WINDOW() {
        setTitle("All Transactions");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initTable();
        loadTransactions();

        setVisible(true);
    }

    private void initTable() {
        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTransactions() {
        String[] columnNames = {"Type", "Category","quantity", "Amount","currency", "Date", "Notes", "Name", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactions ORDER BY date DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[9];

                row[0] = rs.getString("type");
                row[1] = rs.getString("category");
                row[2] = rs.getInt("quantity");
                row[3] = rs.getDouble("amount");
                row[4] = rs.getString("currency");
                row[5] = rs.getDate("date");
                row[6] = rs.getString("notes");
                row[7] = rs.getString("name");
                row[8] = rs.getString("status");
                model.addRow(row);
            }

            transactionTable.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VIEW_TRANSACTIONS_WINDOW());
    }
}
