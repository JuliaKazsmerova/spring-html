package com.gohealth.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final Repository repository;

    @Autowired
    public UserController(Repository repository) {
        this.repository = repository;
    }

    @GetMapping("/form")
    public String showForm(User user) {
        return "form";
    }

    @GetMapping("/list")
    public String showUpdateForm(Model model) {
        model.addAttribute("users", repository.findAll());
        return "list";
    }

    @PostMapping("/add")
    public String addStudent(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }

        if(repository.findUserByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "error.exist", "Email already exists");
            return "form";
        }

        repository.save(user);
        return "result";
    }
}
