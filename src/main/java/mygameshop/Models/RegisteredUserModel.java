package mygameshop.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public final class RegisteredUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id = -1;
    public boolean banned;
    public boolean isAdmin;
    public String name;
    public String passhash;
}
