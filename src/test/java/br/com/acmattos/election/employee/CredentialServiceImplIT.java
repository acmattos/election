package br.com.acmattos.election.employee;

import br.com.acmattos.election.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 18/01/2018
 */
public class CredentialServiceImplIT extends IntegrationTest{

    @Autowired
    private CredentialRepository repository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CredentialServiceImpl service;

    @Before
    public void setUp() throws Exception {
        Role role = Role.builder()
                .description("role1")
                .enabled(true)
                .build();
        Profile profile = Profile.builder()
                .description("profile1")
                .roles(new ArrayList<Role>(){{ add(role); }})
                .enabled(true)
                .build();
        profileRepository.save(profile);
        Credential credential = Credential.builder()
                .username("username")
                .rawPassword("password")
                .profiles(new ArrayList<Profile>(){{ add(profile); }})
                .enabled(true)
                .build();
        repository.save(credential);
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
        profileRepository.deleteAll();
    }

    @Test
    public void findCredentialWithFullProfileByUsername_notFound() throws Exception {
        String username = "notfound";
        Credential credential = service.findCredentialWithFullProfileByUsername(username);
        assertNull("Credential must not de found", credential);
    }

    @Test
    public void findCredentialWithFullProfileByUsername_ok() throws Exception {
        String username = "username";
        Credential credential = service.findCredentialWithFullProfileByUsername(username);
        assertNotNull("Credential must be found", credential);
        assertEquals("Same username", username, credential.getUsername());
        assertNotEquals("Different password", "password", credential.getPassword());
        assertTrue("Credential enabled", credential.isEnabled());
    }
}