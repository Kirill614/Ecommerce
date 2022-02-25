package kirill.ecommerce.controllers;

import kirill.ecommerce.payload.SigninRequest;
import kirill.ecommerce.payload.SignupRequest;
import kirill.ecommerce.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthServiceImpl authServiceImpl;
    private final Validator validator;

    @Autowired
    public AuthController(AuthServiceImpl authServiceImpl,
                          @Qualifier("valid") Validator validator) {
        this.authServiceImpl = authServiceImpl;
        this.validator = validator;
    }

    @PostMapping("register/customer")
    String regCustomer(@ModelAttribute("signup") SignupRequest request,
                        BindingResult result){
        validator.validate(request, result);
        if(result.hasErrors()) return "redirect:/403";
        authServiceImpl.regCustomer(request);
        System.out.println("registered successfully");
        return "redirect:/customer";
    }

    @PostMapping("register/supplier")
    String regSupplier(@ModelAttribute("signup") SignupRequest request,
                                  BindingResult result){
        validator.validate(request, result);
        if(result.hasErrors()) return "redirect:/403";
        authServiceImpl.regSupplier(request);
        System.out.println("registered successfully");
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String loginUser(@ModelAttribute("signin") SigninRequest request, Model model) throws Exception {
        authServiceImpl.authenticate(request);
        return "redirect:/customer";
    }

    @GetMapping("/customer")
    String showCustomerPage() {
        return "customer";
    }

    @ModelAttribute("signin")
    public void addAttributes(Model model) {
        model.addAttribute("signin", new SigninRequest());
    }

    @ModelAttribute("signup")
    public void addRegAttr(Model model) {
        model.addAttribute("signup", new SignupRequest());
    }
}
