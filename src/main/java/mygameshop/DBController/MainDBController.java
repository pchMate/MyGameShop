package mygameshop.DBController;

import mygameshop.Models.GameModel;
import mygameshop.Models.UserModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class MainDBController {
    public static final String MAIN_DB = "jdbc:sqlite:main.db";

    private MainDBController() {

    }

    public static void ensureExists() {
        try {
            Connection conn = DriverManager.getConnection(MAIN_DB);
            Statement stmt = conn.createStatement();
            String creategametable = """
                    CREATE TABLE IF NOT EXISTS Game (
                    Id PRIMARY KEY AUTOINCREMENT,
                    Title\tTEXT NOT NULL,
                    Price\tREAL,
                    PictureURL TEXT,
                    Description TEXT,
                    Rating REAL,
                    ReleaseDate TEXT,
                    );""";
            stmt.execute(creategametable);

            String gameTag = """
                    CREATE TABLE IF NOT EXISTS GameTag (
                    GameId INTEGER NOT NULL,
                    Tag TEXT NOT NULL,
                    PRIMARY KEY (GameId, Tag),
                    FOREIGN KEY (GameId) REFERENCES Game(Id) ON DELETE CASCADE
                    );""";
            stmt.execute(gameTag);

            String user = """
                    CREATE TABLE User IF NOT EXISTS (
                    Id INTEGER PRIMARY KEY AUTOINCREMENT,
                    Name TEXT NOT NULL
                    );""";
            stmt.execute(user);

            String usergame = """
                    CREATE TABLE IF NOT EXISTS UserGame (
                    UserId INTEGER NOT NULL,
                    GameId INTEGER NOT NULL,
                    PRIMARY KEY (UserId, GameId),
                    FOREIGN KEY (UserId) REFERENCES User(Id) ON DELETE CASCADE,
                    FOREIGN KEY (GameId) REFERENCES Game(Id) ON DELETE CASCADE
                    );""";
            stmt.execute(usergame);

            String registereduser = """
                    CREATE TABLE IF NOT EXISTS RegisteredUser (
                    Id INTEGER PRIMARY KEY AUTOINCREMENT,
                    Banned BOOLEAN NOT NULL,
                    IsAdmin BOOLEAN NOT NULL,
                    LoginName TEXT NOT NULL UNIQUE,
                    PassHash TEXT NOT NULL
                    );""";
            stmt.execute(registereduser);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static GameModel getGameById(final int gameId)
    {
        String query = "SELECT * FROM Game WHERE Id = ?";
        String tagQuery = "SELECT Tag FROM GameTag WHERE GameId = ?";
        GameModel game = null;
        ensureExists();
        try (Connection conn = DriverManager.getConnection(MAIN_DB);
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement tagStmt = conn.prepareStatement(tagQuery)) {

            // Fetch game details
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                game = new GameModel();
                game.setId(rs.getInt("Id"));
                game.setTitle(rs.getString("Title"));
                game.setPrice(rs.getDouble("Price"));
                game.setPictureUrl(rs.getString("PictureURL"));
                game.setDescription(rs.getString("Description"));
                game.setRating(rs.getDouble("Rating"));
                game.setReleaseDate(new Date(rs.getDate("ReleaseDate").getTime()));

                // Fetch tags for the game
                tagStmt.setInt(1, gameId);
                List<String> tags = new ArrayList<>();
                ResultSet tagRs = tagStmt.executeQuery();
                while (tagRs.next()) {
                    tags.add(tagRs.getString("Tag"));
                }
                game.setTags(tags);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }

    public static UserModel getUserById(final int userId)
    {
        String query = "SELECT * FROM User WHERE Id = ?";
        String gamesQuery = "SELECT GameId FROM UserGame WHERE UserId = ?";
        UserModel user = null;

        try (Connection conn = DriverManager.getConnection(MAIN_DB);
             PreparedStatement stmt = conn.prepareStatement(query);
             PreparedStatement gamesStmt = conn.prepareStatement(gamesQuery)) {

            // Fetch user details
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new UserModel();
                user.setId(rs.getInt("Id"));
                user.setName(rs.getString("Name"));

                // Fetch games owned by user
                gamesStmt.setInt(1, userId);
                ResultSet gamesRs = gamesStmt.executeQuery();
                List<GameModel> gamesOwned = new ArrayList<>();
                while (gamesRs.next()) {
                    int gameId = gamesRs.getInt("GameId");
                    GameModel game = getGameById(gameId); // Fetch game details
                    if (game != null) {
                        gamesOwned.add(game);
                    }
                }
                user.setGamesOwned(gamesOwned);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
