package mygameshop.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public final class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String title;
    public double price; // IF 0 Means free
    public String pictureUrl;
    public String description;
    public double rating;
    @ElementCollection
    public List<TagModel> tags = new ArrayList<>();
    public Date releaseDate;
}
