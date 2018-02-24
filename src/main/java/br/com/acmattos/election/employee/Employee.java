package br.com.acmattos.election.employee;

import br.com.acmattos.election.election.Vote;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Employee Entity holds all employee's related data.
 *
 * Lombok helps with getters, setter toString, equals and hashcode of the class.
 *
 * @author acmattos
 * @since 18/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString//(exclude="votes")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

   /** Employee's Identity. */
   @Id
   @SequenceGenerator(name           = "employee_id_seq",
                      sequenceName   = "employee_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
                   generator = "employee_id_seq")
   private Long id;
   /** Employee's Identity. */
   @NotNull
   private Integer employeeid;
   /** Employee's Name. */
   @NotNull
   @Size(min=1, max=100)
   private String name;
   /** Employee's E-mail. */
   @NotNull
   @Size(min=6, max=100)
   private String email;
   /** Employee's Credential. */
   @OneToOne(cascade = CascadeType.ALL)
   @NotNull
   private Credential credential;
   @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
   private List<Vote> votes;
}
