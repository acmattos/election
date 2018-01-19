package br.com.acmattos.election.employee;

import br.com.acmattos.election.UnitTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author acmattos
 * @since 18/01/2018
 * TODO CHECK
 */
public class CredentialVOUT extends UnitTest {
    private CredentialVO vo;

    @Before
    public void setUp() throws Exception {
        this.vo = CredentialVO.builder()
                .id(1L)
                .username("username")
                .password("rawPassword")
                .enabled(true)
                .build();
    }

    @Test
    public void isValid_NotOk() throws Exception {
        this.vo.setUsername(null);
        assertFalse("Username: null is not valid", this.vo.isValid());
        this.vo.setUsername("");
        assertFalse("Username: empty is not valid", this.vo.isValid());

        this.vo.setPassword(null);
        assertFalse("Password: null is not valid", this.vo.isValid());
        this.vo.setPassword("");
        assertFalse("Password: empty is not valid", this.vo.isValid());

        this.vo.setUsername("valid");
        this.vo.setPassword("valid");
        this.vo.setId(null);
        assertTrue("Must be valid", this.vo.isValid());
        this.vo.setEnabled(false);
        assertTrue("Must be valid", this.vo.isValid());
    }

    @Test
    public void isValid_Ok() throws Exception {
        assertTrue("Must be valid", this.vo.isValid());
    }

    @Test
    public void toVO_NullEntity() throws Exception {
        Credential entity = null;
        CredentialVO vo = CredentialVO.toVO(entity);
        assertNull("VO must be null!", vo);
    }

    @Test
    public void toVO_EntityOk() throws Exception {
        Credential entity = CredentialVO.toEntity(this.vo);
        CredentialVO vo = CredentialVO.toVO(entity);
        assertEquals("Same vo and entity", vo.toString(), this.vo.toString());
    }

    @Test
    public void toEntity_NullVO() throws Exception {
        Credential entity = CredentialVO.toEntity(null);
        assertNull("Entity must be null!", entity);
    }

    @Test
    public void toEntity_Ok() throws Exception {
        Credential entity = CredentialVO.toEntity(this.vo);
        assertEquals("Same toString()",
           "Credential(id=1, username=username, password=null, enabled=true, rawPassword=rawPassword)",
                entity.toString());
    }


    @Test
    public void ToString() throws Exception {
        assertEquals("Same toString()",
                "CredentialVO(id=1, username=username, password=rawPassword, enabled=true)",
                this.vo.toString());
    }
}