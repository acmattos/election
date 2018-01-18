package br.com.acmattos.election.employee;

/**
 * @author acmattos
 * @since 17/01/2018
 * TODO JAVADOC
 */
public interface CredentialService {

    Credential findCredentialWithFullProfileByUsername(String username);
}
