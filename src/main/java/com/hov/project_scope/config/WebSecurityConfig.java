package com.hov.project_scope.config;


import com.hov.project_scope.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/successLogin")
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .and()
//                .deleteCookies("JSESSIONID")
//                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll();
//                .antMatchers(HttpMethod.GET, "/editBook").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers("/deleteBook").hasAnyAuthority("ADMIN");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
