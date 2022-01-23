package kirill.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    String viewLoginPage(){
        return "login";
    }

    @GetMapping("/registration")
    String viewRegPage(){
        return "reg";
    }

}
