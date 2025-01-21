
import salesforcecrmapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class crudTest {
    
    //*************Creating Leads*************
    public void createLead(String name, String email, String phone, String company) {
        //Validating inputs
        if (!validations.validateRequiredField(name, "Name") ||
        !validations.validateRequiredField(company, "Company Name") ||
        !validations.validateEmail(email) ||
        !validations.validatePhone(phone)) {
        return;
    }
        //creating lead in DB
        String query = "INSERT INTO leads (name, email, phone, company) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, company);
            stmt.executeUpdate();
            System.out.println("Lead created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
   //*************Reading Leads*************
   public void readLeads(){
       String query = "SELECT * FROM leads";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery(query)) {
        
        //getting & printing leads
        while (resultSet.next()) {
            int id = resultSet.getInt("id"); 
            String name = resultSet.getString("name"); 
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String company = resultSet.getString("company");
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("Company: " + company);
            System.out.println("--------------");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
   }
   
   //*************Updateing Leads************* 
   public void updateLead (int id, String name, String email, String phone, String company) {
   
    String query = "UPDATE leads SET name = ?, email = ?, phone = ?, company = ? WHERE id = ?";
    String checkQuery = "SELECT count(*) from leads where id = ?";

    try (
        Connection connection = DatabaseConnection.getConnection(); // Get the database connection
        PreparedStatement preparedStatement = connection.prepareStatement(query); // Prepare the SQL query
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);    
    ) {
            
        //check for ID
            checkStatement.setInt(1, id);
            ResultSet resultSet = checkStatement.executeQuery();
            if(resultSet.next() && resultSet.getInt(1)==0){
                System.out.println("No lead found with given ID: " + id);
                        return;
            }
        
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, company);
        preparedStatement.setInt(5, id);

        int rowsAffected = preparedStatement.executeUpdate();
        
        // Check if the update was successful
        if (rowsAffected > 0) {
            System.out.println("Lead updated successfully!");
        } else {
            System.out.println("No lead found with the given ID.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   
   //*************Deleting Leads*************
   public void deleteLead(int id) {
    
    String checkQuery = "SELECT COUNT(*) FROM leads WHERE id = ?";
    String deleteQuery = "DELETE FROM leads WHERE id = ?";

    try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)
    ) {
        // Check if the ID exists
        checkStatement.setInt(1, id);
        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(1) == 0) {
            System.out.println("No lead found with the given ID: " + id);
            return;
        }

        // Proceed with the deletion if the ID exists
        deleteStatement.setInt(1, id);
        int rowsAffected = deleteStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Lead deleted successfully!");
        } else {
            System.out.println("Failed to delete the lead. Please try again.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
}
