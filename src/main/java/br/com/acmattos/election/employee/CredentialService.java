package br.com.acmattos.election.employee;

/**
 * Infraestructure service available for retrieving full credential info.
 *
 * @author acmattos
 * @since 17/01/2018
 */
public interface CredentialService {

   /**
    * Finds a credention, with all profile and role associated information.
    *
    * @param username username of user.
    * @return Full credential of user or null.
    */
    Credential findCredentialWithFullProfileByUsername(String username);
}
