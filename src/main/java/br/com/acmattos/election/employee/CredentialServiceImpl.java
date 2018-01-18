package br.com.acmattos.election.employee;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author acmattos
 * @since 17/01/2018
 * TODO JAVADOC
 */
@Service
@Transactional
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    private CredentialRepository repository;

    public Credential findCredentialWithFullProfileByUsername(String username){
        Credential credential = repository.findByUsername(username);
        if(null != credential && null != credential.getProfiles()) {
            Hibernate.initialize(credential.getProfiles());
        }
        return credential;
    }
}
