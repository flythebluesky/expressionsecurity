package me.rori.rbac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by Rori Stumpf on 6/7/20
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService() {
    User.UserBuilder users = User.withDefaultPasswordEncoder();
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(users.username("user").password("p").roles("TEAM_MEMBER").build());
    manager.createUser(users.username("manager").password("p").roles("MANAGER").build());
    manager.createUser(users.username("superuser").password("p").roles("SUPERUSER").build());
    return manager;
  }

  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/login*").permitAll()
        .antMatchers("/api/all").permitAll()
        .antMatchers("/**").authenticated()
        .and().formLogin();
  }
}