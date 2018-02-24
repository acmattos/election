package br.com.acmattos.election.employee;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Credentials that allows anyone to access the application.
 * 
 * Lombok helps with getters, setter toString, equals and hashcode of the class.
 * 
 * @author acmattos
 * @since 16/01/2018
 * TODO CHECK, UNIT TEST
 * TODO https://www.concretepage.com/java/jpa/jpa-entitylisteners-example-with-callbacks-prepersist-postpersist-postload-preupdate-postupdate-preremove-postremove
 * TODO http://www.baeldung.com/database-auditing-jpa
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "profiles")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(CredentialListener.class)
@Entity
public class Credential {
   /** Credential's Identity. */
   @Id
   @SequenceGenerator(name           = "user_id_seq",
                      sequenceName   = "user_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy  = GenerationType.SEQUENCE,
                   generator = "user_id_seq")
   @NotNull
   private Long id;
   /** Credential's Username. Cannot return null. */
   @NotNull
   @Size(min=1, max=50)
   private String username;
   /** Credential's Password (encrypted). Cannot return null. */
   @NotNull
   @Size(min=1, max=100)
   private String password;
   /** Credential's Profiles. Cannot return null. */
   @ManyToMany// TODO MAP CORRECTLY http://blog.jbaysolutions.com/2012/12/17/jpa-2-relationships-many-to-many/
   @JoinTable(name = "credential_profile",
       joinColumns = @JoinColumn(name = "credential_id"),
       inverseJoinColumns = @JoinColumn(name = "profile_id"))
   private List<Profile> profiles;
   /**
    * Credential's 'Enabled' Indicator. Indicates whether the user is enabled
    * or disabled. A disabled user cannot be authenticated.
    */
   private boolean enabled;
   /** Credential's Raw Password (as it was typed). */
   @Size(min=4, max=30)
   private String rawPassword; //TODO DOCUMENT SOLUTION
}
