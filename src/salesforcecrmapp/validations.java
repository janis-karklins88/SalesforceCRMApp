import java.util.regex.*;

public class validations {
    
    public static boolean validateRequiredField(String field, String fieldName) {
    if (field == null || field.isEmpty()) {
        System.out.println(fieldName + " cannot be empty.");
        return false;
    }
    return true;
}

    public static boolean validateEmail(String email) {
    if (email != null && !email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        System.out.println("Invalid email format.");
        return false;
    }
    return true;
}

    public static boolean validatePhone(String phone) {
    if (phone != null && !phone.isEmpty() && !phone.matches("\\d+")) {
        System.out.println("Phone number must contain only digits if provided.");
        return false;
    }
    return true;
}

 
}
