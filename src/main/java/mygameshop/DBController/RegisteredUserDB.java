package mygameshop.DBController;

import mygameshop.Models.RegisteredUserUserModel;
import mygameshop.interfaces.RegisteredUser;

import java.sql.*;

public final class RegisteredUserDB {
    // Insert a new Registered User
    public static void insertRegisteredUser(RegisteredUser user) {
        String query = "INSERT INTO RegisteredUser (Banned, IsAdmin, LoginName, PassHash) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, false);
            stmt.setBoolean(2, false);
            stmt.setString(3, user.loginname());
            stmt.setString(4, user.passhash());

            stmt.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Fetch a RegisteredUser by ID
    public static RegisteredUserUserModel getRegisteredUserById(int id) {
        String query = "SELECT * FROM RegisteredUser WHERE Id = ?";
        RegisteredUserUserModel user = null;

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new RegisteredUserUserModel();
                user.setId(rs.getInt("Id"));
                user.setBanned(rs.getBoolean("Banned"));
                user.setAdmin(rs.getBoolean("IsAdmin"));
                user.setLoginname(rs.getString("LoginName"));
                user.setPasshash(rs.getString("PassHash"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static RegisteredUserUserModel getRegisteredUserByData(String loginName, String passHash) {
        String query = "SELECT * FROM RegisteredUser WHERE LoginName = ? AND PassHash = ?";
        RegisteredUserUserModel user = null;

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loginName);
            stmt.setString(2, passHash);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new RegisteredUserUserModel();
                user.setId(rs.getInt("Id"));
                user.setBanned(rs.getBoolean("Banned"));
                user.setAdmin(rs.getBoolean("IsAdmin"));
                user.setLoginname(rs.getString("LoginName"));
                user.setPasshash(rs.getString("PassHash"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    // Update a Registered User
    public static void updateRegisteredUser(RegisteredUserUserModel user) {
        String query = "UPDATE RegisteredUser SET Banned = ?, IsAdmin = ?, LoginName = ?, PassHash = ? WHERE Id = ?";

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, user.isBanned());
            stmt.setBoolean(2, user.isAdmin());
            stmt.setString(3, user.loginname());
            stmt.setString(4, user.passhash());
            stmt.setInt(5, user.getId());

            stmt.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a Registered User by ID
    public static void deleteRegisteredUserById(int id) {
        String query = "DELETE FROM RegisteredUser WHERE Id = ?";

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
