package br.com.acmattos.election.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Credential's Repository.
 * 
 * @author acmattos
 * @since 16/01/2017
 */
interface CredentialRepository extends JpaRepository<Credential, Long>{
   /**
    * Find a credential by its username.
    * @param username username.
    * @return An credential defined by the username or null if no one is found.
    */
   Credential findByUsername(String username);
}
