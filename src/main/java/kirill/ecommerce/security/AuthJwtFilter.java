package kirill.ecommerce.security;

import kirill.ecommerce.service.UserDetailsImpl;
import kirill.ecommerce.service.UserDetailsServiceImpl;
import kirill.ecommerce.service.customer.CustomerDetailsImpl;
import kirill.ecommerce.service.customer.CustomerDetailsServiceImpl;
import kirill.ecommerce.service.supplier.SupplierDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    CustomerDetailsServiceImpl customerService;

    @Autowired
    SupplierDetailsServiceImpl supplierService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authToken = jwtHelper.parseTokenFromRequest(request);
        if(StringUtils.hasText(authToken) && jwtHelper.validateToken(authToken)){
            try{
                UserDetails userDetails;
                String username = jwtHelper.parseUsername(authToken);
                if(customerService.existsByUsername(username)){
                    userDetails = customerService.loadUserByUsername(username);
                } else userDetails = supplierService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                        null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException e){
                  e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
