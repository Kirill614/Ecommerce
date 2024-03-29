package kirill.ecommerce.security;

import kirill.ecommerce.payload.SignupRequest;
import kirill.ecommerce.service.customer.CustomerService;
import kirill.ecommerce.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("valid")
public class SignupValidator implements Validator {
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 15;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;


    private final UserDetailsService userService;
    private final CustomerService customerService;
    private final SupplierService supplierService;

    @Autowired
    public SignupValidator(@Qualifier("userService")UserDetailsService userDetailService,
                           CustomerService customerService, SupplierService supplierService){
        this.userService = userDetailService;
        this.customerService = customerService;
        this.supplierService = supplierService;
    }

    @Override
    public boolean supports(Class<?> aClass){
        return SignupRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors){
        SignupRequest request = (SignupRequest) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Empty or has whitespaces");
        if(request.getUsername().length() < MIN_USERNAME_LENGTH ||
            request.getUsername().length() > MAX_USERNAME_LENGTH){
            errors.rejectValue("username", "username length");
        }
        if(userService.loadUserByUsername(request.getUsername()) != null){
            errors.rejectValue("username", "user with the same username already exists!");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password length");
        if(request.getPassword().length() < MIN_PASSWORD_LENGTH ||
                request.getPassword().length() > MAX_PASSWORD_LENGTH){
            errors.rejectValue("password", "password length");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "mail is empty");
        String mail = request.getMail();
        if(customerService.existsByMail(mail) || supplierService.existsByMail(mail)){
            errors.rejectValue("mail", "already taken");
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){
            errors.rejectValue("password", "different passwords");
        }
    }
}
