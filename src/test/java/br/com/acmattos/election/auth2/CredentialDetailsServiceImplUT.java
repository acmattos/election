package br.com.acmattos.election.auth2;

import br.com.acmattos.election.UnitTest;
import br.com.acmattos.election.employee.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author acmattos
 * @since 16/01/2018
 * TODO CHECK
 */
public class CredentialDetailsServiceImplUT extends UnitTest {

   @InjectMocks
   private UserDetailsServiceImpl userDetailsService;

   @Mock
   private CredentialService credentialService;

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_nullUsername() {

      String username = null;
      UserDetails userDetails = null;
      Credential credential = null;
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [null] not found or disabled!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserNotFound() {

      String username = "notfound";
      UserDetails userDetails = null;
      Credential credential = null;
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [notfound] not found or disabled!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }
   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserDisabled() {

      String username = "notfound";
      UserDetails userDetails = null;
      Credential credential = Credential.builder()
         .enabled(false)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [notfound] not found or disabled!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserProfileIsNull() {

      String username = "found";
      UserDetails userDetails = null;
      Profile profile = null;
      List<Profile> profiles = null;
      Credential credential = Credential.builder()
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [found] has no authorities!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserProfileIsEmpty() {

      String username = "found";
      UserDetails userDetails = null;
      Profile profile = null;
      List<Profile> profiles = new ArrayList<>();
      Credential credential = Credential.builder()
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [found] has no authorities!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserProfileIsDisabled() {

      String username = "found";
      UserDetails userDetails = null;
      Profile profile = Profile.builder()
         .enabled(false)
         .build();
      List<Profile> profiles = new ArrayList<>();
      Credential credential = Credential.builder()
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [found] has no authorities!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserProfileRoleIsNull() {

      String username = "found";
      UserDetails userDetails = null;
      Role role = null;
      List<Role> roles = null;
      Profile profile = Profile.builder()
         .roles(roles)
         .enabled(true)
         .build();
      List<Profile> profiles = new ArrayList<Profile>() {{ add(profile); }};
      Credential credential = Credential.builder()
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [found] has no authorities!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserProfileRoleIsEmpty() {

      String username = "found";
      UserDetails userDetails = null;
      Role role = null;
      List<Role> roles = new ArrayList<>();
      Profile profile = Profile.builder()
         .roles(roles)
         .enabled(true)
         .build();
      List<Profile> profiles = new ArrayList<Profile>() {{ add(profile); }};
      Credential credential = Credential.builder()
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [found] has no authorities!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test(expected = UsernameNotFoundException.class)
   public void loadUserByUsername_UserProfileRoleIsDisabled() {

      String username = "found";
      UserDetails userDetails = null;
      Role role = Role.builder()
         .enabled(false)
         .build();
      List<Role> roles = new ArrayList<Role>() {{ add(role); }};;
      Profile profile = Profile.builder()
         .roles(roles)
         .enabled(true)
         .build();
      List<Profile> profiles = new ArrayList<Profile>() {{ add(profile); }};

      Credential credential = Credential.builder()
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);
         fail("UsernameNotFoundException must be thrown!");
      } catch (UsernameNotFoundException e){
         assertEquals("Username [found] has no authorities!", e.getMessage());
         verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
         verifyNoMoreInteractions(credentialService);
         throw e;
      }
      //doThrow(UsernameNotFoundException.class).when(userDetailsService)
      //   .findByUsername(username);
   }

   @Test
   public void loadUserByUsername_Ok() {

      String username = "found";
      UserDetails userDetails = null;
      Role role = Role.builder()
         .description("role")
         .enabled(true)
         .build();
      List<Role> roles = new ArrayList<Role>() {{ add(role); }};;
      Profile profile = Profile.builder()
         .description("profile")
         .roles(roles)
         .enabled(true)
         .build();
      List<Profile> profiles = new ArrayList<Profile>() {{ add(profile); }};
      Credential credential = Credential.builder()
         .username("found")
         .password("password")
         .profiles(profiles)
         .enabled(true)
         .build();
      when(credentialService.findCredentialWithFullProfileByUsername(username)).thenReturn(credential);

      try {
         userDetails = userDetailsService.loadUserByUsername(username);

      } catch (UsernameNotFoundException e){
         fail("UsernameNotFoundException must not be thrown!");
      }
      verify(credentialService, times(1)).findCredentialWithFullProfileByUsername(username);
      verifyNoMoreInteractions(credentialService);
      assertNotNull(userDetails);
      assertEquals("Same username", credential.getUsername(), userDetails.getUsername());
      assertEquals("Same password", credential.getPassword(), userDetails.getPassword());
      assertTrue(userDetails.isAccountNonExpired());
      assertTrue(userDetails.isCredentialsNonExpired());
      assertTrue(userDetails.isAccountNonLocked());
      assertFalse(userDetails.getAuthorities().isEmpty());
      assertEquals("Must be only 1 authority", 1, userDetails.getAuthorities().size());
      assertEquals("Must start with 'ROLE_'", "ROLE_ROLE",
         userDetails.getAuthorities().stream().findFirst().get().getAuthority());
   }
}