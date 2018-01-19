package br.com.acmattos.election.auth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author acmattos
 * TODO JAVADOC, CHECK
 * TODO https://spring.io/guides/tutorials/spring-security-and-angular-js/#_sso_with_oauth2_angular_js_and_spring_security_part_v
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
   
   @Autowired
   private AuthenticationProvider authenticationProvider;
      
   @Override
   protected void configure(
      AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authenticationProvider);
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
         .csrf().disable()
         .anonymous().disable()
         .authorizeRequests()
         .antMatchers("/oauth/token").permitAll();
         //.antMatchers("/index.html", "/home.html", "/login.html", "/", "/home", "/login").permitAll()
         //.anyRequest().authenticated();
   }
   
   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
}
