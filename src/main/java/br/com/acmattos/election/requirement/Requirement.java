package br.com.acmattos.election.requirement;

//import br.com.acmattos.election.election.Vote;
import br.com.acmattos.election.election.Vote;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Requirement Entity holds all employee's related data.
 * @author acmattos
 * @since 07/02/2018
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
public class Requirement {
   /** Requirement's Identity. */
   @Id
   @SequenceGenerator(name           = "requirement_id_seq",
                      sequenceName   = "requirement_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy  = GenerationType.SEQUENCE,
                   generator = "requirement_id_seq")
   private Long id;
   /** Requirement's Description. Can't be null. */
   @NotNull
   private String description;
   @OneToMany(mappedBy = "requirement", fetch = FetchType.EAGER)
   private List<Vote> votes;
//   @Transient
//   private long totalDeVotos;

//   public Requirement(Long id, String description, long totalDeVotos){
//      this.id = id;
//      this.description = description;
//      this.totalDeVotos = totalDeVotos;
//   }
}
