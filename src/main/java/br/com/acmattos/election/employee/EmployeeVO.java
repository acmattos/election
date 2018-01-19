package br.com.acmattos.election.employee;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author acmattos
 * @since 18/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {
   /***/
   private Long id;
    /***/
   private Integer employeeId;
   /***/
   private String name;
   /***/
   private String email;
   /***/
   private CredentialVO credential;

   /**
    * Indicates if this VO is valid.
    *
    * @return true if it's valid, other wise false.
    */
   public boolean isValid(){
      return null != employeeId && employeeId > 0
         && null != this.name && this.name.length() > 0
         && null != this.email && this.email.length() > 0
         && credential.isValid();
   }

   /**
    * Converts a listt of entities into a list of VOs.
    *
    * @param entities Employees.
    * @return A list of VOs equivalent to the given entities.
    */
   public static List<EmployeeVO> toVOs(List<Employee> entities){
      List<EmployeeVO> vos = new ArrayList<>();
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
   public static EmployeeVO toVO(Employee entity){
      EmployeeVO vo = null;
      if(null != entity) {
         vo = EmployeeVO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .employeeId(entity.getEmployeeId())
            .email(entity.getEmail())
            .credential(CredentialVO.toVO(entity.getCredential()))
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
   public static Employee toEntity(EmployeeVO vo){
      Employee entity = null;
      if(null != vo) {
         entity = Employee.builder()
            .id(vo.getId())
            .name(vo.getName())
            .employeeId(vo.getEmployeeId())
            .email(vo.getEmail())
            .credential(CredentialVO.toEntity(vo.getCredential()))
            .build();
      }
      return entity;
   }
}
