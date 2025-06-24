package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class homepage extends JFrame {
    private JFrame frame = this;
    private Color c = new Color(240, 248, 255, 200); // Semi-transparent
    private Color tgc = new Color(200, 230, 255);
    private BackgroundImagePanel bgPanel;

    public homepage(String title) {
        setTitle(title);
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        try {
            // Use relative path or resource loading
            bgPanel = new BackgroundImagePanel(
                    "/Users/mezbahuddinaqib/Downloads/EXPENSE_MANAGEMENT_SYSTEM.java-master/src/gui/Flux_Dev_Vibrant_cash_background_with_a_mesmerizing_mosaic_of__3.jpg"
            );
        } catch (IOException e) {
            e.printStackTrace();
            bgPanel = new BackgroundImagePanel(); // Fallback
        }

        bgPanel.setLayout(null);
        setContentPane(bgPanel);

        addhomepageGuiComponents();
        setVisible(true);
    }

    public void addhomepageGuiComponents() {
        // Create a semi-transparent overlay panel
        JPanel overlay = new JPanel(null);
        overlay.setOpaque(false);
        overlay.setBounds(0, 0, 700, 600);
        bgPanel.add(overlay);

        JLabel l1 = new JLabel("WELCOME TO EASY MONEY MANAGER !");
        l1.setForeground(new Color(48, 25, 52)); // White text for better contrast
        l1.setFont(new Font("Segoe UI", Font.BOLD, 24));

// Create a semi-transparent background panel for the label
        JPanel labelBackground = new JPanel();
        labelBackground.setBackground(new Color(52, 152, 219, 150)); // Semi-transparent blue
        labelBackground.setBounds(100, 0, 500, 60); // Slightly larger than the label
        labelBackground.setLayout(new GridBagLayout()); // Center the label

// Add the label to the background panel
        labelBackground.add(l1);

// Add the background panel to your overlay
        overlay.add(labelBackground);

        AnimatedButton adminloginbutton = new AnimatedButton("Admin Login");
        AnimatedButton employeeloginbutton = new AnimatedButton("Employee Login");
        AnimatedButton registerbutton = new AnimatedButton("Register");
        AnimatedButton aboutbutton = new AnimatedButton("About");

        styleButton(adminloginbutton, 130);
        styleButton(employeeloginbutton, 230);
        styleButton(registerbutton, 330);
        styleButton(aboutbutton, 430);

        overlay.add(adminloginbutton);
        overlay.add(employeeloginbutton);
        overlay.add(registerbutton);
        overlay.add(aboutbutton);

        adminloginbutton.addActionListener(e -> {
            new adminloginpage();
            dispose();
        });

        registerbutton.addActionListener(e -> {
            new registerpage();
            dispose();
        });

        employeeloginbutton.addActionListener(e -> {
            new employeeloginpage();
            dispose();
        });
        aboutbutton.addActionListener(e -> {
             new ABOUT_WINDOW();
             dispose();
        });
    }


    private void styleButton(AnimatedButton button, int y) {
        button.setBounds(240, y, 200, 50);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setGradientColors(
                new Color(52, 152, 219),
                new Color(41, 128, 185)
        );
    }

    // Custom Background Panel
    static class BackgroundImagePanel extends JPanel {
        private BufferedImage image;

        public BackgroundImagePanel() {
            // Create fallback gradient background
            image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250), 800, 600, new Color(70, 130, 180));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, 800, 600);
            g2d.dispose();
        }

        public BackgroundImagePanel(String path) throws IOException {
            image = ImageIO.read(new java.io.File(path));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Animated Button with Gradient and Effects
    static class AnimatedButton extends JButton {
        private Timer animationTimer;
        private float animationProgress = 0f;
        private boolean hoverState = false;
        private Color color1, color2;
        private final List<Ripple> ripples = new ArrayList<>();
        private Timer rippleTimer;

        public AnimatedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setOpaque(false);

            // Mouse enter/exit animations
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hoverState = true;
                    startAnimation();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hoverState = false;
                    startAnimation();
                }
            });

            // Click ripple effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    addRipple(e.getPoint());
                }
            });

            // Animation timer for hover effect
            animationTimer = new Timer(16, e -> {
                if (hoverState) {
                    animationProgress = Math.min(1f, animationProgress + 0.1f);
                } else {
                    animationProgress = Math.max(0f, animationProgress - 0.1f);
                }

                if ((hoverState && animationProgress >= 1f) ||
                        (!hoverState && animationProgress <= 0f)) {
                    animationTimer.stop();
                }
                repaint();
            });

            // Ripple effect timer
            rippleTimer = new Timer(16, e -> {
                boolean needsRepaint = false;
                for (int i = ripples.size() - 1; i >= 0; i--) {
                    Ripple ripple = ripples.get(i);
                    ripple.update();
                    if (ripple.isDone()) {
                        ripples.remove(i);
                    }
                    needsRepaint = true;
                }
                if (needsRepaint) {
                    repaint();
                }
                if (ripples.isEmpty()) {
                    rippleTimer.stop();
                }
            });
        }

        public void setGradientColors(Color color1, Color color2) {
            this.color1 = color1;
            this.color2 = color2;
        }

        private void startAnimation() {
            if (!animationTimer.isRunning()) {
                animationTimer.start();
            }
        }

        private void addRipple(Point point) {
            ripples.add(new Ripple(point.x, point.y));
            if (!rippleTimer.isRunning()) {
                rippleTimer.start();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Animated gradient background
            float progress = animationProgress * 0.5f + 0.5f;
            Color startColor = interpolateColor(color1, color2, progress);
            Color endColor = interpolateColor(color2, color1, progress);

            GradientPaint gp = new GradientPaint(0, 0, startColor, width, height, endColor);
            g2.setPaint(gp);
            g2.fill(new RoundRectangle2D.Float(0, 0, width, height, 25, 25));

            // Draw border
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(255, 255, 255, 100));
            g2.drawRoundRect(1, 1, width-3, height-3, 25, 25);

            // Draw ripples
            for (Ripple ripple : ripples) {
                ripple.draw(g2);
            }

            // Draw text
            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            String text = getText();
            int x = (width - fm.stringWidth(text)) / 2;
            int y = (height - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(text, x, y);

            g2.dispose();
        }

        private Color interpolateColor(Color c1, Color c2, float ratio) {
            int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
            int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
            int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
            return new Color(r, g, b);
        }

        // Ripple effect class
        class Ripple {
            private float x, y;
            private float radius;
            private float alpha;

            public Ripple(int x, int y) {
                this.x = x;
                this.y = y;
                this.radius = 5;
                this.alpha = 0.8f;
            }

            public void update() {
                radius += 4;
                alpha -= 0.05f;
            }

            public boolean isDone() {
                return alpha <= 0;
            }

            public void draw(Graphics2D g2) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.setColor(new Color(255, 255, 255, 150));
                g2.fill(new Ellipse2D.Float(x - radius, y - radius, radius * 2, radius * 2));
                g2.setComposite(AlphaComposite.SrcOver);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new homepage("Expense Manager"));
    }
}