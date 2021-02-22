package com.vaadin.testapp.controllers;

import com.vaadin.testapp.dto.UserDto;
import com.vaadin.testapp.models.User;
import com.vaadin.testapp.service.RoleService;
import com.vaadin.testapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RoleService roleService;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewHomePage(Model model) {
        return findPaginated(1, "username", 5, "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam(value = "pageSize") int pageSize,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<User> listUsers = page.getContent();
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("users", listUsers);
        return "users/user";
    }


    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/view";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.show(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid UserDto userDto,
                         BindingResult bindingResult,
                         @RequestParam("setRole") String setRole,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        if (setRole.equals("setAllRoles")) {
            userDto.setRoles(roleService.getRoles());
        }
        if (setRole.equals("setOnlyUser")) {
            userDto.setRoles(roleService.getOnlyRoleUser());
        }
        userService.update(userDto.convertToUserUser(), id);
        return "redirect:/user";
    }

    @PutMapping("/{id}")
    public String chengStatus(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(user, id);
        return "redirect:/user/" + id;
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        if (!userService.saveUser(userDto.convertToUserUser())) {
            model.addAttribute("user", "user already exist ");
        } else {
            userService.saveUser(userDto.convertToUserUser());
            return "redirect:/user";
        }
        return "redirect:/user";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/user";
    }
}
