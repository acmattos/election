package br.com.acmattos.election.auth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Convenient strategy for configuring an OAUth2 Authorization Server. Beans 
 * of this type are applied to the Spring context automatically if you 
 * @EnableAuthorizationServer.
 * @author acmattos
 * @since 16/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurerImpl
   extends AuthorizationServerConfigurerAdapter {
   @Autowired
   private AuthenticationManager authenticationManager;
   
   @Autowired
   private UserDetailsService userDetailsService;
   
   /**
    * Configure the non-security features of the Authorization Server endpoints, like token store, token customizations, user approvals and grant types.
    Configure the ClientDetailsService, e.g. declaring individual clients and their properties. Note that password grant is not enabled (even if some clients are allowed it) unless an AuthenticationManager is supplied to the AuthorizationServerConfigurer.configure(AuthorizationServerEndpointsConfigurer). At least one client, or a fully formed custom ClientDetailsService must be declared or the server will not start.
Specified by:
configure in interface AuthorizationServerConfigurer
Parameters:
clients - the client details configurer
Throws:
Exception

Configure the ClientDetailsService, e.g. declaring individual clients and their properties. Note that password grant is not enabled (even if some clients are allowed it) unless an AuthenticationManager is supplied to the configure(AuthorizationServerEndpointsConfigurer). At least one client, or a fully formed custom ClientDetailsService must be declared or the server will not start.
Parameters:
clients - the client details configurer
Throws:
Exception
configure
    */
   @Override
   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients
         .inMemory()//jdbc(DataSource dataSource) 
         .withClient("election-frontend")
         .secret("secret")
         .authorizedGrantTypes("password", "refresh_token")
         .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
         .scopes("read", "write", "trust")
         .accessTokenValiditySeconds(600)// 10 min
         .refreshTokenValiditySeconds(7200);//120 min
   }
   
   /**
    * Configure the non-security features of the Authorization Server endpoints, like token store, token customizations, user approvals and grant types.
    Configure the non-security features of the Authorization Server endpoints, like token store, token customizations, user approvals and grant types. You shouldn't need to do anything by default, unless you need password grants, in which case you need to provide an AuthenticationManager.
Specified by:
configure in interface AuthorizationServerConfigurer
Parameters:
endpoints - the endpoints configurer
Throws:
Exception

Configure the non-security features of the Authorization Server endpoints, like token store, token customizations, user approvals and grant types. You shouldn't need to do anything by default, unless you need password grants, in which case you need to provide an AuthenticationManager.
Parameters:
endpoints - the endpoints configurer
Throws:
Exception
    */
   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints)
      throws Exception {
      endpoints
         .authenticationManager(authenticationManager)
         .accessTokenConverter(jwtAccessTokenConverter())
         .tokenStore(tokenStore())
         .userDetailsService(userDetailsService)
         .reuseRefreshTokens(false);
   }
   
   @Bean
   public TokenStore tokenStore() {
      return new InMemoryTokenStore();
   }
   
   @Bean
   @Primary
   public DefaultTokenServices tokenServices() {
      final DefaultTokenServices tokenServices = new DefaultTokenServices();
      tokenServices.setTokenStore(tokenStore());
      tokenServices.setSupportRefreshToken(true);
      return tokenServices;
   }
   
   @Bean
   public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
      return converter;
   }
}
