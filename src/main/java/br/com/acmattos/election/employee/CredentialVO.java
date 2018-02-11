package br.com.acmattos.election.employee;

import lombok.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

/**
 * Value Object that is similar to Credential entity, responsible to hold
 * Credential's data outside application scope.
 *
 * @author acmattos
 * @since 18/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@ApiModel(value="Credential",
          description="Employee's  credential data, composed by id, username, password, profiles and enabled")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CredentialVO {
   /** Credential's Identity. */
   @ApiModelProperty(example="1", position = 1, required = false, readOnly = true)
   private Long id;
   /** Credential's Username. Cannot return null. */
   @ApiModelProperty(example="johndoe", position = 2, required = true)
   private String username;
   /** Credential's Password. Cannot return null. */
   @ApiModelProperty(example="myP4ssW0rd!", position = 3, required = true)
   private String password;
   /** Credential's Granted Authorities. Cannot return null. */
   //@ApiModelProperty(example="johndoe", position = 4, required = true)
   //private List<Profile> profiles;
   /** Credential's 'Enabled' Indicator. Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated. */
   @ApiModelProperty(example="true", position = 5, required = true)
   private boolean enabled;

   /**
    * Indicates if this VO is valid.
    *
    * @return true if it's valid, other wise false.
    */
   @ApiModelProperty(hidden = true)
   public boolean isValid(){
      return null != this.username && this.username.length() > 0
         && null != this.password && this.password.length() > 0;
   }

   /**
    * Converts an entity into a VO.
    *
    * @param entity Credential.
    * @return A VO equivalent to the given entity.
    */
   public static CredentialVO toVO(Credential entity){
      CredentialVO vo = null;
      if(null != entity) {
         vo = CredentialVO.builder()
                 .id(entity.getId())
                 .username(entity.getUsername())
                 .password(entity.getRawPassword())
                 .enabled(entity.isEnabled())
                 .build();
      }
      return vo;
   }

   /**
    * Converts a VO into an entity.
    *
    * @param vo Credential VO.
    * @return An entity equivalent to the given VO.
    */
   public static Credential toEntity(CredentialVO vo){
      Credential entity = null;
      if(null != vo) {
         entity = Credential.builder()
                 .id(vo.getId())
                 .username(vo.getUsername())
                 .rawPassword(vo.getPassword())
                 .enabled(vo.isEnabled())
                 .build();
      }
      return entity;
   }   
}
