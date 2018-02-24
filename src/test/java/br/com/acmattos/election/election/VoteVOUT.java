package br.com.acmattos.election.election;

import br.com.acmattos.election.UnitTest;
import br.com.acmattos.election.employee.CredentialVO;
import br.com.acmattos.election.employee.EmployeeVO;
import br.com.acmattos.election.requirement.RequirementVO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 12/02/2018.
 * TODO CHECK
 */
public class VoteVOUT extends UnitTest {
   private VoteVO vo;
   
   @Before
   public void setUp() throws Exception {
      CredentialVO credentialVO = CredentialVO.builder()
         .id(3L)
         .username("username")
         .password("password")
         .enabled(true)
         .build();
      EmployeeVO employee = EmployeeVO.builder()
         .id(1L)
         .employeeid(2)
         .name("name")
         .email("email")
         .credential(credentialVO)
         .build();
      RequirementVO requirement = RequirementVO.builder()
         .id(2L)
         .description("description")
         //.totalDeVotos(0L)
         .build();
      this.vo = VoteVO.builder()
         .id(3L)
         .comment("comment")
         .datetime("2018-01-01 01:23:45 -0300")//"yyyy-MM-dd HH:mm:ss Z"
         .employee(employee)
         .requirement(requirement)
         .build();
   }
     
   @Test
   public void isValid_NotOk() throws Exception {
      this.vo.setComment(null);
      assertFalse(this.vo.isValid());
      this.vo.setComment("");
      assertFalse(this.vo.isValid());
      this.vo.setComment("alterado");
      this.vo.setDatetime(null);
      assertFalse(this.vo.isValid());
      this.vo.setDatetime("");
      assertFalse(this.vo.isValid());
      this.vo.setDatetime("2018-01-01 01:23:45 -0300");
      EmployeeVO employeeVO = this.vo.getEmployee();
      this.vo.setEmployee(null);
      assertFalse(this.vo.isValid());
      employeeVO.setId(null);
      this.vo.setEmployee(employeeVO);
      assertFalse(this.vo.isValid());
      employeeVO.setId(1000L);
      RequirementVO requirementVO = this.vo.getRequirement();
      this.vo.setRequirement(null);
      assertFalse(this.vo.isValid());
      requirementVO.setId(null);
      this.vo.setRequirement(requirementVO);
      assertFalse(this.vo.isValid());
      requirementVO.setId(2000L);
      assertTrue(this.vo.isValid());
   }
   
   @Test
   public void isValid_Ok() throws Exception {
      assertTrue("Must be valid", this.vo.isValid());
   }
   
   @Test
   public void toVO_NullEntity() throws Exception {
      Vote entity = null;
      VoteVO vo = VoteVO.toVO(entity);
      assertNull("VO must be null!", vo);
   }
   
   @Test
   public void toVO_EntityOk() throws Exception {
      Vote entity = VoteVO.toEntity(this.vo);
      VoteVO vo = VoteVO.toVO(entity);
      assertEquals("Same vo and entity", vo.toString(), this.vo.toString());
   }
   
   @Test
   public void toEntity_NullVO() throws Exception {
      Vote entity = VoteVO.toEntity(null);
      assertNull("Entity must be null!", entity);
   }
   
   @Test
   public void toEntity_Ok() throws Exception {
      Vote entity = VoteVO.toEntity(this.vo);
      assertEquals("Same toString()",
         "Vote(id=3, comment=comment, datetime=2018-01-01T01:23:45, requirement=Requirement(id=2, description=description, votes=null), employee=Employee(id=1, employeeid=2, name=name, email=email, credential=Credential(id=3, username=username, password=null, enabled=true, rawPassword=password)))",
         entity.toString());
   }
   
   
   @Test
   public void ToString() throws Exception {
      assertEquals("Same toString()",
         "VoteVO(id=3, comment=comment, datetime=2018-01-01 01:23:45 -0300, employee=EmployeeVO(id=1, employeeid=2, name=name, email=email, credential=CredentialVO(id=3, username=username, password=password, enabled=true)), requirement=RequirementVO(id=2, description=description, numberOfVotes=null))",
         this.vo.toString());
   }
   

