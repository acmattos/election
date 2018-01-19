package br.com.acmattos.election.employee;

import br.com.acmattos.election.UnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author acmattos
 * @since 18/01/2018
 */
public class CredentialListenerUT extends UnitTest {

   @InjectMocks
   private CredentialListener listener;

   @Test(expected = IllegalArgumentException.class)
   public void beforePersist_nullEntity() throws Exception {
      Credential entity = null;
      try {
         listener.beforePersist(entity);
         fail("IllegalArgumentException must be thrown!");
      } catch (IllegalArgumentException e) {
          assertEquals("Exception message must be the same",
             "Credential can't be null!", e.getMessage());
          throw e;
      }
   }

   @Test(expected = IllegalArgumentException.class)
   public void beforePersist_nullPassword() throws Exception {
      Credential entity = Credential.builder().build();
      try {
         listener.beforePersist(entity);
         fail("IllegalArgumentException must be thrown!");
      } catch (IllegalArgumentException e) {
         assertEquals("Exception message must be the same",
                 "Password can't be null or empty!", e.getMessage());
         throw e;
      }
   }

   @Test(expected = IllegalArgumentException.class)
   public void beforePersist_emptyPassword() throws Exception {
      Credential entity = Credential.builder().rawPassword("").build();
      try {
         listener.beforePersist(entity);
         fail("IllegalArgumentException must be thrown!");
      } catch (IllegalArgumentException e) {
         assertEquals("Exception message must be the same",
                 "Password can't be null or empty!", e.getMessage());
         throw e;
      }
   }

   @Test
   public void beforePersist_passwordOk() throws Exception {
      String password = "rawpassword";
      Credential entity = Credential.builder().rawPassword(password).build();
      try {
         listener.beforePersist(entity);
         assertNotNull("Not null password", entity.getPassword());
         assertNotEquals("Same password", password, entity.getPassword());
      } catch (IllegalArgumentException e) {
          fail("IllegalArgumentException must not be thrown!");
      }
   }

   @Test(expected = IllegalArgumentException.class)
   public void beforeUpdate_nullEntity() throws Exception {
      Credential entity = null;
      try {
         listener.beforeUpdate(entity);
         fail("IllegalArgumentException must be thrown!");
      } catch (IllegalArgumentException e) {
         assertEquals("Exception message must be the same",
            "Credential can't be null!", e.getMessage());
         throw e;
      }
   }
   @Test
   public void beforeUpdate_nullPasswordOk() throws Exception {
      String password = null;
      Credential entity = Credential.builder().id(1L)
              .rawPassword(password).password("encoded").build();
      try {
         listener.beforeUpdate(entity);
         assertNull("Null raw password", entity.getRawPassword());
         assertEquals("Same password", "encoded", entity.getPassword());
      } catch (IllegalArgumentException e) {
         fail("IllegalArgumentException must not be thrown!");
      }
   }

   @Test
   public void beforeUpdate_emptyPasswordOk() throws Exception {
       String password = "";
       Credential entity = Credential.builder().id(1L)
               .rawPassword(password).password("encoded").build();
       try {
           listener.beforeUpdate(entity);
           assertNotNull("Not null raw password", entity.getRawPassword());
           assertEquals("Same raw password", password, entity.getRawPassword());
           assertEquals("Same password", "encoded", entity.getPassword());
       } catch (IllegalArgumentException e) {
          fail("IllegalArgumentException must not be thrown!");
       }
   }

   @Test
   public void beforeUpdate_newPasswordOk() throws Exception {
        String password = "newpassword";
        Credential entity = Credential.builder().id(1L).rawPassword(password).build();
        try {
            listener.beforeUpdate(entity);
            assertNotNull("Not null password", entity.getPassword());
            assertNotEquals("Different password", password, entity.getPassword());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException must not be thrown!");
        }
   }

   @Test
   public void beforeUpdate_oldPasswordOk() throws Exception {
        String password = "oldencoded";
        Credential entity = Credential.builder().id(1L).password(password).build();
        try {
            listener.beforeUpdate(entity);
            assertNotNull("Not null password", entity.getPassword());
            assertEquals("Same password", "oldencoded", entity.getPassword());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException must not be thrown!");
        }
   }
}