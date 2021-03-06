package br.com.acmattos.election.employee;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of infraestructure service available for retrieving full
 * credential info.
 * In order to allow later collection initialization, this class must be
 * annotated as transactional.
 *
 * @author acmattos
 * @since 17/01/2018
 */
@Service
@Transactional // TODO DOCUMENT SOLUTION
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    private CredentialRepository repository;

    //@Autowired ProfileRepository profileRepository;
    /**
     * Finds a credention, with all profile and role associated information.
     *
     * @param username username of user.
     * @return Full credential of user or null.
     */
    public Credential findCredentialWithFullProfileByUsername(String username){
        Credential credential = repository.findByUsername(username);
        if(null != credential && null != credential.getProfiles()) {
            Hibernate.initialize(credential.getProfiles());// TODO DOCUMENT SOLUTION
            for(Profile profile: credential.getProfiles()) {
                Hibernate.initialize(profile.getRoles());
            }
        }
        return credential;
    }
}
