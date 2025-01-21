import salesforcecrmapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import at.favre.lib.crypto.bcrypt.BCrypt;


public class registerUsers {

    public void registerUser(String name, String password) {

        // Field validation
        if (!validations.validateRequiredField(name, "Username") || 
            !validations.validateRequiredField(password, "Password")) {
            return;
        }

        // Check if username exists
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery)
        ) {
            checkStatement.setString(1, name);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Username already exists: " + name);
                return;
            }
        } catch (SQLException e) {
            System.err.println("Error while checking username existence.");
            e.printStackTrace();
            return;
        }

        // Hash password and add user
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery)
        ) {
            insertStatement.setString(1, name);
            insertStatement.setString(2, hashedPassword);

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully: " + name);
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.err.println("Error while registering user.");
            e.printStackTrace();
        }
    }
}
