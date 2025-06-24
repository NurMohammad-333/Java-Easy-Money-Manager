package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import controller.database;

public class registerpage extends JFrame {

    public registerpage() {
        setTitle("Register");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        JLabel label = new JLabel("Create New Account");
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(new Color(52, 73, 94));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(60, 40, 300, 40);
        add(label);

        addregistradetails();
        setVisible(true);
    }

    public void addregistradetails() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(50, 100, 300, 300);
        formPanel.setBackground(new Color(52,152,219));
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(formPanel);

        JLabel username = new JLabel("Username");
        username.setFont(new Font("Segoe UI", Font.BOLD, 16));
        username.setForeground(new Color(33, 33, 33));
        username.setBounds(20, 10, 250, 25);
        formPanel.add(username);

        JTextField username1 = new JTextField();
        username1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        username1.setBounds(20, 35, 250, 30);
        username1.setBackground(new Color(245, 245, 245));
        username1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(username1);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("Segoe UI", Font.BOLD, 16));
        password.setForeground(new Color(33, 33, 33));
        password.setBounds(20, 75, 250, 25);
        formPanel.add(password);

        JPasswordField password1 = new JPasswordField();
        password1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        password1.setBounds(20, 100, 250, 30);
        password1.setBackground(new Color(245, 245, 245));
        password1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(password1);

        JButton submitbutton = new JButton("Register");
        submitbutton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitbutton.setBounds(20, 150, 250, 35);
        submitbutton.setBackground(new Color(52, 152, 219));
        submitbutton.setForeground(Color.GREEN);
        submitbutton.setFocusPainted(false);
        submitbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitbutton.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2)); // <-- Visible border

        submitbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitbutton.setBackground(new Color(41, 128, 185));
                submitbutton.setBorder(BorderFactory.createLineBorder(new Color(30, 100, 150), 2)); // darker border on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitbutton.setBackground(new Color(52, 152, 219));
                submitbutton.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2)); // original border
            }
        });
        formPanel.add(submitbutton);

        JButton backbutton = new JButton("Back");
        backbutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backbutton.setBounds(20, 200, 250, 30);
        backbutton.setBackground(new Color(230, 230, 230));
        backbutton.setForeground(Color.BLACK);
        backbutton.setFocusPainted(false);
        backbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backbutton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1)); // <-- Visible border
        formPanel.add(backbutton);

        backbutton.addActionListener(ex -> {
            new homepage("Expense Management System");
            dispose();
        });

        submitbutton.addActionListener(e -> {
            String uname = username1.getText();
            String pass = new String(password1.getPassword());

            if (uname.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection c = database.getConnection()) {
                String query = "INSERT INTO employee (username, password) VALUES (?, ?)";
                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1, uname);
                ps.setString(2, pass);

                int row = ps.executeUpdate();

                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "Successfully Registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new employeeloginpage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(registerpage::new);
    }
}
