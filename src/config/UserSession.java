package config;

public class UserSession {

    private static UserSession instance;

    private String username;
    private String role;
    private String fullName;

    private UserSession() {
        // private constructor
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // setters
    public void setUser(String username, String role, String fullName) {
        this.username = username;
        this.role = role;
        this.fullName = fullName;
    }

    // getters
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    // clear session (for logout)
    public void clearSession() {
        instance = null;
    }
}

