package br.com.acmattos.election.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of Credential's.
 * 
 * @author acmattos
 * @since 16/01/2017
 * TODO JAVADOC
 */
interface CredentialRepository extends JpaRepository<Credential, Long>{
   /**
    * Find a credential by its username.
    * @param username .
    * @return An credential defined by the username or null if no one is found.
    */
   Credential findByUsername(String username);
}
