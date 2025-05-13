package mygameshop.Models;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public int Id;
    public String Name;
    public List<GameModel> GamesOwned = new ArrayList<>();

}
