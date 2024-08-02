package uexcel.com.taskmanagerdemo.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.function.Supplier;

@Controller
@SessionAttributes("username")
public class WelcomeController {

    @GetMapping("/")
    public  String goToWelcomePage(ModelMap modelMap){
        modelMap.addAttribute("username", logInUsername.get());
            return "welcome";

    }

    private final Supplier<String> logInUsername =()-> SecurityContextHolder.getContext().getAuthentication().getName();
}
