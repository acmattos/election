package br.com.acmattos.election.employee;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * A profile is a collection of (security) roles. Each profile has its group of 
 * roles, allowing credentials to access major groups of application's
 * functionality.
 * If the specific profile is disabled, its roles cannot be added as authorities 
 * for any user. 
 * 
 * Lombok helps with getters, setter toString, equals and hashcode of the class.
 * 
 * @author acmattos
 * @since 16/01/2018
 * TODO CHECK, UNIT TEST
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"credentials", "roles"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
   /** Role's Identity. */
   @Id
   @SequenceGenerator(name           = "profile_id_seq",
                      sequenceName   = "profile_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy  = GenerationType.SEQUENCE,
                   generator = "profile_id_seq")
   @NotNull
   private Long id;
   /** Profile's Description. Cannot return null. */
   @NotNull
   @Size(min=1, max=20)
   private String description;
   /** 
    * Indicates whether the profile is enabled or disabled. 
    * A disabled profile cannot have its roles added as authorities for any user. 
    */
   private boolean enabled;
   /** Profile's Available Roles (Granted Authorities). */
   @ManyToMany(cascade = CascadeType.ALL)
   private List<Role> roles;
   /** Profile's Related Credentials. */
   @ManyToMany(mappedBy = "profiles")
   private List<Credential> credentials;
   /** List of Available Roles for this Profile. */
   @ManyToMany(cascade = CascadeType.ALL)
   private List<Role> roles;
}