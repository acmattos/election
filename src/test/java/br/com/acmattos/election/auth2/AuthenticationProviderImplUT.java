package br.com.acmattos.election.auth2;

import br.com.acmattos.election.UnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author acmattos
 * @since 16/01/2018
 */
public class AuthenticationProviderImplUT extends UnitTest{

   @InjectMocks
   private AuthenticationProviderImpl provider;

   @Mock
   private UserDetailsService service;
    
   @Mock
   private PasswordEncoder encoder;

   @Test(expected = BadCredentialsException.class)
   public void loadUserByUsername_passwordDoesNotMatch() {

      String username = "username";
      Authentication authentication = new UsernamePasswordAuthenticationToken(
         username, "password", new ArrayList<>());
      UserDetails userDetails = 
         new User(
            username, "password", true, true,
            true, true, new ArrayList<>());
      when(service.loadUserByUsername(username)).thenReturn(userDetails);

      try {
          Authentication notAuthenticated = provider.authenticate(authentication);
         fail("BadCredentialsException must be thrown!");
      } catch (BadCredentialsException e){
         assertEquals("Username or password is invalid!", e.getMessage());
         verify(service, times(1)).loadUserByUsername(username);
         verifyNoMoreInteractions(service);
         throw e;
      }
        //doThrow(UsernameNotFoundException.class).when(provider)
        //   .findByUsername(username);
   }

   @Test
   public void loadUserByUsername_Ok() {
      String username = "username";
      String rawPwd = "password";
      String encPwd = "$2a$10$MOVYyZA6kv7Ynxl.GBf.kOXKh73kDkgY86mVEihm2r3PA.gMERpXm";
      Authentication authentication = new UsernamePasswordAuthenticationToken(
         username, rawPwd, new ArrayList<>());
      UserDetails userDetails = new User(
         username, encPwd, true, true,
         true, true, new ArrayList(){{ add(new SimpleGrantedAuthority("ROLE_ROLE"));}});
      when(service.loadUserByUsername(username)).thenReturn(userDetails);
      when(encoder.matches(anyString(), anyString())).thenReturn(true);
      try {
           Authentication authenticated = provider.authenticate(authentication);
      } catch (BadCredentialsException e){
         fail("BadCredentialsException must not be thrown!");
      }
      verify(service, times(1)).loadUserByUsername(username);
      verify(encoder, times(1)).matches(rawPwd, encPwd);
      verifyNoMoreInteractions(service);
      verifyNoMoreInteractions(encoder);
      assertNotNull(userDetails);
      assertEquals("Same username", authentication.getName(), userDetails.getUsername());
      assertEquals("Same raw password", rawPwd, authentication.getCredentials().toString());
      assertEquals("Same encoded password", encPwd, userDetails.getPassword());
      assertNotEquals("Different password", authentication.getCredentials().toString(), userDetails.getPassword());
      assertTrue(userDetails.isAccountNonExpired());
      assertTrue(userDetails.isCredentialsNonExpired());
      assertTrue(userDetails.isAccountNonLocked());
      assertFalse("Must not be empty!", userDetails.getAuthorities().isEmpty());
      assertEquals("Must be only 1 element", 1, userDetails.getAuthorities().size());
      assertEquals("Must start with 'ROLE_'", "ROLE_ROLE",
      userDetails.getAuthorities().stream().findFirst().get().getAuthority());
   }
}