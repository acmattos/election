package br.com.acmattos.election.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Profile's Repository.
 *
 * @author acmattos
 * @since 18/01/2017
 */
public interface ProfileRepository  extends JpaRepository<Profile, Long> {
}
