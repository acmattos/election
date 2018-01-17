package br.com.acmattos.election.auth2;

import br.com.acmattos.election.employee.*;
import br.com.acmattos.election.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

/**
 * User Details Service implementation to be used throughout the Spring Security 
 * framework as a user DAO and is the strategy used by the 
 * DaoAuthenticationProvider.
 * 
 * @author acmattos
 * @since 16/01/2018
 * TODO http://blog.camilolopes.com.br/implementando-userdetailsservice-spring-security/
 * TODO CHECK, UNIT TEST
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private static final String USER_NAME_NOT_FOUND_FORMAT = 
      "Username [%s] not found or disabled!";
   private static final String USER_NAME_HAS_NO_AUTH_FORMAT = 
      "Username [%s] has no authorities!";   
   private static final String ROLE_PREFIX = "ROLE_";   
   @Autowired
   private UserRepository repository;
   
   /**
    * Locates the user based on the username. The user name is CASE SENSITIVE!
    *
    * @param username Username identifying the user whose data is required.
    * @return A fully populated user record (never null).
    * @throws UsernameNotFoundException If the user could not be found or the 
    *         user has no GrantedAuthority.
    */
   @Override
   public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
      User user = repository.findByUsername(username);
      checkUser(user, username);

      List<GrantedAuthority> authorities = new ArrayList<>();
      checkGrantAuthorities(user, authorities, username);

      UserDetails userDetails = createUserDetails(user, authorities);
      return userDetails;
   }

   /**
    * Checks user's integrity.
    *
    * @param user User found by this service.
    * @param username Username identifying the user whose data is required.
    * @return A fully populated user record (never null).
    * @throws UsernameNotFoundException If the user could not be found or is 
    *         disabled.
    */
   private void checkUser(User user, String username){
      if(null == user || !user.isEnabled()){
         throw new UsernameNotFoundException(
            String.format(USER_NAME_NOT_FOUND_FORMAT, username)); 
      }
   }

   /**
    * Checks granted authorities for the user.
    *
    * @param user User found by this service.
    * @param authorities List of granted authorities of this user.
    * @param username Username identifying the user whose data is required.
    * @return A fully populated user record (never null).
    * @throws UsernameNotFoundException If the user has no GrantedAuthority.
    */
   private void checkGrantAuthorities(User user,
                                      List<GrantedAuthority> authorities,
                                      String username)  
                                      throws UsernameNotFoundException{
      if(null != user.getProfiles() && !user.getProfiles().isEmpty()){
         user.getProfiles().stream().foreach(profile => {
            
            if(profile.isEnabled() 
               && null != profile.getRoles() && !profile.getRoles().isEmpty()){
               profile.getRoles().stream().foreach(role => {
                  
                  if(role.isEnabled()){
                     authorities.add(
                        new GrantedAuthorityImpl(
                           ROLE_PREFIX + role.getDescription().upper()));
         
                  }
               });
            }
         });
      }
      
      if(authorities.isEmpty()){
         throw new UsernameNotFoundException(
            String.format(USER_NAME_HAS_NO_AUTH_FORMAT, username));
      }
   }

   /**
    * Creates a Spring Security UserDetails' instance.
    * 
    * @param user User found by this service.
    * @param authorities List of granted authorities of this user.
    * @return Spring Security UserDetails' instance based on the User found.
    */
   private UserDetails createUserDetails(User user, 
                                         List<GrantedAuthority> authorities){
      boolean accountNonLocked = true;
      boolean enabledUser = true;
      boolean accountNonExpired = true;
      boolean credentialsNonExpired = true;
      UserDetails userDetails = 
         new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), enabledUser, 
            accountNonExpired, credentialsNonExpired, accountNonLocked, 
            authorities);
      return userDetails;
   }
}
