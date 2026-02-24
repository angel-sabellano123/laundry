package blocking;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import laundry.login; // import ng login JFrame
import config.UserSession;

public class PageLauncher {

    public static Object PageLauncher;

    public static void launch(JFrame frame) {
        if (UserSession.getInstance().getUsername() == null) {
            // Walang naka-login
            JOptionPane.showMessageDialog(null, "Please login first!");
            login loginForm = new login();
            loginForm.setVisible(true);
        } else {
            // May naka-login na
            frame.setVisible(true);
        }
    }
}
