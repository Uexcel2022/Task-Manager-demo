package uexcel.com.taskmanagerdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SayHelloController {

    @RequestMapping("say-hello")
    @ResponseBody   //makes take whatever as the response rather html page name
    public String sayHello() {
        return "Hello... What are you learning today?";
    }

    @GetMapping("say-hello-tl")
    public String sayHelloThymeleaf() {
        return "sayHello";
    }

}
