package com.mkyong.security;

import com.mkyong.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Pour l'identification
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /*
    @Override
    public void configure(AuthenticationManagerBuilder auth)throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("USER", "ADMIN")
        ;
    }
*/
    // Pour annuler les autorisations sur les ressources
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/pictures/**");

    }

    // le gros de la configuration est ici
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .frameOptions().sameOrigin()
                    .and()
                        .authorizeRequests()
                            .antMatchers("/recources/**","/webjars/**","/assets/**","/login/**","/registration/**").permitAll()
                            .antMatchers("/admin/**").hasRole("ADMIN")
                               // .antMatchers().permitAll()
                                .anyRequest().authenticated()
                                .and()
                            .formLogin()
                                .loginPage("/login")
                                .defaultSuccessUrl("/welcome")
                                .failureUrl("/login?error")
                                .permitAll()
                                .and()
                            .logout()
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .deleteCookies("my-remember-me-cookie")
                                    .permitAll()
                                    .and()
                            .rememberMe()
                                //.key("my-secure-key")
                                .rememberMeCookieName("my-remember-me-cookie")
                                //.tokenValiditySeconds(24 * 60 * 60)
                                .and()
                            .exceptionHandling()
        ;
    }

}