   @Test
   public void converteParaListaDeVO_listaComElemento() throws Exception {
      Vote vote = VoteVO.toEntity(this.vo);
      List<Vote> votes = new ArrayList<Vote>() {{ add(vote); }};
      assertEquals(1, votes.size());
      List<VoteVO> vos = VoteVO.toVOs(votes);
      assertEquals(1, vos.size());
      assertEquals("VoteVO(id=3, comment=comment, datetime=2018-01-01T01:23:45, employee=EmployeeVO(id=1, employeeid=2, name=name, email=email, credential=CredentialVO(id=3, username=username, password=password, enabled=true)), requirement=RequirementVO(id=2, description=description, numberOfVotes=null))",
         vos.get(0).toString());
   }

   @Test
   public void converteParaListaDeVO_listaVazia() throws Exception {
      List<Vote> votes = new ArrayList<>();
      assertEquals(0, votes.size());
      List<VoteVO> vos = VoteVO.toVOs(votes);
      assertEquals(0, vos.size());
   }

   @Test
   public void converteParaListaDeVO_listaNula() throws Exception {
      List<Vote> votes = null;
      assertNull(votes);
      List<VoteVO> vos = VoteVO.toVOs(votes);
      assertEquals(0, vos.size());
   }

   @Test
   public void converteParaVO_VotoOk() throws Exception {
      Vote vote = VoteVO.toEntity(this.vo);
      VoteVO vo = VoteVO.toVO(vote);
      this.vo.setDatetime(this.vo.getDatetime().substring(0, 19).replace(" ", "T"));
      assertEquals(vo.toString(), this.vo.toString());
      this.vo.setDatetime(this.vo.getDatetime() + " -0300");
   }

   @Test
   public void converteParaVO_VotoNulo() throws Exception {
      Vote vote = null;
      VoteVO vo = VoteVO.toVO(vote);
      assertNull(vo);
   }

   @Test
   public void converteParaEntidade_Ok() throws Exception {
      Vote vote = VoteVO.toEntity(this.vo);
      assertEquals("Vote(id=3, comment=comment, datetime=2018-01-01T01:23:45, requirement=Requirement(id=2, description=description, votes=null), employee=Employee(id=1, employeeid=2, name=name, email=email, credential=Credential(id=3, username=username, password=null, enabled=true, rawPassword=password)))",
         vote.toString());
   }

   @Test
   public void converteParaEntidade_Nula() throws Exception {
      Vote vote = VoteVO.toEntity(null);
      assertNull(vote);
   }

   @Test
   public void converteDataHora_Ok(){
      String datetime = "2018-01-01 01:23:45 -0300";
      LocalDateTime localDateTime = VoteVO.convertDatetime(datetime);
      assertEquals("2018-01-01T01:23:45", localDateTime.toString());
   }

   @Test(expected = IllegalArgumentException.class)
   public void converteDataHora_datahoraNula(){
      String datetime = null;
      LocalDateTime localDateTime = null;
      try {
         localDateTime = VoteVO.convertDatetime(datetime);
      }catch (IllegalArgumentException iae){
         assertEquals("datahora nula ou vazia!", iae.getMessage());
         assertNull(localDateTime);
         throw iae;
      }
   }

   @Test(expected = IllegalArgumentException.class)
   public void converteDataHora_datahoraVazia(){
      String datetime = "";
      LocalDateTime localDateTime = null;
      try {
         localDateTime = VoteVO.convertDatetime(datetime);
      }catch (IllegalArgumentException iae){
         assertEquals("datahora nula ou vazia!", iae.getMessage());
         assertNull(localDateTime);
         throw iae;
      }
   }
   @Test(expected = DateTimeParseException.class)
   public void converteDataHora_datahoraForaDoPadrao(){
      String datahota = "2018-01-01T01:23:45Z";
      LocalDateTime localDateTime = null;
      try {
         localDateTime = VoteVO.convertDatetime(datahota);
      }catch (DateTimeParseException dtpe){
         assertEquals("Text '2018-01-01T01:23:45Z' could not be parsed at index 10", dtpe.getMessage());
         assertNull(localDateTime);
         throw dtpe;
      }
   }
}