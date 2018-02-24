package br.com.acmattos.election.election;

import br.com.acmattos.election.employee.EmployeeVO;
import br.com.acmattos.election.requirement.RequirementVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author acmattos
 * @since 11/02/2018.
 * TODO JAVADOC, UNIT TEST
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@ApiModel(value="Vote",
   description="Vote data, composed by id, description, employee and requirement")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
class VoteVO {
   private static final transient DateTimeFormatter FORMATTER =
       DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
   /***/
   @ApiModelProperty(example="1", position = 1, required = false, readOnly = true)
   private Long id;
   /***/
   @ApiModelProperty(example="Vote comment", position = 2, required = true)
   private String comment;
   @ApiModelProperty(example="Date/Time", position = 3, required = true)
   private String datetime;
   /***/
   @ApiModelProperty(position = 4, required = true)
   private EmployeeVO employee;
   /***/
   @ApiModelProperty(position = 5, required = true)
   private RequirementVO requirement;
   
   /**
    * Indicates if this VO is valid.
    *
    * @return true if it's valid, other wise false.
    */
   @ApiModelProperty(hidden = true)
   public boolean isValid(){
      return null != this.comment && this.comment.length() > 0
         && null != this.datetime && this.datetime.length() > 0
         && null != this.employee && null != this.employee.getId()
         && null != this.requirement && null != this.requirement.getId();
   }
   
   /**
    * Converts a list of entities into a list of VOs.
    *
    * @param entities Employees.
    * @return A list of VOs equivalent to the given entities.
    */
   public static List<VoteVO> toVOs(List<Vote> entities){
      List<VoteVO> vos = new ArrayList<>();
      if(null != entities) {
         entities.stream().forEach(entity -> {
            vos.add(toVO(entity));
         });
      }
      return vos;
   }
   
   /**
    * Converts an entity into a VO.
    *
    * @param entity Employee.
    * @return A VO equivalent to the given entity.
    */
   public static VoteVO toVO(Vote entity){
      VoteVO vo = null;
      if(null != entity) {
         vo = VoteVO.builder()
            .id(entity.getId())
            .comment(entity.getComment())
            .datetime(entity.getDatetime().toString())
            .employee(EmployeeVO.toVO(entity.getEmployee()))
            .requirement(RequirementVO.toVO(entity.getRequirement()))
            .build();
      }
      return vo;
   }
   
   /**
    * Converts a VO into an entity.
    *
    * @param vo Employee VO.
    * @return An entity equivalent to the given VO.
    */
   public static Vote toEntity(VoteVO vo){
      Vote entity = null;
      if(null != vo) {
         entity = Vote.builder()
            .id(vo.getId())
            .comment(vo.getComment())
            .datetime(VoteVO.convertDatetime(vo.getDatetime()))
            .employee(EmployeeVO.toEntity(vo.getEmployee()))
            .requirement(RequirementVO.toEntity(vo.getRequirement()))
            .build();
      }
      return entity;
   }

   public static LocalDateTime convertDatetime(String datetime){
      if (null != datetime && !datetime.isEmpty()){
         return LocalDateTime.parse(datetime, FORMATTER);
      }
      throw new IllegalArgumentException("datahora nula ou vazia!");
   }
}
