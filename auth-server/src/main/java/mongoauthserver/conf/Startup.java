package mongoauthserver.conf;

import com.google.common.collect.Sets;
import mongoauthserver.library.document.MongoAccessToken;
import mongoauthserver.library.document.MongoAuthorizationCode;
import mongoauthserver.library.document.MongoClientDetails;
import mongoauthserver.library.document.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.authority.AuthorityUtils;

@Configuration
public class Startup {

    @Autowired MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void mongoFillDb() {

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
