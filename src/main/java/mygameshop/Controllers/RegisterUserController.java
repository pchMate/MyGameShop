package mygameshop.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import mygameshop.DBController.RegisteredUserDB;
import mygameshop.Models.RegisteredUserModel;
import mygameshop.interfaces.registereduser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class RegisterUserController {

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute("user") registereduser user, HttpServletResponse response) {
        if (user.loginname() == null || user.loginname().isEmpty() ||
                user.passhash() == null || user.passhash().isEmpty()) {
            return ResponseEntity.badRequest().body("LoginName and PassHash are required.");
        }

        try {
            RegisteredUserModel user2 = RegisteredUserDB.getRegisteredUserByData(user.loginname(), user.passhash());
            if (user2 != null)
            {
                return ResponseEntity.badRequest().body("User already registered.");
            }
            RegisteredUserDB.insertRegisteredUser(user);
            RegisteredUserModel user3 = RegisteredUserDB.getRegisteredUserByData(user.loginname(), user.passhash());
            if (!Objects.equals(user3.loginname, user.loginname()))
                return ResponseEntity.badRequest().body("Something happened in database");
            Cookie cookie = new Cookie("userId", String.valueOf(user3.Id));
            cookie.setPath("/");
            cookie.setMaxAge(7*24*60*60);
            cookie.setSecure(false);
            response.addCookie(cookie);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error registering user.");
        }
    }
}
