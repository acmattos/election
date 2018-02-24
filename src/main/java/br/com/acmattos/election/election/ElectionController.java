package br.com.acmattos.election.election;

import br.com.acmattos.election.employee.Employee;
import br.com.acmattos.election.employee.EmployeeRepository;
import br.com.acmattos.election.requirement.Requirement;
import br.com.acmattos.election.requirement.RequirementRepository;
import br.com.acmattos.election.requirement.RequirementVO;
import br.com.acmattos.election.util.ResponseEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author acmattos
 * @since 11/02/2018.
 * * TODO JAVADOC, CHECK, UNIT TEST
 */
@RestController
class ElectionController {
   @Autowired
   private VoteRepository voteRepository;
   @Autowired
   private RequirementRepository requirementRepository;
   @Autowired
   private EmployeeRepository employeeRepository;

   @GetMapping("/v1/election/participants")
   @PreAuthorize("hasAuthority('ROLE_VOTES_BY_EMPLOYEE')")
   ResponseEntity<List<VoteVO>> votesByEmplyeeAndRequirements() {
      List<Vote> entities =
         voteRepository.findAllVotesOrderedByEmplyeeAscAndRequirementsAsc();
      return ResponseEntityBuilder
          .builder()
          .body(VoteVO.toVOs(entities))
          .build();
   }

   @PostMapping("/v1/election/votes")
   ResponseEntity<VoteVO> doVote(@RequestBody VoteVO voteVO){
      Vote vote = null;
      if(voteVO.isValid()) {
         Employee employee =
             employeeRepository.findOne(voteVO.getEmployee().getId());
         Requirement requirement =
             requirementRepository.findOne(voteVO.getRequirement().getId());
         vote = Vote.builder()
             .comment(voteVO.getComment())
             .datetime(VoteVO.convertDatetime(voteVO.getDatetime()))
             .employee(employee)
             .requirement(requirement)
             .build();
         voteRepository.save(vote);
      }
      return ResponseEntityBuilder
          .builder()
          .body(VoteVO.toVO(vote))
          .build();
   }

   @GetMapping("/v1/election/requirements/{employeeId}")
   ResponseEntity<List<RequirementVO>> requirementsNotVotedByEmployee(
       @PathVariable long employeeId) {
      List<Requirement> entities =
          requirementRepository.findAllRequirementsNotVotedByEmployee(employeeId);
      return ResponseEntityBuilder
          .builder()
          .body(RequirementVO.toVOs(entities))
          .build();
   }

   @GetMapping("/v1/election/results")
   ResponseEntity<List<RequirementVO>> resultados() {
      List<RequirementVO> entities = requirementRepository.findAllCountedVotesByRequirement();
      return ResponseEntityBuilder
          .builder()
          .body(entities)
          .build();
   }
}
