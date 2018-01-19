package br.com.acmattos.election.employee;

import br.com.acmattos.election.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 18/01/2018
 */
public class CredentialListenerIT extends IntegrationTest {

   @Autowired
   private CredentialRepository repository;

   @Test
   public void beforePersist() throws Exception {
      String password = "password";
      Credential credential = Credential.builder()
               .username("username1")
               .rawPassword(password)
               .enabled(true)
               .build();
      repository.save(credential);
      assertNotNull("Not null password", credential.getPassword());
      assertNotEquals("Different password", password, credential.getPassword());
      assertEquals("Different raw password", "RWPW", credential.getRawPassword());
   }

    @Test
    public void beforeUpdate() throws Exception {
       String password = "password";
       Credential credential = Credential.builder()
                .username("username2")
                .rawPassword(password)
                .enabled(true)
                .build();
       credential = repository.save(credential);
       assertNotNull("Not null password", credential.getPassword());
       assertNotEquals("Different password", password, credential.getPassword());
       assertEquals("Different raw password", "RWPW", credential.getRawPassword());
       String encodedPassord = credential.getPassword();

       credential.setRawPassword("newpassword");
       credential = repository.save(credential);
       assertNotNull("Not null password", credential.getPassword());
       assertNotEquals("Different password raw", "newpassword", credential.getPassword());
       assertNotEquals("Different password encoded", encodedPassord, credential.getPassword());
       assertEquals("Different raw password", "RWPW", credential.getRawPassword());
    }
}