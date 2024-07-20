package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String table(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "table";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("newUser", new User());
        return "create";
    }

    @PostMapping("/new")
    public String add(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        } else {
            userService.addUser(user);
            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        userService.removeUser(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String updateUser(@RequestParam int id, Model model) {
        model.addAttribute("userToUpdate", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String update(User userToSave, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            userService.updateUser(userToSave);
            return "redirect:/";
        }
    }
}