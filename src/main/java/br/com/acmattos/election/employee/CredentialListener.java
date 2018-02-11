package br.com.acmattos.election.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Credential entity listener that observes raw password changes. In that case,
 * a new encoded password must be generated based on raw password given.
 *
 * @author acmattos
 * @since 18/01/2018
 * TODO Document Listener
 */
@Component
public class CredentialListener {
   private static final String RAW_PASSWORD = "RWPW";
   private static final String IAE_MESSAGE_PASSWORD_INVALID =
            "Password can't be null or empty!";
   private static final String IAE_MESSAGE_ENTITY_INVALID =
            "Credential can't be null!";

   // TODO DOCUMENT SOLUTION http://blog-en.lineofsightnet.com/2012/08/dependency-injection-on-stateless-beans.html
   private static PasswordEncoder passwordEncoder;

   /**
    * In order to allow automatic dependency injection of a class that is
    * instantiated automaticaly by persistence framework (out of Spring's
    * dependency management).
    *
    * @param passwordEncoder
    */
   @Autowired(required = true)
   @Qualifier("passwordEncoder")
   public void setPasswordEncoder(PasswordEncoder passwordEncoder){
      this.passwordEncoder = passwordEncoder;
   }

   /**
    * Encodes password before persit the entity and switches .
    *
    * @param entity Credential entity.
    * @throws IllegalArgumentException if entity's password is null or empty.
    */
   @PrePersist
   public void beforePersist(Credential entity) throws IllegalArgumentException{
      validateEntity(entity);
      String encodedPassword = getEncodedPassword(entity.getRawPassword());
      entity.setRawPassword(RAW_PASSWORD);
      entity.setPassword(encodedPassword);
   }

   /**
    * Encodes password, only if it has changed, before persit the entity.
    * @param entity
    * @throws IllegalArgumentException if entity's password is null or empty.
    */
   @PreUpdate
   public void beforeUpdate(Credential entity) throws IllegalArgumentException{
      validateEntity(entity);
      if(null != entity.getRawPassword() && !entity.getRawPassword().isEmpty()
         && !RAW_PASSWORD.equals(entity.getRawPassword())){
         beforePersist(entity);
      }
   }

   /**
    * Validates entity against NullPointerException.
    * @param entity Credential entity.
    * @throws IllegalArgumentException if entity is null.
    */
   private void validateEntity(Credential entity)
      throws IllegalArgumentException{
      if(null == entity){
         throw new IllegalArgumentException(IAE_MESSAGE_ENTITY_INVALID);
      }
   }

   /**
    * Gets encoded password based on a raw password.
    *
    * @param rawPassword Non encoded (raw) password.
    * @return Encoded password.
    * @throws IllegalArgumentException if rawPassword is null or empty.
    */
   private String getEncodedPassword(String rawPassword)
      throws IllegalArgumentException{
      if (null != rawPassword && !rawPassword.isEmpty()) {
         return this.passwordEncoder.encode(rawPassword);
      }
      throw new IllegalArgumentException(IAE_MESSAGE_PASSWORD_INVALID);
   }
}
