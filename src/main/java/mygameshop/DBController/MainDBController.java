package mygameshop.DBController;

import mygameshop.Models.GameModel;
import mygameshop.Models.UserModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


public final class MainDBController {
    public static final String MainDB = "jdbc:sqlite:main.db";

    public static void EnsureExists() {
        try {
            Connection conn = DriverManager.getConnection(MainDB);
            Statement stmt = conn.createStatement();
            String CreateGameTable = """
                    CREATE TABLE IF NOT EXISTS Game (
                    Id PRIMARY KEY AUTOINCREMENT,
                    Title\tTEXT NOT NULL,
                    Price\tREAL,
                    PictureURL TEXT,
                    Description TEXT,
                    Rating REAL,
                    ReleaseDate TEXT,
                    );""";
            stmt.execute(CreateGameTable);

            String GameTag = """
                    CREATE TABLE IF NOT EXISTS GameTag (
                    GameId INTEGER NOT NULL,
                    Tag TEXT NOT NULL,
                    PRIMARY KEY (GameId, Tag),
                    FOREIGN KEY (GameId) REFERENCES Game(Id) ON DELETE CASCADE
                    );""";
            stmt.execute(GameTag);

            String User = """
                    CREATE TABLE User IF NOT EXISTS (
                    Id INTEGER PRIMARY KEY AUTOINCREMENT,
                    Name TEXT NOT NULL
                    );""";
            stmt.execute(User);

            String UserGame = """
                    CREATE TABLE IF NOT EXISTS UserGame (
                    UserId INTEGER NOT NULL,
                    GameId INTEGER NOT NULL,
                    PRIMARY KEY (UserId, GameId),
                    FOREIGN KEY (UserId) REFERENCES User(Id) ON DELETE CASCADE,
                    FOREIGN KEY (GameId) REFERENCES Game(Id) ON DELETE CASCADE
                    );""";
            stmt.execute(UserGame);

            String RegisteredUser = """
                    CREATE TABLE IF NOT EXISTS RegisteredUser (
                    Id INTEGER PRIMARY KEY AUTOINCREMENT,
                    Banned BOOLEAN NOT NULL,
                    IsAdmin BOOLEAN NOT NULL,
                    LoginName TEXT NOT NULL UNIQUE,
                    PassHash TEXT NOT NULL
                    );""";
            stmt.execute(RegisteredUser);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static GameModel GetGameById(int gameId)
    {
        String query = "SELECT * FROM Game WHERE Id = ?";
        String tagQuery = "SELECT Tag FROM GameTag WHERE GameId = ?";
        GameModel game = null;
        EnsureExists();
        try (Connection conn = DriverManager.getConnection(MainDB);
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement tagStmt = conn.prepareStatement(tagQuery)) {

            // Fetch game details
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                game = new GameModel();
                game.Id = rs.getInt("Id");
                game.Title = rs.getString("Title");
                game.Price =rs.getDouble("Price");
                game.PictureURL = rs.getString("PictureURL");
                game.Description = rs.getString("Description");
                game.Rating = rs.getDouble("Rating");
                game.ReleaseDate = new Date(rs.getDate("ReleaseDate").getTime());

                // Fetch tags for the game
                tagStmt.setInt(1, gameId);
                ResultSet tagRs = tagStmt.executeQuery();
                while (tagRs.next()) {
                    game.Tags.add(tagRs.getString("Tag"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static UserModel GetUserById(int userId)
    {
        String query = "SELECT * FROM User WHERE Id = ?";
        String gamesQuery = "SELECT GameId FROM UserGame WHERE UserId = ?";
        UserModel user = null;

        try (Connection conn = DriverManager.getConnection(MainDB);
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement gamesStmt = conn.prepareStatement(gamesQuery)) {

            // Fetch user details
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new UserModel();
                user.Id = rs.getInt("Id");
                user.Name = rs.getString("Name");

                // Fetch games owned by user
                gamesStmt.setInt(1, userId);
                ResultSet gamesRs = gamesStmt.executeQuery();
                while (gamesRs.next()) {
                    int gameId = gamesRs.getInt("GameId");
                    GameModel game = GetGameById(gameId); // Fetch game details
                    if (game != null) {
                        user.GamesOwned.add(game);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
