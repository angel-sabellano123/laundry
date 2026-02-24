package config;

public class UserSession {

    private static UserSession instance;

    private String username;
    private String role;
    private String fullName;

    // Private constructor ensures singleton pattern
    private UserSession() { }

    // Get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set all user info at once (login)
    public void setUser(String username, String role, String fullName) {
        this.username = username;
        this.role = role;
        this.fullName = fullName;
    }

    // Setters for individual fields
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    // Clear session (for logout)
    public void clearSession() {
        instance = null;
    }
}
