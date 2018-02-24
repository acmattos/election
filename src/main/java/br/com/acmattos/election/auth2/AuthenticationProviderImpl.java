package br.com.acmattos.election.auth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This AuthenticationProvider's Implementation is used by Spring Security 
 * framework to handle Credential's authentication.
 * 
 * @author acmattos
 * @since 16/01/2018
 * TODO CHECK, UNIT TEST
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
   private static final String EMPTY_PASSWORD = "";
   private static final String AUTH_EX_MESSAGE = 
      "Username or password is invalid!";

   @Autowired
   private UserDetailsService service;
   
   @Autowired
   private PasswordEncoder encoder;
   
   /**
    * Performs authentication of an user.
    *
    * @param authentication The authentication request object.
    * @return A fully authenticated object including credentials. 
    * @throws AuthenticationException If authentication fails.
    */
   @Override
   public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
      String username = authentication.getName();
      UserDetails userDetails = service.loadUserByUsername(username);

      String password = null != authentication.getCredentials()
         ? authentication.getCredentials().toString()
         : EMPTY_PASSWORD;
      if (encoder.matches(password, userDetails.getPassword())) {
         return new UsernamePasswordAuthenticationToken(
            username, password, userDetails.getAuthorities());
      }
      throw new BadCredentialsException(AUTH_EX_MESSAGE);
   }
   
   /**
    * Returns true if this AuthenticationProvider supports the indicated 
    * Authentication object. Returning true does not guarantee an 
    * AuthenticationProvider will be able to authenticate the presented 
    * instance of the Authentication class. It simply indicates it can support 
    * closer evaluation of it. An AuthenticationProvider can still return null 
    * from the authenticate(Authentication) method to indicate another 
    * AuthenticationProvider should be tried.
    *
    * @param authentication Authentication request object class.
    * @return true if the implementation can more closely evaluate the 
    *         Authentication class presented.
    *
    */
   @Override
   public boolean supports(Class<?> authentication) {
      return UsernamePasswordAuthenticationToken.class.isAssignableFrom(
         authentication);
   }
}
