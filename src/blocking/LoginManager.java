package blocking;

public class LoginManager {
    private static boolean loggedIn = false;
    private static String username;

    // I-check kung naka-login na
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    // I-set kapag nag-login
    public static void login(String user) {
        loggedIn = true;
        username = user;
    }

    // I-call kapag nag-logout
    public static void logout() {
        loggedIn = false;
        username = null;
    }

    public static String getUsername() {
        return username;
    }
}
