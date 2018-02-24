package br.com.acmattos.election.requirement;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Value Object that is similar to Requirement entity, responsible to hold
 * Requirement's data outside application scope.
 *
 * @author acmattos
 * @since 07/02/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequirementVO {

    /** Requirement's Identity. */
   @ApiModelProperty(example="1", position = 1, required = false, readOnly = true)
   private Long id;
   /** Requirement's Description. */
   @ApiModelProperty(example="Some description for this", position = 2, required = true)
   private String description;
   //private String comment;
   private Long numberOfVotes;

//   public RequirementVO(Long id, String description, Long numberOfVotes){
//      this.id = id;
//      this.description = description;
//      this.numberOfVotes = numberOfVotes;
//   }

   /**
    * Indicates if this VO is valid.
    *
    * @return true if it's valid, other wise false.
    */
   @ApiModelProperty(hidden = true)
   public boolean isValid(){
      return null != this.description && this.description.length() > 0;
   }

   /**
    * Converts a list of entities into a list of VOs.
    *
    * @param entities Requirements.
    * @return A list of VOs equivalent to the given entities.
    */
   public static List<RequirementVO> toVOs(List<Requirement> entities){
      List<RequirementVO> vos = new ArrayList<>();
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
    * @param entity Requirement.
    * @return A VO equivalent to the given entity.
    */
   public static RequirementVO toVO(Requirement entity){
      RequirementVO vo = null;
      if(null != entity) {
         vo = RequirementVO.builder()
            .id(entity.getId())
            .description(entity.getDescription())
            //.totalDeVotos(entity.getTotalDeVotos())
            .build();
      }
      return vo;
   }

   /**
    * Converts a VO into an entity.
    *
    * @param vo Requiremet VO.
    * @return An entity equivalent to the given VO.
    */
   public static Requirement toEntity(RequirementVO vo){
      Requirement entity = null;
      if(null != vo) {
         entity = Requirement.builder()
            .id(vo.getId())
            .description(vo.getDescription())
            .build();
      }
      return entity;
   }
}
