package mygameshop.Models;

import java.util.ArrayList;
import java.util.List;

public final class UserModel {
    private int id;
    private String name;
    private List<GameModel> gamesOwned = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<GameModel> getGamesOwned() {
        return gamesOwned;
    }

    public void setGamesOwned(final List<GameModel> gamesOwned) {
        this.gamesOwned = gamesOwned;
    }
}
