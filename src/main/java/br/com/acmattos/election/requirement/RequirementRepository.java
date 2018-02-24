package br.com.acmattos.election.requirement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author acmattos
 * @since 07/02/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
public interface RequirementRepository extends JpaRepository<Requirement, Long> {
   /**
    * Retorna a lista de total de votos por recursos, de forma descendente
    * (recursos mais votados emprimeiro lugar).
    * @return  Uma lista com todos os recursosA list of persons whose last name is an exact match with the given last name.
    *          If no persons is found, this method returns an empty list.
    */
   @Query(" select new br.com.acmattos.election.requirement.RequirementVO(r.id, r.description, count(v.id)) " +
          "   from Requirement r " +
          " left join r.votes v " +
          " group by r.id, r.description " +
          " order by count(v.id) desc, r.description asc")
   public List<RequirementVO> findAllCountedVotesByRequirement();

   @Query(value = "  select distinct (r.id), r.description, 0 \n" +
      "    from Requirement r\n" +
      "    left outer join vote v on v.requirement_id = r.id\n" +
      "   where  v.requirement_id not in (\n" +
      "         select re.id\n" +
      "           from Requirement re\n" +
      "           left outer join vote vo on vo.requirement_id = re.id\n" +
      "          where vo.employee_id = :employeeId \n" +
      "     )\n" +
      "     or v.employee_id is null\n" +
      "group by r.id\n" +
      "order by r.description asc", nativeQuery = true)
   public List<Requirement> findAllRequirementsNotVotedByEmployee(
      @Param("employeeId") Long employeeId);
}