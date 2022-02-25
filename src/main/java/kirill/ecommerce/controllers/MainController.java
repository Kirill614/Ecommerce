package kirill.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    String viewLoginPage(){
        return "login";
    }

    @GetMapping("/registration/customer")
    String viewRegCustomerPage(){
        return "reg_customer";
    }

    @GetMapping("/registration/supplier")
    String viewRegSupplierPage(){
        return "reg_supplier";
    }

    @GetMapping("/catalog")
    String viewCatalog(){
        return "catalog";
    }

}
