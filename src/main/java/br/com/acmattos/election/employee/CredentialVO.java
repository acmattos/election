package br.com.acmattos.election.employee;

import lombok.*;

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
public class CredentialVO {
   /** Credential's Identity. */
   private Long id;
   /** Credential's Username. Cannot return null. */
   private String username;
   /** Credential's Password. Cannot return null. */
   private String password;
   /** Credential's Granted Authorities. Cannot return null. */
   //private List<Profile> profiles;
   /** Credential's 'Enabled' Indicator. Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated. */
   private boolean enabled;

   /**
    * Indicates if this VO is valid.
    *
    * @return true if it's valid, other wise false.
    */
   public boolean isValid(){
      return null != this.username && this.username.length() > 0
         && null != this.password && this.password.length() > 0;
   }

//   /**
//    * Converts a listt of entities into a list of VOs.
//    *
//    * @param entities Credentials.
//    * @return A list of VOs equivalent to the given entities.
//    */
//   public static List<CredentialVO> toVOs(List<Credential> entities){
//        List<CredentialVO> vos = new ArrayList<>();
//        if(null != entities) {
//            entities.stream().forEach(entity -> {
//                vos.add(toVO(entity));
//            });
//        }
//        return vos;
//   }

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
