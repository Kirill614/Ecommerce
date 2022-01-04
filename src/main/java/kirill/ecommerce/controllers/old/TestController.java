package kirill.ecommerce.controllers.old;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    String adminContent(){
        return "admin_content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    String userContent(){
        return "user_content";
    }

}
