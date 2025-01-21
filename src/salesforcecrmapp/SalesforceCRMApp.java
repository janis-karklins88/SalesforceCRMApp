import at.favre.lib.crypto.bcrypt.BCrypt;

public class SalesforceCRMApp {
    public static void main(String[] args) {
        loginUsers dao = new loginUsers();
        String username = "";
        String pass = "";
    boolean loginResult = dao.loginUser(username, pass);
    if (loginResult) {
        System.out.println("Welcome, " + username);
    } else {
        System.out.println("Login failed.");
    }
        
    }
}
