package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    @Autowired
    private UserService service;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(service);
//    }
    @Autowired
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        //daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(service);
        return daoAuthenticationProvider;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.cors().and()
//http.csrf().disable();
//        http.authorizeHttpRequests()
////                .antMatchers("/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/api/**").hasRole("ADMIN")
//                .and()
//                .formLogin()//включаем форм логин
//                .loginPage("/login")//указываем что form login находиться на данном мэппинге
//                .usernameParameter("email")
//                .successHandler(successUserHandler);
//    }
        @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
                http.authorizeHttpRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").authenticated()
                .and()
                .formLogin()//включаем форм логин
                .loginPage("/login")//указываем что form login находиться на данном мэппинге
                .usernameParameter("email")
                .successHandler(successUserHandler);
    }

}
