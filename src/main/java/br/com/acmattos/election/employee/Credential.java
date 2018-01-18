package br.com.acmattos.election.employee;

import lombok.*;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Credentials that allows someone to access the application.
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
   /** Credential's Password. Cannot return null. */
   @NotNull
   @Size(min=1, max=100)
   private String password;
   /** Credential's Granted Authorities. Cannot return null. */
   @ManyToMany(cascade = CascadeType.ALL)//(mappedBy = "profile", fetch = FetchType.EAGER) // TODO MAP CORRECTLY http://blog.jbaysolutions.com/2012/12/17/jpa-2-relationships-many-to-many/
   private List<Profile> profiles;
   /** */
//   @OneToOne(mappedBy = "credential")
//   private Employee employee;
    /** Credential's 'Enabled' Indicator. Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated. */
   private boolean enabled;

   @PrePersist
   public void onPrePersist() {
      // TODO CHANGE PASSWORD MUST REENCRIPTY IT
      String senhaOfuscada = new BCryptPasswordEncoder().encode(this.getPassword());
      this.setPassword(senhaOfuscada);
   }   
}
