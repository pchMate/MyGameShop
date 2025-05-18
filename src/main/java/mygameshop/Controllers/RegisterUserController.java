package mygameshop.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mygameshop.Models.RegisteredUserModel;
import mygameshop.Models.UserModel;
import mygameshop.Service.RegisterUserService;
import mygameshop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public final class RegisterUserController {

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        model.addAttribute("register", new RegisteredUserModel());
        String userid = getCookie(request, "userId");
        if (userid == null)
        {
            return "authing/login";
        }
        int useridInt = Integer.getInteger(userid);
        if (registerUserService.findById(useridInt).isEmpty())
        {
            return "authing/login";
        }
        return "redirect:/games/list";
    }

    public String getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @RequestMapping(value =  "/login", method = RequestMethod.POST)
    public String register(
            @ModelAttribute RegisteredUserModel user,
            HttpServletResponse response, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authing/login";
        }
        if (user.name == null || user.name.isEmpty()
        || user.passhash == null || user.passhash.isEmpty()) {
            return "LoginName and PassHash are required.";
        }

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            return "Cannot encrypt password!";
        }
        byte[] hashbytes = digest.digest(
                user.passhash.getBytes());
        user.passhash = Base64.getEncoder().encodeToString(hashbytes);
        System.out.println("Pass changed");
        try {
            Optional<RegisteredUserModel> foundUser = registerUserService.findByName(
                    user.name, user.passhash);
            System.out.println("Checking if user exists");
            if (foundUser.isPresent())
            {
                setCookie(response, foundUser.get().id);
                return "redirect:/games/list";
            }
            System.out.println("if not we save");
            user.isAdmin = false;
            user.banned = false;
            RegisteredUserModel user3 = registerUserService.save(user);
            UserModel model = new UserModel();
            model.name = user3.name;
            model.gamesOwned = new ArrayList<>();
            System.out.println("save other user");
            userService.save(model);
            if (!Objects.equals(user3.name, user.name))
            {
                return "Something happened in database";
            }
            setCookie(response, user3.id);
            return "redirect:/games/list";
        } catch (Exception e) {
            System.out.println("EXCEPTION!!!");
            System.out.println(e.getMessage());
            return "redirect:/errors/UserLoginError";
        }
    }

    private void setCookie(HttpServletResponse response, int id)
    {
        Cookie cookie = new Cookie("userId", String.valueOf(id));
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}
