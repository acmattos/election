package br.com.acmattos.election.auth2;

import br.com.acmattos.election.employee.*;
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
 * Credential Details Service implementation to be used throughout the Spring Security
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
   private CredentialService service;
   
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
      Credential credential = service.findCredentialWithFullProfileByUsername(username);
      checkCredential(credential, username);

      List<GrantedAuthority> authorities = new ArrayList<>();
      checkGrantAuthorities(credential, authorities, username);

      UserDetails userDetails = createUserDetails(credential, authorities);
      return userDetails;
   }

   /**
    * Checks credential's integrity.
    *
    * @param credential Credential found by this service.
    * @param username Username identifying the credential whose data is required.
    * @return A fully populated credential record (never null).
    * @throws UsernameNotFoundException If the credential could not be found or is
    *         disabled.
    */
   private void checkCredential(Credential credential, String username){
      if(null == credential || !credential.isEnabled()){
         throw new UsernameNotFoundException(
            String.format(USER_NAME_NOT_FOUND_FORMAT, username)); 
      }
   }

   /**
    * Checks granted authorities for the credential.
    *
    * @param credential Credential found by this service.
    * @param authorities List of granted authorities of this credential.
    * @param username Username identifying the credential whose data is required.
    * @return A fully populated credential record (never null).
    * @throws UsernameNotFoundException If the credential has no GrantedAuthority.
    */
   private void checkGrantAuthorities(Credential credential,
                                      List<GrantedAuthority> authorities,
                                      String username)  
                                      throws UsernameNotFoundException{
      if(null != credential.getProfiles() && !credential.getProfiles().isEmpty()){
         credential.getProfiles().stream().forEach(profile -> {
            
            if(profile.isEnabled() 
               && null != profile.getRoles() && !profile.getRoles().isEmpty()){
               profile.getRoles().stream().forEach(role -> {
                  
                  if(role.isEnabled()){
                     authorities.add(
                        new SimpleGrantedAuthority(ROLE_PREFIX +
                           role.getDescription().toUpperCase()));
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
    * @param credential Credential found by this service.
    * @param authorities List of granted authorities of this credential.
    * @return Spring Security UserDetails' instance based on the Credential found.
    */
   private UserDetails createUserDetails(Credential credential,
                                         List<GrantedAuthority> authorities){
      boolean accountNonLocked = true;
      boolean enabledUser = true;
      boolean accountNonExpired = true;
      boolean credentialsNonExpired = true;
      UserDetails userDetails = 
         new org.springframework.security.core.userdetails.User(
            credential.getUsername(), credential.getPassword(), enabledUser,
            accountNonExpired, credentialsNonExpired, accountNonLocked, 
            authorities);
      return userDetails;
   }
}
