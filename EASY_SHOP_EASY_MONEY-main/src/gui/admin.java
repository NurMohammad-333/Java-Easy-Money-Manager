
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.security.PrivateKey;
import javax.swing.border.Border;

//import static sun.tools.jconsole.inspector.XDataViewer.dispose;

public class admin {
     JFrame frame = new JFrame("Admin");
    BackgroundImagePanel panel = new BackgroundImagePanel("/Users/mezbahuddinaqib/Downloads/EXPENSE_MANAGEMENT_SYSTEM.java-master/src/gui/Flux_Dev_A_vibrant_abstract_animation_background_image_for_a_s_2.jpg");

    JPanel buttonPanel = new JPanel(new GridLayout(10, 1, 0, 0));

     JLabel title = new JLabel("<html><center>WELCOME TO ADMIN PANEL.<br>MANAGE EXPENSES WITH US.</center></html>");
    JButton[] buttons = {
            new JButton("Dashboard"),
            new JButton("Add Transaction"),
            new JButton("View Transactions"),
            new JButton("Manage Categories"),
            new JButton("Inventory"),
            new JButton("Settings"),
            new JButton("Logout"),
           new JButton("Exit"),


    };

    admin(String name, String post) {
        frame.setSize(700, 700);

        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        // Customize the label
        title.setOpaque(true);
        title.setBackground(new Color(0, 0, 0, 180)); // Semi-transparent background
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.GREEN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(600, 100));

        Border border = BorderFactory.createLineBorder(Color.RED, 3);
        title.setBorder(border);

        // Add label to panel (not to frame directly)
        panel.add(title, BorderLayout.NORTH);

        // Set background panel as the content pane
        frame.setContentPane(panel);

        buttonPanel.setOpaque(false);//to make it transparent
        for(JButton button : buttons) {
            button.setContentAreaFilled(false);// means i dont need any background color of buttons , which are white by default
            button.setOpaque(false);
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setBorder(BorderFactory.createLineBorder(Color.black, 2));//border around the button
            buttonPanel.add(button);

            // adds hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                // when cursor is pointed
                public void mouseEntered(MouseEvent evt) {
                    button.setForeground(Color.YELLOW); // Change text color on hover
                    button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2)); // Thicker yellow border
                }

                 // when cursor is removed , necessary bcz when pointed it will not revert again
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setForeground(Color.BLACK); // Revert text color
                    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Revert border
                }
            });
        }
        buttonPanel.setPreferredSize(new Dimension(270, 0));
        panel.add(buttonPanel, BorderLayout.WEST);
        frame.setContentPane(panel);
        buttons[0].addActionListener(e -> new DASHBOARD_WINDOW());
        buttons[1].addActionListener(e -> new ADD_TRANSACTIONS_WINDOW(name, post));
        buttons[2].addActionListener(e -> new VIEW_TRANSACTIONS_WINDOW());
        buttons[3].addActionListener(e -> new MANAGE_CATEGORIES_WINDOW());
        buttons[4].addActionListener(e -> new INVENTORY_WINDOW());
        buttons[5].addActionListener(e -> new SETTINGS_WINDOW());
        buttons[6].addActionListener(e -> {
            new homepage("Expense management portal");
            frame.dispose();  // properly closes the current admin window
        });

        buttons[7].addActionListener(e ->
               System.exit(0));

        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new admin("Admin", "Manager"));
    }



}
