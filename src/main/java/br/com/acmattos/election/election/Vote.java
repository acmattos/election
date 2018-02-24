package br.com.acmattos.election.election;

import br.com.acmattos.election.employee.Employee;
import br.com.acmattos.election.requirement.Requirement;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author acmattos
 * @since 11/02/2018.
 * TODO JAVADOC, UNIT TEST
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vote {
   /** Vote's Identity. */
   @Id
   @SequenceGenerator(name           = "vote_id_seq",
                      sequenceName   = "vote_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy  = GenerationType.SEQUENCE,
      generator = "vote_id_seq")
   @NotNull
   private Long id;
   /** Vote's Related Comment. Cannot return null. */
   @NotNull
   @Size(min=1, max=50)
   private String comment;
   @NotNull
   private LocalDateTime datetime;
   @ManyToOne
   @JoinColumn(name = "requirement_id")
   @NotNull
   private Requirement requirement;
   @ManyToOne
   @JoinColumn(name = "employee_id")
   @NotNull
   private Employee employee;
}
