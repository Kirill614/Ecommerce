package kirill.ecommerce.security;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class SecurityConfigureAdapter1 extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("userService")
        private UserDetailsService userService;

        @Autowired
        @Qualifier("jwtFilter")
        private OncePerRequestFilter filter;

        @Autowired
        private CustomAccessDeniedHandler accessDeniedHandler;

        @Bean
        AuthenticationManager authManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        public void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
            authManagerBuilder.userDetailsService(userService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .antMatcher("/customer/**").authorizeRequests()
//                    .anyRequest().hasRole("CUSTOMER")
//                    .and().formLogin()
//                    .loginPage("/login").and()
//                    .cors().and().csrf().disable()
//                    .exceptionHandling().authenticationEntryPoint(entryPoint).and()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//            http.authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll();

//            http.authorizeRequests()
//                    .antMatchers("/product/**")
//                    .hasRole("CUSTOMER")
//                    .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

//            http.authorizeRequests()
//                    .antMatchers("/cart/**")
//                    .hasRole("CUSTOMER");

            http.cors().and().csrf().disable();

            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        }
    }

    @Configuration
    @Order(2)
    public static class SecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticationEntryPoint entryPoint;

        @Autowired
        private AuthJwtFilter filter;

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
