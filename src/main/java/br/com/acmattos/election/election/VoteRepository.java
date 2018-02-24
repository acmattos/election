package br.com.acmattos.election.election;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author acmattos
 * @since 11/02/2018.
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
   @Query("   select v " +
          "     from Vote v " +
          " order by v.employee.name asc, v.requirement.description asc")
   List<Vote> findAllVotesOrderedByEmplyeeAscAndRequirementsAsc();
}
