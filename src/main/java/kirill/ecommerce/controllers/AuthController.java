package kirill.ecommerce.controllers;

import kirill.ecommerce.payload.SigninRequest;
import kirill.ecommerce.payload.SignupRequest;
import kirill.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;
    private final Validator validator;

    @Autowired
    public AuthController(AuthService authService,
                          @Qualifier("valid") Validator validator){
        this.authService = authService;
        this.validator = validator;
    }

    @PostMapping("/reg")
    String registration(@ModelAttribute("signup") SignupRequest request,
                                   BindingResult bindingResult) throws Exception {
      validator.validate(request, bindingResult);
      if(bindingResult.hasErrors()) return "redirect:/registration?error";
      authService.registration(request);
      return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String loginUser(@ModelAttribute("signin") SigninRequest request, Model model) throws Exception {
        authService.authenticate(request);
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
