package com.rites.sample.oauth2.authserver;

import com.google.common.collect.Sets;
import com.rites.sample.oauth2.authserver.library.document.MongoAccessToken;
import com.rites.sample.oauth2.authserver.library.document.MongoAuthorizationCode;
import com.rites.sample.oauth2.authserver.library.document.MongoClientDetails;
import com.rites.sample.oauth2.authserver.library.document.MongoUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
//@SessionAttributes("authorizationRequest")
@ComponentScan("com.rites.sample.oauth2.authserver")
public class AuthServer {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(AuthServer.class, args);

        if (args .length > 0 && "init".equalsIgnoreCase(args[0])) {

            MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

            mongoTemplate.dropCollection(MongoUser.class);
            mongoTemplate.dropCollection(MongoClientDetails.class);
            mongoTemplate.dropCollection(MongoAccessToken.class);
            mongoTemplate.dropCollection(MongoAuthorizationCode.class);

            // init the users
            MongoUser mongoUser = new MongoUser();
            mongoUser.setUsername("andrey");
            mongoUser.setPassword("ozornov");
            mongoUser.setRoles(Sets.newHashSet(("ROLE_USER")));
            mongoTemplate.save(mongoUser);

            // init the client details
            MongoClientDetails clientDetails = new MongoClientDetails();
            clientDetails.setClientId("client");
            clientDetails.setClientSecret("clientsecret");
            clientDetails.setSecretRequired(true);
//            clientDetails.setResourceIds(Sets.newHashSet("foo"));
            clientDetails.setScope(Sets.newHashSet("openid"));
            clientDetails.setAuthorizedGrantTypes(Sets.newHashSet("authorization_code", "refresh_token",
                    "password", "client_credentials"));
//            clientDetails.setRegisteredRedirectUri(Sets.newHashSet("http://192.168.0.126:8080/"));
            clientDetails.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));
//            clientDetails.setAccessTokenValiditySeconds(60);
//            clientDetails.setRefreshTokenValiditySeconds(14400);
            clientDetails.setAutoApprove(false);
            mongoTemplate.save(clientDetails);

        }
    }
}
