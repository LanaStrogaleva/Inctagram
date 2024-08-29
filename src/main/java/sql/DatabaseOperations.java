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
    // Получение id по соответствующему email в таблице User
    public static Integer getIdByEmail(String email) {
        String sql = "SELECT id FROM public.\"User\" WHERE email = ?";
        Integer id = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    // Получение confirmation_code по соответствующему "userId" в таблице public."EmailConfirmation"
    public static String getConfirmationCodeByUserId(Integer userId) {
        String sql = "SELECT \"confirmationCode\" FROM public.\"EmailConfirmation\" WHERE \"userId\" = ?";
        String confirmationCode = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                confirmationCode = rs.getString("confirmationCode");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return confirmationCode;
    }
    // Получение recoveryCode по соответствующему email из таблицы public."PasswordRecovery"
    public static String getRecoveryCodeByUserId(String email) {
        String sql = "SELECT \"recoveryCode\" FROM public.\"PasswordRecovery\" WHERE \"email\" = ?";
        String recoveryCode = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                recoveryCode = rs.getString("recoveryCode");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return recoveryCode;
    }
}
