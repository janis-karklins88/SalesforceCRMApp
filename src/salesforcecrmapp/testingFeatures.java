import salesforcecrmapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class testingFeatures {
    
    public static void checkUsername(String username){
    String query = "SELECT COUNT(*) FROM users WHERE username = ?";
    
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement checkStatement = connection.prepareStatement(query)
         ){
        
        checkStatement.setString(1, username);
        ResultSet resultSet = checkStatement.executeQuery();
                
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            System.out.println("Username already exist!");

        }
        else {
            System.out.println("No such username found!");
        }
    
}   catch (SQLException e) {
    e.printStackTrace();
}
    
    }
    
}
