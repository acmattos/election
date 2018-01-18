package br.com.acmattos.election.employee;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A role is the smallest access permition available, allowing an user to 
 * access a specific part of the application.
 * If the specific role is disabled, it'll not be added as an authority for any 
 * user. 
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
@ToString(exclude = "profiles")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
   /** Role's Identity. */
   @Id
   @SequenceGenerator(name           = "role_id_seq",
                      sequenceName   = "role_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy  = GenerationType.SEQUENCE,
                   generator = "role_id_seq")
   @NotNull
   private Long id;
   /** Role's Description. Cannot return null. */
   @NotNull
   @Size(min=1, max=50)
   private String description;
   /** 
    * Indicates whether the role is enabled or disabled. 
    * A disabled role cannot be added as an authority for any user. 
    */
   private boolean enabled;

   @ManyToMany(mappedBy = "roles")
   private List<Profile> profiles;
}