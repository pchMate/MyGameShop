package mygameshop.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameModel {
    public int Id;
    public String Title;
    public double Price; // IF 0 Means free
    public String PictureURL;
    public String Description;
    public double Rating;
    public List<String> Tags= new ArrayList<>();
    public Date ReleaseDate;
}
