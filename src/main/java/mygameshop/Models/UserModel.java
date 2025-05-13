package mygameshop.Models;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private int Id;
    private String Name;
    private List<GameModel> GamesOwned = new ArrayList<>();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<GameModel> getGamesOwned() {
        return GamesOwned;
    }

    public void setGamesOwned(List<GameModel> gamesOwned) {
        GamesOwned = gamesOwned;
    }
}
