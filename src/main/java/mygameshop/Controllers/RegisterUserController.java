package mygameshop.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import mygameshop.Models.RegisteredUserModel;
import mygameshop.Service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class RegisterUserController {

    @Autowired
    private RegisterUserService registerUserService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("register", new RegisteredUserModel());
        return "authing/register";
    }

    @RequestMapping(value =  "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(
            @ModelAttribute RegisteredUserModel user,
            HttpServletResponse response, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("authing/register.");
        }
        if (user.name == null || user.name.isEmpty()
        || user.passhash == null || user.passhash.isEmpty()) {
            return ResponseEntity.badRequest().
                    body("LoginName and PassHash are required.");
        }

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.badRequest().body("Cannot encrypt password!");
        }
        byte[] hashbytes = digest.digest(
                user.passhash.getBytes());
        user.passhash = Base64.getEncoder().encodeToString(hashbytes);

        try {
            if (registerUserService.findByName(
                    user.name, user.passhash).isEmpty())
            {
                return ResponseEntity.badRequest().body("User already registered.");
            }
            RegisteredUserModel user3 = registerUserService.save(user);
            if (!Objects.equals(user3.name, user.name))
            {
                return ResponseEntity.badRequest().
                        body("Something happened in database");
            }
            Cookie cookie = new Cookie("userId", String.valueOf(user3.id));
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(false);
            response.addCookie(cookie);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error registering user.");
        }
    }
}
