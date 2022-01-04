package kirill.ecommerce.security;

import kirill.ecommerce.security.providers.CustomerAuthenticationProvider;
import kirill.ecommerce.security.providers.SupplierAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class SecurityConfigureAdapter1 extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("customerProvider")
        CustomerAuthenticationProvider customerProvider;

        @Autowired
        @Qualifier("supplierProvider")
        SupplierAuthenticationProvider supplierProvider;

        @Autowired
        AuthenticationEntryPoint entryPoint;

        @Autowired
        AuthJwtFilter filter;

        @Bean
        AuthenticationManager authManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder authManagerBuilder) {
            authManagerBuilder.authenticationProvider(customerProvider)
                    .authenticationProvider(supplierProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/customer/**").authorizeRequests()
                    .anyRequest().hasRole("CUSTOMER")
                    .and().formLogin()
                    .loginPage("/loginCustomer").and()
                    .cors().and().csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        }
    }

    @Configuration
    @Order(2)
    public static class SecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        AuthenticationEntryPoint entryPoint;

        @Autowired
        AuthJwtFilter filter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/supplier/**").authorizeRequests()
                    .anyRequest().hasRole("SUPPLIER")
                    .and().formLogin()
                    .loginPage("/loginSupplier")
                    .and().cors().and().csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}
