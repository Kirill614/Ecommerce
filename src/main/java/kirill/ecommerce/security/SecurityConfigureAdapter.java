package kirill.ecommerce.security;

import kirill.ecommerce.security.AuthEntryPoint;
import kirill.ecommerce.security.AuthJwtFilter;
import kirill.ecommerce.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfigureAdapter extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    AuthEntryPoint entryPoint;
//
//    @Autowired
//    CustomerAuthenticationProvider customerAuthProvider;
//
//    @Autowired
//    SupplierAuthenticationProvider supplierProvider;
//
//    @Bean
//    AuthJwtFilter filter(){
//        return new AuthJwtFilter();
//    };
//
//    @Autowired
//    UserDetailsServiceImpl userService;
//
////    @Bean
////    PasswordEncoder passwordEncoder(){
////        return new BCryptPasswordEncoder();
////    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    @Order(1)
//    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//        //authManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
//        authManagerBuilder.authenticationProvider(customerAuthProvider);
//    }
//
//    @Order(2)
//    protected void configureGlobal(AuthenticationManagerBuilder auth){
//        auth.authenticationProvider(supplierProvider);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.cors().and().csrf().disable()
////                .exceptionHandling().authenticationEntryPoint(entryPoint)
////                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and().authorizeRequests().antMatchers("api/auth/**").permitAll()
////                .antMatchers("api/test/**").permitAll()
////                .anyRequest().authenticated();
//
//        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(entryPoint).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests().antMatchers("api/auth/**").permitAll()
//                .antMatchers("product/**").authenticated();
//                //.anyRequest().authenticated();
//        http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
//    }
//}
