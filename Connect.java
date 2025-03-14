import java.sql.*;
import java.util.ArrayList;

public class Connect {
    static String url = "jdbc:mysql://localhost:3306/phonebook";
    static String username = "root";
    static String password = "root1234!";
    static String query;

    public static ArrayList<String> getContacts() {
        query = "SELECT * FROM contacts";
        ArrayList<String> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.add(rs.getInt("id") + " - " + rs.getString("name") + " | " + rs.getString("phone") +
                        " | " + rs.getString("email") + " | " + rs.getString("address"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result.add("Error fetching contacts: " + e.getMessage());
        }
        return result;
    }

    public static void addContact(String name, String phone, String email, String address) {
        query = "INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContact(int id) {
        query = "DELETE FROM contacts WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateContact(int id, String name, String phone, String email, String address) {
        query = "UPDATE contacts SET name = COALESCE(NULLIF(?, ''), name), phone = COALESCE(NULLIF(?, ''), phone), email = COALESCE(NULLIF(?, ''), email), address = COALESCE(NULLIF(?, ''), address) WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
