package mygameshop.Controllers;

import mygameshop.Models.GameModel;
import mygameshop.Service.GameService;
import mygameshop.Service.TagService;
import mygameshop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/games")
public final class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("games", gameService.getAll());
        return "games/main";
    }

    @GetMapping("/new")
    public String createBookForm(Model model) {
        model.addAttribute("game", new GameModel());
        model.addAttribute("tags", tagService.getAll());
        return "games/create"; // Updated template path
    }

    @PostMapping("/createnew")
    public String saveBook(@ModelAttribute GameModel gameModel) {
        gameService.save(gameModel);
        return "redirect:/games/list";
    }
}
