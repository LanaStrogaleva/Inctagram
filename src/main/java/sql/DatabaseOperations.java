package sql;

import java.sql.*;

public class DatabaseOperations {
    public static void executeQuery(String query) {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    // Замена false на true в столбце isConfirmed в поле с соответствующим userId в таблице users_registration_info
    public static void updateIsConfirmed(String userId, boolean isConfirmed) {
        String sql = "UPDATE public.users_registration_info SET is_confirmed = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isConfirmed);
            pstmt.setString(2, userId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No record found with the provided user ID.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    // Получение id по соответствующему email в таблице users
    public static String getIdByEmail(String email) {
        String sql = "SELECT id FROM public.users WHERE email = ?";
        String id = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getString("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    // Получение confirmation_code по соответствующему user_id в таблице users_registration_info
    public static String getConfirmationCodeByUserId(String email) {
        String sql = "SELECT id FROM public.users_registration_info WHERE user_id = ?";
        String confirmationCode = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                confirmationCode = rs.getString("confirmation_code");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return confirmationCode;
    }
}
