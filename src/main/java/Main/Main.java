package Main;

import view.LoginFrame;
import controller.LoginController;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel for better UI appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Initialize login frame and controller
                LoginFrame loginFrame = new LoginFrame();
                new LoginController(loginFrame);
                loginFrame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error starting application: " + e.getMessage(), 
                    "Startup Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
