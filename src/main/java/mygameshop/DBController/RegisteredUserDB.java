package mygameshop.DBController;

import mygameshop.Models.RegisteredUserModel;
import mygameshop.interfaces.registereduser;

import java.sql.*;

public final class RegisteredUserDB {
    // Insert a new Registered User
    public static void insertRegisteredUser(registereduser user) {
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
    public static RegisteredUserModel getRegisteredUserById(int id) {
        String query = "SELECT * FROM RegisteredUser WHERE Id = ?";
        RegisteredUserModel user = null;

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new RegisteredUserModel();
                user.Id = (rs.getInt("Id"));
                user.Banned = (rs.getBoolean("Banned"));
                user.IsAdmin = (rs.getBoolean("IsAdmin"));
                user.loginname = (rs.getString("LoginName"));
                user.passhash = (rs.getString("PassHash"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static RegisteredUserModel getRegisteredUserByData(String loginName, String passHash) {
        String query = "SELECT * FROM RegisteredUser WHERE LoginName = ? AND PassHash = ?";
        RegisteredUserModel user = null;

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loginName);
            stmt.setString(2, passHash);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new RegisteredUserModel();
                user.Id = (rs.getInt("Id"));
                user.Banned = (rs.getBoolean("Banned"));
                user.IsAdmin = (rs.getBoolean("IsAdmin"));
                user.loginname = (rs.getString("LoginName"));
                user.passhash = (rs.getString("PassHash"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    // Update a Registered User
    public static void updateRegisteredUser(RegisteredUserModel user) {
        String query = "UPDATE RegisteredUser SET Banned = ?, IsAdmin = ?, LoginName = ?, PassHash = ? WHERE Id = ?";

        try (Connection conn = DriverManager.getConnection(MainDBController.MainDB);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, user.Banned);
            stmt.setBoolean(2, user.IsAdmin);
            stmt.setString(3, user.loginname);
            stmt.setString(4, user.passhash);
            stmt.setInt(5, user.Id);

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
