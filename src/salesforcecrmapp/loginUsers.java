import salesforcecrmapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class loginUsers {
    public boolean loginUser(String username, String password) {
        
    // Field validation
        if (!validations.validateRequiredField(username, "Username") || 
            !validations.validateRequiredField(password, "Password")) {
            return false;
        }
        
    String query = "SELECT password FROM users WHERE username = ?";

    try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)
    ) {
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String hashedPassword = resultSet.getString("password");

            // Verify the password
            if (BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified) {
            System.out.println("Login successful!");
            return true;
}
            else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("User not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
   

}
