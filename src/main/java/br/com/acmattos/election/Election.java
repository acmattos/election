package br.com.acmattos.election;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Bootstraps Election Application.
 *
 * @author acmattos
 * @since 07/02/2018
 * TODO UNIT TEST
 */
@SpringBootApplication
public class Election extends SpringBootServletInitializer {
	
	/**
	 * Allows this aplication to run propertly.
	 *
	 * @param args not used
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		System.out.println(new Election().passwordEncoder().encode("alfies"));
//		System.out.println(new Election().passwordEncoder().encode("ameliaj"));
//		System.out.println(new Election().passwordEncoder().encode("duncanb"));
//		System.out.println(new Election().passwordEncoder().encode("evaw"));
//		System.out.println(new Election().passwordEncoder().encode("fergusd"));
//		System.out.println(new Election().passwordEncoder().encode("lydiae"));
//		System.out.println(new Election().passwordEncoder().encode("hught"));
//		System.out.println(new Election().passwordEncoder().encode("rubyw"));
//		System.out.println(new Election().passwordEncoder().encode("leot"));
//		System.out.println(new Election().passwordEncoder().encode("zaraj"));
		org.hsqldb.util.DatabaseManagerSwing.main(new String[] {
			"--url",  "jdbc:hsqldb:mem:testdb", "--noexit"
		});
		new SpringApplicationBuilder(Election.class).run(args);
	}
	
	/**
	 * Bean responsible for password encryption and validation.
	 *
	 * @return Password encoder bean.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
	}
}
