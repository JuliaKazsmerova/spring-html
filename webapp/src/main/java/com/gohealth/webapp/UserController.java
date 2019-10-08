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
//https://stackoverflow.com/questions/32801008/how-to-find-out-if-an-email-already-exist-with-jpa-spring-and-sending-some-error
    @GetMapping("/form")
    public String showForm(User user) {
        return "save";
    }

    @GetMapping("/list")
    public String showUpdateForm(Model model) {
        model.addAttribute("users", repository.findAll());
        return "list";
    }

    @PostMapping("/add")
    public String addStudent(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "save";
        }

        if(repository.findUserByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "error.exist", "Email already exists");
            return "save";
        }

        repository.save(user);
        return "redirect:list";
    }

//    @PostMapping("/user")
//    public String showUsersData(@ModelAttribute User user) {
//        return "result";
//    }
    // https://www.javaguides.net/2019/04/spring-boot-thymeleaf-crud-example-tutorial.html
    // https://technology.amis.nl/2019/02/26/building-a-restful-web-service-with-spring-boot-using-an-h2-in-memory-database-and-also-an-external-mysql-database/
    // https://spring.io/guides/gs/handling-form-submission/

}
